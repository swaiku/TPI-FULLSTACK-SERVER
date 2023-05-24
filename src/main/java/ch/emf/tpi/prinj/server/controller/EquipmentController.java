package ch.emf.tpi.prinj.server.controller;

import ch.emf.tpi.prinj.server.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ch.emf.tpi.prinj.server.entity.Equipment;
import java.util.List;

@RestController
@AllArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping("/equipments")
    List<Equipment> getEquipments() {
        return equipmentService.getEquipments();
    }

    @GetMapping("/equipments/{id}")
    Equipment getEquipmentById(@PathVariable Long id) {
        return equipmentService.getEquipmentById(id);
    }

    @PostMapping("/equipments")
    Equipment newEquipment(@RequestBody Equipment newEquipment) {
        return equipmentService.addEquipment(newEquipment);
    }

    @PutMapping("/equipments/{id}")
    Equipment editEquipment(@RequestBody Equipment editEquipment, @PathVariable Long id) {
        return equipmentService.updateEquipment(editEquipment, id);
    }

    @DeleteMapping("/equipments/{id}")
    void deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
    }

}
