package hikeapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KorisnikDTO {
	
	@NotBlank(message = "Ime je obavezno")
	private String ime;
	
	@NotBlank(message = "Prezime je obavezno")
	private String prezime;
	
	@NotBlank(message = "Email je obavezan")
	@Email(message = "Email format nije ispravan")
	private String email;
	
	@NotBlank(message = "Lozinka je obavezna")
	@Size(min = 6, message = "Lozinka mora imati minimun 6 karaktera")
	private String password;
	
	private String brojTelefona;
	
	private String adresa;
	private String grad;
	private String postanskiBroj;
	private String drzava;
}
