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
    public void equipmentRepository_existsByInventoryNumber_ReturnTrue() {
        Equipment equipment = Equipment.builder()
                .name("equipment1")
                .buyPrice(100)
                .inventoryNumber("1234")
                .serialNumber("SN1234")
                .buyDate(new Date()).build();

        equipmentRepository.save(equipment);

        boolean isEquipmentExist = equipmentRepository.existsByInventoryNumber(equipment.getInventoryNumber());

        assertThat(isEquipmentExist).isEqualTo(true);
    }
}
