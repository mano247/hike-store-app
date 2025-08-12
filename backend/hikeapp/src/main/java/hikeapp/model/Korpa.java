package hikeapp.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Korpa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Korisnik korisnik;
	
	@OneToMany(mappedBy = "korpa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<KorpaItem> stavke;
	
	@Builder.Default
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal ukupnaCena=BigDecimal.ZERO;
	
	@Builder.Default
	@Column(nullable = false)
	private String status = "ACTIVE";
	
	@Builder.Default
	@Column(nullable = false)
	private Boolean vidljiv = true;
	
	@Builder.Default
	@Column(nullable = false)
	private OffsetDateTime kreirana = OffsetDateTime.now();
	
	@Builder.Default
	@Column(nullable = false)
	private OffsetDateTime azurirana = OffsetDateTime.now();
	
	void onUpdate() {
		this.azurirana = OffsetDateTime.now();
	}
}
