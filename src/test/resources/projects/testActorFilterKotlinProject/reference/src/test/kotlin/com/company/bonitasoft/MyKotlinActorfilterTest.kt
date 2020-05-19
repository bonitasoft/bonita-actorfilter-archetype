package com.company.bonitasoft

import java.util.Collections

import io.mockk.every
import io.mockk.mockk

import org.bonitasoft.engine.api.APIAccessor
import org.bonitasoft.engine.api.ProcessAPI
import org.bonitasoft.engine.connector.ConnectorValidationException
import org.bonitasoft.engine.connector.EngineExecutionContext
import org.junit.Before
import org.junit.Test

class MyKotlinActorfilterTest {

    val filter = MyKotlinActorfilter()
    val apiAccessor = mockk<APIAccessor>()
    val processApi = mockk<ProcessAPI>()
    val executionContext = mockk<EngineExecutionContext>()

    @Before
    fun setUp() {
        every { apiAccessor.getProcessAPI() } returns processApi
        every { executionContext.getProcessDefinitionId() } returns 0
        filter.setAPIAccessor(apiAccessor);
        filter.setExecutionContext(executionContext);
    }

    @Test(expected = ConnectorValidationException::class)
    fun should_throw_exception_if_mandatory_input_is_missing() {
        filter.setInputParameters(Collections.emptyMap())
        filter.validateInputParameters()
    }

    @Test(expected = ConnectorValidationException::class)
    fun should_throw_exception_if_mandatory_input_is_not_positive_integer() {
        val parameters = HashMap<String, Any>();
        parameters.put(MyKotlinActorfilter.MAXIMUM_WORKLOAD_INPUT, -1)
        filter.setInputParameters(parameters)
        filter.validateInputParameters()
    }

    @Test(expected = ConnectorValidationException::class)
    fun should_throw_exception_if_mandatory_input_is_not_an_integer() {
        val parameters = HashMap<String, Any>();
        parameters.put(MyKotlinActorfilter.MAXIMUM_WORKLOAD_INPUT, "1")
        filter.setInputParameters(parameters)
        filter.validateInputParameters()
    }

    @Test
    fun should_return_a_list_of_candidates() {
        every { processApi.getUserIdsForActor(any(), "MyActor", 0, Integer.MAX_VALUE) } returns listOf(1L, 2L, 3L)
        every { processApi.getNumberOfAssignedHumanTaskInstances(1L) } returns 2L
        every { processApi.getNumberOfAssignedHumanTaskInstances(2L) } returns 3L
        every { processApi.getNumberOfAssignedHumanTaskInstances(3L) } returns 0L

        val parameters = HashMap<String, Any>();
        parameters.put(MyKotlinActorfilter.MAXIMUM_WORKLOAD_INPUT, 3)
        filter.setInputParameters(parameters)

        val candidates = filter.filter("MyActor")
        assert(
            candidates.contains(1L)
                    && candidates.contains(3L)
                    && !candidates.contains(2L)
        ) { "Only users with a workload below the maximum can be candidates." }
    }
}