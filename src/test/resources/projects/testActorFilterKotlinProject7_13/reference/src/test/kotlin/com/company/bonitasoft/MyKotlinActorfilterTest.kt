package com.company.bonitasoft

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.bonitasoft.engine.api.APIAccessor
import org.bonitasoft.engine.api.ProcessAPI
import org.bonitasoft.engine.connector.ConnectorValidationException
import org.bonitasoft.engine.connector.EngineExecutionContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MyKotlinActorfilterTest {

    @MockK
    lateinit var apiAccessor: APIAccessor

    @MockK
    lateinit var processApi: ProcessAPI

    @RelaxedMockK
    lateinit var executionContext: EngineExecutionContext

    lateinit var filter: MyKotlinActorfilter

    @BeforeEach
    fun setUp() {
        filter = MyKotlinActorfilter()
        filter.apiAccessor = apiAccessor
        filter.executionContext = executionContext

        every { apiAccessor.processAPI } returns processApi
    }

    @Test
    fun `should throw exception if mandatory input is missing`() {
        assertThatThrownBy { filter.validateInputParameters() }
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should throw exception if mandatory input is not positive integer`() {
        // Given
        val parameters = HashMap<String, Any>()
        parameters.put(MyKotlinActorfilter.MAXIMUM_WORKLOAD_INPUT, -1)
        filter.setInputParameters(parameters)

        // When
        assertThatThrownBy { filter.validateInputParameters() }
                // Then
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should throw exception if mandatory input is not an integer`() {
        // Given
        val parameters = HashMap<String, Any>()
        parameters.put(MyKotlinActorfilter.MAXIMUM_WORKLOAD_INPUT, "1")
        filter.setInputParameters(parameters)

        // When
        assertThatThrownBy { filter.validateInputParameters() }
                // Then
                .isExactlyInstanceOf(ConnectorValidationException::class.java)
    }

    @Test
    fun `should return a list of candidates`() {
        // Given
        every { processApi.getUserIdsForActor(any(), "MyActor", 0, Integer.MAX_VALUE) } returns listOf(1L, 2L, 3L)
        every { processApi.getNumberOfAssignedHumanTaskInstances(1L) } returns 2L
        every { processApi.getNumberOfAssignedHumanTaskInstances(2L) } returns 3L
        every { processApi.getNumberOfAssignedHumanTaskInstances(3L) } returns 0L

        val parameters = HashMap<String, Any>()
        parameters[MyKotlinActorfilter.MAXIMUM_WORKLOAD_INPUT] = 3
        filter.setInputParameters(parameters)

        // When
        val candidates = filter.filter("MyActor")

        // Then
        assertThat(candidates).`as`("Only users with a workload below the maximum are returned as candidates")
                .containsExactly(1L, 3L)
    }
}