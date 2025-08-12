package hikeapp.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PorudzbinaItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Porudzbina porudzbina;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Velicina velicina;

    @Column(nullable = false)
    private Double kolicina;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal cenaItema;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean vidljiv = true;
}
