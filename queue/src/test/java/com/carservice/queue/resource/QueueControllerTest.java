package com.carservice.queue.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.carservice.common.JsonTool;
import com.carservice.queue.WebTestConfiguration;
import com.carservice.queue.exceptions.InvalidEntityException;
import com.carservice.queue.resource.model.Maintenance;
import com.carservice.queue.service.QueueService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QueueController.class)
@ContextConfiguration(classes = WebTestConfiguration.class)
public class QueueControllerTest {

    @MockBean
    private QueueService queueServiceMock;

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
        when(queueServiceMock.addMaintenance(
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
        verify(queueServiceMock, times(1)).addMaintenance(carId, procedureId);
    }

    @Test
    public void addMaintenance_withInvalidValues_shouldReturn422UnprocessableEntity() throws Exception {
        // given
        Maintenance newMaintenance = new Maintenance(null, null, null, null);
        // and
        when(queueServiceMock.addMaintenance(any(), any())).thenThrow(InvalidEntityException.class);
        // and
        String postMessage = jsonTool.toJson(newMaintenance);

        // when
        mockMvc.perform(post(ResourcePaths.ADD_MAINTENANCE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(postMessage))
                // then
                .andExpect(status().isUnprocessableEntity());

        // and
        verify(queueServiceMock, times(1)).addMaintenance(any(), any());
    }

}