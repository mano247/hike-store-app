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
public class PorudzbinaItemDTO {
	@NotNull(message = "Velicina je obavezna")
	private Long velicinaId;
	
	@NotNull(message = "Kolicina je obavezna")
	@Positive(message = "Kolicina mora biti pozitivna")
	private Double kolicina;
}
