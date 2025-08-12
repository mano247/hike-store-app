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
public class KorpaDTO {
	
	@NotNull(message = "Id korisnika je obavezan")
	private Long korsnikId;
	
	@Pattern(
		regexp = "ACTIVE|CHECKED_OUT|ABANDONED",
		message = "Status mora biti jedna od sledecih vrednosti: ACTIVE, CHECKED_OUT, ABANDONED"
			)
	private String status;
	
	private List<KorpaItemDTO> stavke;
}
