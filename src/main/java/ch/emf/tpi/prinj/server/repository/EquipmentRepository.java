package ch.emf.tpi.prinj.server.repository;

import ch.emf.tpi.prinj.server.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    boolean existsByInventoryNumber(String inventoryNumber);
}
