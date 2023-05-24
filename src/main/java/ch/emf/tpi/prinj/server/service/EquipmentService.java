package ch.emf.tpi.prinj.server.service;

import ch.emf.tpi.prinj.server.entity.Equipment;

import java.util.List;

public interface EquipmentService {

    List<Equipment> getEquipments();

    Equipment getEquipmentById(Long id);

    Equipment addEquipment(Equipment equipment);

    Equipment updateEquipment(Equipment equipment, Long id);
    void deleteEquipment(Long id);
}
