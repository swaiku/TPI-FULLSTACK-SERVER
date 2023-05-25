package ch.emf.tpi.prinj.server.controller;

import ch.emf.tpi.prinj.server.entity.Equipment;
import ch.emf.tpi.prinj.server.exception.EquipmentNotFoundException;
import ch.emf.tpi.prinj.server.service.EquipmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
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
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
                .id(1L)
                .name("equipment1")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();
    }
    @Test
    public void equipmentController_GetAllEquipments_ReturnEmpty() throws Exception {
        given(equipmentService.getEquipments()).willReturn(Collections.emptyList());

        ResultActions response = mockMvc.perform(get("/equipments")
                .contentType(MediaType.APPLICATION_JSON));

        // except to receive an empty array
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void equipmentController_GetAllEquipments_ReturnList() throws Exception {
        given(equipmentService.getEquipments()).willReturn(Collections.singletonList(equipment));

        ResultActions response = mockMvc.perform(get("/equipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)));

        // except to receive an empty array
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void equipmentController_getEquipment_ReturnEquipment() throws Exception {
        given(equipmentService.getEquipmentById(1L)).willReturn(equipment);

        ResultActions response = mockMvc.perform(get("/equipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(equipment.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryNumber", CoreMatchers.is(equipment.getInventoryNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", CoreMatchers.is(equipment.getSerialNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyPrice", CoreMatchers.is(equipment.getBuyPrice().doubleValue())));
    }

    @Test
    public void equipmentController_getEquipment_InexistantId_ReturnError404() throws Exception {
        given(equipmentService.getEquipmentById(1L)).willThrow(new EquipmentNotFoundException(1L));

        ResultActions response = mockMvc.perform(get("/equipments/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void equipmentController_getEquipment_InvalidId_ReturnError400() throws Exception {
        // "HELLO" is not a valid id
        // given(equipmentService.getEquipmentById("HELLO")).willReturn(null);

        ResultActions response = mockMvc.perform(get("/equipments/HELLO")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyPrice", CoreMatchers.is(equipment.getBuyPrice().doubleValue())));
    }
    @Test
    public void equipmentController_CreateEquipment_Invalid_ReturnError400() throws Exception {
        given(equipmentService.addEquipment(ArgumentMatchers.any())).willThrow(new ConstraintViolationException(null));

        ResultActions response = mockMvc.perform(post("/equipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void equipmentController_UpdateEquipment_Valid_ReturnUpdated() throws Exception {
        given(equipmentService.updateEquipment(ArgumentMatchers.any(), any())).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc.perform(put("/equipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(equipment.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventoryNumber", CoreMatchers.is(equipment.getInventoryNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", CoreMatchers.is(equipment.getSerialNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyPrice", CoreMatchers.is(equipment.getBuyPrice().doubleValue())));
    }

    @Test
    public void equipmentController_UpdateEquipment_Invalid_ReturnError400() throws Exception {
        given(equipmentService.updateEquipment(ArgumentMatchers.any(), any())).willThrow(new ConstraintViolationException(null));

        ResultActions response = mockMvc.perform(put("/equipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // update equipment with invalid id
    @Test
    public void equipmentController_UpdateEquipment_InexistantId_ReturnError404() throws Exception {
        given(equipmentService.updateEquipment(ArgumentMatchers.any(), any())).willThrow(new EquipmentNotFoundException(1L));

        ResultActions response = mockMvc.perform(put("/equipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipment)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
