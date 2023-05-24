package ch.emf.tpi.prinj.server.controller;

import ch.emf.tpi.prinj.server.entity.Equipment;
import ch.emf.tpi.prinj.server.service.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = EquipmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EquipmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentService equipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private static Equipment equipment;


    @BeforeAll
    public static void init() {
        equipment = Equipment.builder()
                .name("equipment1")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();
    }


    @Test
    public void equipmentController_CreateEquipment_ReturnCreated() throws Exception {
        given(equipmentService.addEquipment(ArgumentMatchers.any())).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/equipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(equipment.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryNumber", CoreMatchers.is(equipment.getInventoryNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", CoreMatchers.is(equipment.getSerialNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyPrice", CoreMatchers.is(equipment.getBuyPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyDate", CoreMatchers.is(equipment.getBuyDate())));
    }


}
