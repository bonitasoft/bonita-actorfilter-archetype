package com.company.bonitasoft

import java.util.logging.Logger

import org.bonitasoft.engine.connector.AbstractConnector
import org.bonitasoft.engine.connector.ConnectorValidationException
import org.bonitasoft.engine.filter.AbstractUserFilter
import org.bonitasoft.engine.api.APIAccessor

class MyKotlinActorfilter : AbstractUserFilter {

    constructor() : super()

    companion object {
        const val MAXIMUM_WORKLOAD_INPUT = "maximumWorkload"
    }
    
    val logger = Logger.getLogger(MyKotlinActorfilter::class.java.name)

    /**
     * Perform validation on the inputs defined on the actorfilter definition (src/main/resources/actorfilter-kotlin-test.def)
     * You should:
     * - validate that mandatory inputs are presents
     * - validate that the content of the inputs is coherent with your use case (e.g: validate that a date is / isn't in the past ...)
     */
    override fun validateInputParameters() {
        checkPositiveIntegerInput(MAXIMUM_WORKLOAD_INPUT)
    }
    
    fun checkPositiveIntegerInput(inputName : String) {
        val value = getInputParameter(inputName)
        if (value is Int) {
            if (value <= 0) {
                throw ConnectorValidationException("Mandatory parameter '$inputName' must be a positive integer but is '$value'.")
            }
        } else {
            throw ConnectorValidationException("'$inputName' parameter must be an Integer")
        }
    }

    /**
     * @return a list of {@link User} id that are the candidates to execute the task where this filter is defined.
     * If the result contains a unique user, the task will automaticaly be assigned.
     * @see AbstractUserFilter.shouldAutoAssignTaskIfSingleResult
     */
    override fun filter(actorName: String) : List<Long> {
        val maximumWorkload = getInputParameter(MAXIMUM_WORKLOAD_INPUT)
        val apiAccessor = getAPIAccessor()
        val processAPI = apiAccessor.getProcessAPI()
        val users = processAPI.getUserIdsForActor(getExecutionContext().getProcessDefinitionId(), actorName, 0, Integer.MAX_VALUE);

        logger.info("${MAXIMUM_WORKLOAD_INPUT} input = ${maximumWorkload}")
        return users.filter { !isOverloaded(it, apiAccessor, maximumWorkload as Int) }
    }
    
    fun isOverloaded(userId : Long, apiAccessor : APIAccessor, maximumWorkload : Int) : Boolean {
        return apiAccessor.getProcessAPI().getNumberOfAssignedHumanTaskInstances(userId) >= maximumWorkload
    }
}