package hikeapp.dto;

import java.time.LocalDateTime;

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
public class PopustDTO {
	@NotNull(message = "Proizvod je obavezan")
	private Long proizvodId;
	
	@NotNull(message = "Nova cena je obavezna")
	@Positive(message = "Nova cena mora biti pozitivna")
	private Double novaCena;
	
	@NotNull(message = "Datum pocetka je obavezan")
	private LocalDateTime aktivanOd;
	
	@NotNull(message = "Datum zavrsetka je obavezan")
	private LocalDateTime aktivanDo;
	
	private String opis;
}
