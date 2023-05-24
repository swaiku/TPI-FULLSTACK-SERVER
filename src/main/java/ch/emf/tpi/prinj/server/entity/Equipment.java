package ch.emf.tpi.prinj.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Size(max = 45)
    @NotBlank
    @Column(name = "inventory_number", nullable = false, length = 45, unique = true)
    private String inventoryNumber;

    @Size(max = 45)
    @NotBlank
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Size(max = 50)
    @Column(name = "serial_number", length = 50)
    private String serialNumber;


    @PastOrPresent
    @Temporal(TemporalType.DATE)
    @Column(name = "buy_date")
    private Date buyDate;

    @DecimalMin(value="0.00")
    @Digits(integer = 9, fraction = 2)
    @Column(name = "buy_price", precision = 9, scale = 2)
    private BigDecimal buyPrice;
}

