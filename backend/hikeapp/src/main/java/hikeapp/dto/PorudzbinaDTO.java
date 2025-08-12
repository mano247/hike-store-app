package hikeapp.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PorudzbinaDTO {
	
	@NotNull(message = "ID korisnika je obavezan")
	private Long korisnikId;
	
	@Pattern(
			regexp = "KREIRANO|NA_CEKANJU|POSLATO|ISPORUCENO|OTKAZANO",
			message = "Status mora biti jedna od sledecih vrednosti: KREIRANO, NA_CEKANJU, POSLATO, ISPORUCENO, OTKAZANO"
	)	
	private String status;
	
	private String ulica;
	private String broj;
	private String grad;
	private String postanskiBroj;
	
	private List<PorudzbinaItemDTO> proizvodi;
}
