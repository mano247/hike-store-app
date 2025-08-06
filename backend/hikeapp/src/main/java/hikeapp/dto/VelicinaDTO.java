package hikeapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VelicinaDTO {
	
	@NotBlank(message = "Velicina je obavezna")
	private String velicina;
	
	@NotNull(message = "Broj dostupnih je obavezan")
	@Min(value = 0, message = "Broj dostupnih ne moze biti negativan")
	private Double brojDostupnih;
	
	@NotNull(message = "ID proizvoda je obavezan")
	private Long proizvodId;
}
