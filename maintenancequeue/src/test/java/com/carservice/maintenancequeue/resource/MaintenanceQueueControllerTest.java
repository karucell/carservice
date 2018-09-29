package com.carservice.maintenancequeue.resource;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.carservice.maintenancequeue.service.MaintenanceQueueService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MaintenanceQueueControllerTest {

    @MockBean
    private MaintenanceQueueService maintenanceQueueServiceMock;

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private JsonTool jsonTool;


    @Test
    public void addMaintenance_withValidValues_shouldReturn200OKAndMaintenanceId() throws Exception {

        String carId = "1234-5678";
        String procedureId = "5678-9010";
        String newMaintenanceEntityId = "1112-1314A";
        when(maintenanceQueueServiceMock.addMaintenance(carId, procedureId)).thenReturn(newMaintenanceEntityId);

        String postMessage = String.format("{\"carId\":\"%s\",\"procedureId\":\"%s\"}", carId, procedureId);

        mockMvc.perform(post(ResourcePaths.ADD_MAINTENANCE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(postMessage))
                .andExpect(status().isOk())
                .andExpect(content().string(newMaintenanceEntityId));

        verify(maintenanceQueueServiceMock, times(1)).addMaintenance(carId, procedureId);
    }

}