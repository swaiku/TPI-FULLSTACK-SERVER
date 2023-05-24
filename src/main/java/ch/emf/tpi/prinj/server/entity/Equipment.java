package ch.emf.tpi.prinj.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name="equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "inventory_number", nullable = false, length = 45)
    private String inventoryNumber;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "serial_number", length = 50)
    private String serialNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "buy_date")
    private Date buyDate;

    @Column(name = "buy_price", precision = 8, scale = 2)
    private BigDecimal buyPrice;
}

