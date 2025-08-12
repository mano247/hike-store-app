package hikeapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KorpaItemDTO {
	
	@NotNull(message = "Proizvod je obavezan")
	private Long proizvodId;
	
	@NotNull(message = "Velicina je obavezna")
	private Long velicinaId;
	
	@NotNull(message = "Kolicina je obavezna")
	@Positive(message = "Kolicina mora biti veca od 0")
	private Double kolicina;
}
