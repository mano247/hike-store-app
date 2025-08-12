package hikeapp.model;

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
public class KorpaItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Korpa korpa;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Proizvod proizvod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Velicina velicina;
    
    @Column(nullable = false)
    private Double kolicina;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean vidljiv = true;
}
