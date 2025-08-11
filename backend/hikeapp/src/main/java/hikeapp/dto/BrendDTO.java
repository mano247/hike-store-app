package hikeapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrendDTO {
	@NotBlank(message = "Naziv brenda je obavezan")
	private String naziv;
	
	private String opis;
	private String slikaURL;
	
	@Builder.Default
	private Boolean vidljiv = true;
}
