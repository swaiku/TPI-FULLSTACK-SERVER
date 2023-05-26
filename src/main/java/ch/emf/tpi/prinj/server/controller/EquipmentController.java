package ch.emf.tpi.prinj.server.controller;

import ch.emf.tpi.prinj.server.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.emf.tpi.prinj.server.entity.Equipment;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping("/equipments")
    ResponseEntity<List<Equipment>> getEquipments() {
        return ResponseEntity.ok(equipmentService.getEquipments());
    }

    @GetMapping("/equipments/{id}")
    ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @PostMapping("/equipments")
    ResponseEntity<Equipment> newEquipment(@Valid @RequestBody Equipment newEquipment) {
        return new ResponseEntity<>(equipmentService.addEquipment(newEquipment), HttpStatus.CREATED);
    }

    @PutMapping("/equipments/{id}")
    ResponseEntity<Equipment> editEquipment(@Valid @RequestBody Equipment editEquipment, @PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.updateEquipment(editEquipment, id));
    }

    @DeleteMapping("/equipments/{id}")
    void deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
    }

}
