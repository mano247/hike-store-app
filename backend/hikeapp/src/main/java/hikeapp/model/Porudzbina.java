package hikeapp.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Korisnik korisnik;

    private String status;
    private LocalDateTime vreme;
    private Double ukupnaCena;

    // adresa za dostavu
    private String adresa;
    private String grad;
    private String postanskiBroj;
    private String drzava;

    @OneToMany(mappedBy = "porudzbina", cascade = CascadeType.ALL)
    private List<PorudzbinaItem> proizvodi;
}
