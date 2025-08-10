package hikeapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProizvodDTO {
	
	@NotBlank(message = "Ime je obavezno")
	private String ime;
	
	@NotBlank(message = "Opis je obavezan")
	private String opis;
	
	@NotBlank(message = "Pol je obavezan")
	@Pattern(regexp = "muski|zeniski|unisex|np", message = "Dozvoljene vrednosti su: muski, zenski, unisex, np")
	private String pol;
	
	@NotBlank(message = "Sifra je obavezna")
	private String sifra;
	
	@NotNull(message = "Cena je obavezna")
	@Positive(message = "Cena mora biti pozitivna")
	private Double cena;
	
	@Builder.Default
	private Boolean dostupno = true;
	
	private String slikaURL;
	
	@Builder.Default
	private Boolean vidljiv = true;
	
	@NotNull(message = "Kategorija je obavezna")
	private Long kategorijaId;
	
	@NotNull(message = "Brend je obavezan")
	private Long brendId;
	
	@NotNull(message = "Korisnik koji kreira je obavezan")
	private Long kreiraoId;
}
