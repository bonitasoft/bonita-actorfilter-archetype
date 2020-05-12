package com.company.bonitasoft;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.api.APIAccessor;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.connector.ConnectorValidationException;
import org.bonitasoft.engine.connector.EngineExecutionContext;
import org.bonitasoft.engine.filter.UserFilterException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MyJavaActorfilterTest {

    private MyJavaActorfilter filter;
    @Mock
    private APIAccessor apiAccessor;
    @Mock
    private ProcessAPI processApi;
    @Mock
    private EngineExecutionContext executionContext;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        filter = new MyJavaActorfilter();
        when(apiAccessor.getProcessAPI()).thenReturn(processApi);
        filter.setAPIAccessor(apiAccessor);
        filter.setExecutionContext(executionContext);
    }
    
    @Test
    public void should_throw_exception_if_mandatory_input_is_missing() throws ConnectorValidationException {
        filter.setInputParameters(Collections.emptyMap());
                
        expectedException.expect(ConnectorValidationException.class);
        
        filter.validateInputParameters();
    }

    @Test
    public void should_throw_exception_if_mandatory_input_is_not_positive_integer() throws ConnectorValidationException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(MyJavaActorfilter.MAXIMUM_WORKLOAD_INPUT, -1);
        filter.setInputParameters(parameters);
        
        expectedException.expect(ConnectorValidationException.class);
        
        filter.validateInputParameters();
    }

    @Test
    public void should_throw_exception_if_mandatory_input_is_not_an_integer() throws ConnectorValidationException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(MyJavaActorfilter.MAXIMUM_WORKLOAD_INPUT, "1");
        filter.setInputParameters(parameters);
        
        expectedException.expect(ConnectorValidationException.class);
        
        filter.validateInputParameters();
    }

    @Test
    public void should_return_a_list_of_candidates() throws UserFilterException {
        when(processApi.getUserIdsForActor(Mockito.anyLong(), Mockito.eq("MyActor"), Mockito.eq(0),
                Mockito.eq(Integer.MAX_VALUE)))
                        .thenReturn(Arrays.asList(1L, 2L, 3L));
        when(processApi.getNumberOfAssignedHumanTaskInstances(1L)).thenReturn(2L);
        when(processApi.getNumberOfAssignedHumanTaskInstances(2L)).thenReturn(3L);
        when(processApi.getNumberOfAssignedHumanTaskInstances(3L)).thenReturn(0L);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(MyJavaActorfilter.MAXIMUM_WORKLOAD_INPUT, 3);
        filter.setInputParameters(parameters);
        
        List<Long> candidates = filter.filter("MyActor");
        
        assertTrue("Only users with a workload below the maximum can be candidates.",
                candidates.contains(1L)
                && candidates.contains(3L)
                && !candidates.contains(2L));
    }

}
