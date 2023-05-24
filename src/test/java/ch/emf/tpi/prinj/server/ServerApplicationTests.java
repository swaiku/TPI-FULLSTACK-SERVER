package ch.emf.tpi.prinj.server;

import ch.emf.tpi.prinj.server.controller.EquipmentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ServerApplicationTests {

	@Autowired
	private EquipmentController equipmentController;

	@Test
	void contextLoads() {
		assertThat(equipmentController).isNotNull();
	}
}
