package ch.emf.tpi.prinj.server.repository;

import ch.emf.tpi.prinj.server.entity.Equipment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EquipmentRepositoryTests {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    public void equipmentRepository_Save_ReturnSavedEquipment() {

        Equipment equipment = Equipment.builder()
                .name("equipment1")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();

        Equipment savedEquipment = equipmentRepository.save(equipment);

        assertThat(savedEquipment).isNotNull();
        assertThat(savedEquipment.getId()).isGreaterThan(0);
    }

    @Test
    public void equipmentRepository_FindAll_ReturnAllEquipment() {
        Equipment equipment = Equipment.builder()
                .name("equipment1")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();

        Equipment equipment2 = Equipment.builder()
                .name("equipment2")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();

        equipmentRepository.save(equipment);
        equipmentRepository.save(equipment2);

        List<Equipment> equipmentList = equipmentRepository.findAll();

        assertThat(equipmentList).isNotNull();
        assertThat(equipmentList.size()).isEqualTo(2);
    }

    @Test
    public void equipmentRepository_FindById_ReturnEquipment() {
        Equipment equipment = Equipment.builder()
                .name("equipment1")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();

        equipmentRepository.save(equipment);

        Equipment savedEquipment = equipmentRepository.findById(equipment.getId()).get();

        assertThat(savedEquipment).isNotNull();
    }

    @Test
    public void equipmentRepository_existsByInventoryNumber_ReturnTrue() {
        Equipment equipment = Equipment.builder()
                .name("equipment1")
                .buyPrice(new BigDecimal("1.00"))
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();

        equipmentRepository.save(equipment);

        boolean isEquipmentExist = equipmentRepository.existsByInventoryNumber(equipment.getInventoryNumber());

        assertThat(isEquipmentExist).isEqualTo(true);
    }

    @Test
    public void equipmentRepository_DuplicatedInventoryNumber_beInvalid() {

    }
}
