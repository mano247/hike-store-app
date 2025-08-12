package hikeapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Porudzbina {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Korisnik korisnik;

    @Column(length = 32, nullable = false)
    private String status;
    
    @Builder.Default
    @Column(nullable = false)
    private OffsetDateTime vreme = OffsetDateTime.now();
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal ukupnaCena;

    // adresa za dostavu
    private String adresa;
    private String grad;
    private String postanskiBroj;
    private String drzava;

    @OneToMany(mappedBy = "porudzbina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PorudzbinaItem> proizvodi;
    
    private Long korpaId;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean vidljiv = true;
    
    
}
