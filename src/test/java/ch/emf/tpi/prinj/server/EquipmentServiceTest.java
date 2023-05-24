package ch.emf.tpi.prinj.server;

import ch.emf.tpi.prinj.server.exception.InventoryNumberAlreadyExistingException;
import ch.emf.tpi.prinj.server.repository.EquipmentRepository;
import ch.emf.tpi.prinj.server.service.EquipmentService;
import ch.emf.tpi.prinj.server.entity.Equipment;
import ch.emf.tpi.prinj.server.service.impl.EquipmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EquipmentServiceTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentService equipmentService = new EquipmentServiceImpl(equipmentRepository);

    @Test
    void getEquipments() {
        Equipment equipment1 = new Equipment();
        Equipment equipment2 = new Equipment();
        List<Equipment> equipments = Arrays.asList(equipment1, equipment2);
        when(equipmentRepository.findAll()).thenReturn(equipments);
        List<Equipment> equipmentsFromService = equipmentService.getEquipments();

        // Verify that the returned list of equipments is the same as the one returned by the equipmentRepository
        assertEquals(equipments, equipmentsFromService);

        // Verify that the findAll method of the equipmentRepository has been called exactly once
        verify(equipmentRepository, times(1)).findAll();
    }

    @Test
    void getEquipmentById() {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        when(equipmentRepository.findById(1L)).thenReturn(java.util.Optional.of(equipment));
        Equipment equipmentFromService = equipmentService.getEquipmentById(1L);

        // Verify that the returned equipment is the same as the one returned by the equipmentRepository
        assertEquals(equipment, equipmentFromService);

        // Verify that the findById method of the equipmentRepository has been called exactly once
        verify(equipmentRepository, times(1)).findById(1L);
    }

    @Test
    void addEquipment() {
        Equipment equipment = new Equipment();
        equipment.setInventoryNumber("123456");
        when(equipmentRepository.existsByInventoryNumber("123456")).thenReturn(false);
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        Equipment equipmentFromService = equipmentService.addEquipment(equipment);

        // Verify that the returned equipment is the same as the one returned by the equipmentRepository
        assertEquals(equipment, equipmentFromService);

        // Verify that the existsByInventoryNumber method of the equipmentRepository has been called exactly once
        verify(equipmentRepository, times(1)).existsByInventoryNumber("123456");

        // Verify that the save method of the equipmentRepository has been called exactly once
        verify(equipmentRepository, times(1)).save(equipment);
    }

    @Test
    void addEquipmentWithInventoryNumberAlreadyExist() {
        Equipment equipment = new Equipment();
        equipment.setInventoryNumber("123456");
        when(equipmentRepository.existsByInventoryNumber("123456")).thenReturn(true);


        assertThrows(InventoryNumberAlreadyExistingException.class, () -> {
            equipmentService.addEquipment(equipment);
        });
        verify(equipmentRepository, times(1)).existsByInventoryNumber("123456");
        verify(equipmentRepository, times(0)).save(equipment);
    }

    @Test
    void editEquipment() {
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setInventoryNumber("123456");
        when(equipmentRepository.findById(1L)).thenReturn(java.util.Optional.of(equipment));
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        Equipment equipmentFromService = equipmentService.updateEquipment(equipment, 1L);

        // Verify that the returned equipment is the same as the one returned by the equipmentRepository
        assertEquals(equipment, equipmentFromService);

        // Verify that the findById method of the equipmentRepository has been called exactly once
        verify(equipmentRepository, times(1)).findById(1L);

        // Verify that the save method of the equipmentRepository has been called exactly once
        verify(equipmentRepository, times(1)).save(equipment);
    }

    @Test
    void editEquipmentWithInventoryNumberChanged() {
        // when the inventory number is changed, the service must ignore the change and keep the old inventory number
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        equipment.setInventoryNumber("123456");
        when(equipmentRepository.findById(1L)).thenReturn(java.util.Optional.of(equipment));
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
    }

}
