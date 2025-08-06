package hikeapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecenzijaDTO {
	@NotNull(message = "Korisnik je obavezan")
	private Long korisnikId;
	
	@NotNull(message = "Proizvod je obavezan")
	private Long proizvodId;
	
	@Min(value = 1)
	@Max(value = 5)
	private Integer ocena;
	
	private String komentar;
}
