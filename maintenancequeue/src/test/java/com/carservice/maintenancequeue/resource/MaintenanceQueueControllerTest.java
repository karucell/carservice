package com.carservice.maintenancequeue.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.carservice.common.JsonTool;
import com.carservice.maintenancequeue.WebTestConfiguration;
import com.carservice.maintenancequeue.exceptions.InvalidEntityException;
import com.carservice.maintenancequeue.resource.model.Maintenance;
import com.carservice.maintenancequeue.service.MaintenanceQueueService;

@RunWith(SpringRunner.class)
@WebMvcTest(MaintenanceQueueController.class)
@ContextConfiguration(classes = WebTestConfiguration.class)
public class MaintenanceQueueControllerTest {

    @MockBean
    private MaintenanceQueueService maintenanceQueueServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonTool jsonTool;

    @Test
    public void addMaintenance_withValidValues_shouldReturn200OKAndMaintenanceId() throws Exception {
        // given
        String carId = "1234-5678";
        String procedureId = "5678-9010";
        // and
        Maintenance newMaintenance = new Maintenance(null, carId, procedureId, null);
        // and
        String newMaintenanceEntityId = "1112-1314A";
        // and
        when(maintenanceQueueServiceMock.addMaintenance(
                newMaintenance.getCarId(),
                newMaintenance.getProcedureId())
        ).thenReturn(newMaintenanceEntityId);
        // and
        String postMessage = jsonTool.toJson(newMaintenance);

        // when
        mockMvc.perform(post(ResourcePaths.ADD_MAINTENANCE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(postMessage))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(newMaintenanceEntityId));

        // and
        verify(maintenanceQueueServiceMock, times(1)).addMaintenance(carId, procedureId);
    }

    @Test
    public void addMaintenance_withInvalidValues_shouldReturn422UnprocessableEntity() throws Exception {
        // given
        Maintenance newMaintenance = new Maintenance(null, null, null, null);
        // and
        when(maintenanceQueueServiceMock.addMaintenance(any(), any())).thenThrow(InvalidEntityException.class);
        // and
        String postMessage = jsonTool.toJson(newMaintenance);

        // when
        mockMvc.perform(post(ResourcePaths.ADD_MAINTENANCE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(postMessage))
                // then
                .andExpect(status().isUnprocessableEntity());

        // and
        verify(maintenanceQueueServiceMock, times(1)).addMaintenance(any(), any());
    }

}