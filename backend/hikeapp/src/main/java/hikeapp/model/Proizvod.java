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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proizvod {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String opis;
    private String pol;
    private String sifra;
    private Double cena;
    private Boolean dostupno;
    private String slikaURL;
    private LocalDateTime vreme;
    private boolean vidljiv = true;

    @ManyToOne
    private Korisnik kreirao;

    @ManyToOne
    private Brend brend;

    @ManyToOne
    private Kategorija kategorija;

    @OneToMany(mappedBy = "proizvod", cascade = CascadeType.ALL)
    private List<Velicina> velicine;

    @OneToOne(mappedBy = "proizvod", cascade = CascadeType.ALL)
    private Popust popust;

    @OneToMany(mappedBy = "proizvod", cascade = CascadeType.ALL)
    private List<Recenzija> recenzije;
}
