package hikeapp.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Korisnik {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String brojTelefona;
    
    private String adresa;       
    private String grad;
    private String postanskiBroj;
    private String drzava;
    
    private String email;
    private String password;
    
    private String role;
    
    private LocalDateTime kreiran;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Porudzbina> porudzbine;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Korpa> korpe;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Poruka> poruke;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Recenzija> recenzije;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<Notifikacija> notifikacije;
}
