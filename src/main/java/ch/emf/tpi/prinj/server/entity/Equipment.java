package ch.emf.tpi.prinj.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Min(value=0)
    @Column(name = "buy_price")
    private Integer buyPrice;
}

