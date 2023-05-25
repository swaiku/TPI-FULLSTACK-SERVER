package ch.emf.tpi.prinj.server.service;


import ch.emf.tpi.prinj.server.entity.Equipment;
import ch.emf.tpi.prinj.server.exception.EquipmentNotFoundException;
import ch.emf.tpi.prinj.server.exception.InventoryNumberAlreadyExistingException;
import ch.emf.tpi.prinj.server.repository.EquipmentRepository;
import ch.emf.tpi.prinj.server.service.impl.EquipmentServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTests {

    @Mock
    private EquipmentRepository employeeRepository;

    @InjectMocks
    private EquipmentServiceImpl employeeService;

    private Equipment equipment;

    @BeforeEach
    public void init() {
        equipment = Equipment.builder()
                .id(1L)
                .name("equipment1")
                .serialNumber("SN1234")
                .inventoryNumber("1234")
                .buyPrice(BigDecimal.valueOf(1.00))
                .build();
    }

    @Test
    public void equipmentService_addEquipment_inventoryNumberAlreadyExist_ThrowConstraintViolationException() {
        given(employeeRepository.existsByInventoryNumber(equipment.getInventoryNumber())).willReturn(true);

        assertThrows(InventoryNumberAlreadyExistingException.class, () -> {
            employeeService.addEquipment(equipment);
        });
    }

    @Test
    public void equipmentService_addEquipment_ConstraintViolationException() {
        given(employeeRepository.existsByInventoryNumber(equipment.getInventoryNumber())).willReturn(false);
        given(employeeRepository.save(equipment)).willThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> {
            employeeService.addEquipment(equipment);
        });
    }

    // throw EquipmentNotFoundException if equipment not found
    @Test
    public void equipmentService_updateEquipment_ConstraintViolationException() {
        given(employeeRepository.findById(equipment.getId())).willReturn(Optional.ofNullable(equipment));
        given(employeeRepository.save(equipment)).willThrow(ConstraintViolationException.class);
        Equipment invalidEquipment = Equipment.builder()
                .id(1L)
                .name("")
                .serialNumber("SN1234")
                .inventoryNumber("")
                .buyPrice(BigDecimal.valueOf(1.000))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            employeeService.updateEquipment(invalidEquipment, equipment.getId());
        });
    }

    // throw EquipmentNotFoundException if equipment not found
    @Test
    public void equipmentService_getEquipment_EquipmentNotFoundException() {
        given(employeeRepository.findById(equipment.getId())).willReturn(java.util.Optional.empty());

        assertThrows(EquipmentNotFoundException.class, () -> {
            employeeService.getEquipmentById(equipment.getId());
        });
    }

    // when updating a equipment, the inventory number should not be changed, the other fields can be changed
    @Test
    public void equipmentService_updateEquipment_inventoryNumberChanged_ThrowConstraintViolationException() {
        Equipment updatedEquipment = Equipment.builder()
                .id(equipment.getId())
                .name("equipment1")
                .inventoryNumber("12345")
                .serialNumber("SN4321")
                .buyPrice(BigDecimal.valueOf(2.00))
                .build();

        Equipment exceptedEquipment = Equipment.builder()
                .id(equipment.getId())
                .name(updatedEquipment.getName())
                .inventoryNumber(equipment.getInventoryNumber())
                .serialNumber(updatedEquipment.getSerialNumber())
                .buyPrice(updatedEquipment.getBuyPrice())
                .build();

        given(employeeRepository.findById(equipment.getId())).willReturn(Optional.ofNullable(equipment));
        given(employeeRepository.save(equipment)).willReturn(exceptedEquipment);

        Equipment result = employeeService.updateEquipment(updatedEquipment, equipment.getId());

        assertThat(result.getInventoryNumber()).isEqualTo(equipment.getInventoryNumber());
        assertThat(result.getSerialNumber()).isEqualTo(updatedEquipment.getSerialNumber());
    }

}
