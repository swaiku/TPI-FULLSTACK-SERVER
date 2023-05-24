package ch.emf.tpi.prinj.server.service.impl;

import ch.emf.tpi.prinj.server.entity.Equipment;
import ch.emf.tpi.prinj.server.exception.EquipmentNotFoundException;
import ch.emf.tpi.prinj.server.repository.EquipmentRepository;
import ch.emf.tpi.prinj.server.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    EquipmentRepository equipmentRepository;

    @Override
    public List<Equipment> getEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id).orElseThrow(() -> new EquipmentNotFoundException(id));
    }

    @Override
    public Equipment addEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(Equipment updatedEquipment, Long id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
        BeanUtils.copyProperties(equipment, updatedEquipment);
        return equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }
}
