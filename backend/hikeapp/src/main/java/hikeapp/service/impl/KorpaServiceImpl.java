package hikeapp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.KorpaDTO;
import hikeapp.dto.KorpaItemDTO;
import hikeapp.model.Korisnik;
import hikeapp.model.Korpa;
import hikeapp.model.KorpaItem;
import hikeapp.repository.KorisnikRepository;
import hikeapp.repository.KorpaItemRepository;
import hikeapp.repository.KorpaRepository;
import hikeapp.repository.ProizvodRepository;
import hikeapp.repository.VelicinaRepository;
import hikeapp.service.KorpaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KorpaServiceImpl implements KorpaService{
	
	@Autowired
	private final KorpaRepository korpaRepository;
	
	@Autowired
	private final KorpaItemRepository korpaItemRepository;
	
	@Autowired
	private final KorisnikRepository korisnikRepository;
	
	@Autowired
	private final ProizvodRepository proizvodRepository;
	
	@Autowired
	private final VelicinaRepository velicinaRepository;

	@Override
	@Transactional(readOnly = true)
	public KorpaDTO getActive(Long korisnikId) {
		Page<Korpa> page = korpaRepository.search(korisnikId, "ACTIVE", PageRequest.of(0, 1));
		Korpa k = page.hasContent() ? page.getContent().get(0) : null;
		if(k == null) {
			return KorpaDTO.builder()
					.korsnikId(korisnikId)
					.status("ACTIVE")
					.stavke(List.of())
					.build();
		}
		List<KorpaItem> stavke = korpaItemRepository.search(k.getId(), null, null, Pageable.unpaged()).getContent();
		return toDTO(k, stavke);
	}

	@Override
	public KorpaDTO addOrIncrementItem(Long korisnikId, KorpaItemDTO dto) {
		Page<Korpa> page = korpaRepository.search(korisnikId, "ACTIVE", PageRequest.of(0, 1));
		Korpa korpa = page.hasContent() ? page.getContent().get(0) : createActiveCart(korisnikId);
		
		Page<KorpaItem> itemPage = korpaItemRepository.search(korpa.getId(), dto.getProizvodId(), dto.getVelicinaId(), PageRequest.of(0, 1));
		KorpaItem item = itemPage.hasContent() ? itemPage.getContent().get(0) : null;
		
		if(item == null) {
			item = KorpaItem.builder()
					.korpa(korpa)
					.proizvod(proizvodRepository.findById(dto.getProizvodId()).orElseThrow())
					.velicina(velicinaRepository.findById(dto.getVelicinaId()).orElseThrow())
					.kolicina(dto.getKolicina())
					.vidljiv(true)
					.build();
		} else {
			item.setKolicina(item.getKolicina() + dto.getKolicina());
		}
		korpaItemRepository.save(item);
		
		recalcTotal(korpa);
		korpaRepository.save(korpa);
		
		List<KorpaItem> stavke = korpaItemRepository.search(korpa.getId(), null, null, Pageable.unpaged()).getContent();
		return toDTO(korpa, stavke);
	}

	@Override
	public KorpaDTO setItemQuantity(Long korisnikId, KorpaItemDTO dto) {
		var korpaPage = korpaRepository.search(korisnikId, "ACTIVE", PageRequest.of(0, 1));
		Korpa korpa = korpaPage.getContent().stream()
		        .findFirst()
		        .orElseThrow(() -> new IllegalStateException("Aktivna korpa ne postoji"));

		var itemPage = korpaItemRepository.search(korpa.getId(), dto.getProizvodId(), dto.getVelicinaId(), PageRequest.of(0, 1));
		KorpaItem item = itemPage.getContent().stream()
		        .findFirst()
		        .orElse(null);
		
		if(dto.getKolicina() == null || dto.getKolicina() <= 0.0) {
			item.setVidljiv(false);
		} else {
			item.setKolicina(dto.getKolicina());
		}
		
		korpaItemRepository.save(item);
		
		recalcTotal(korpa);
		korpaRepository.save(korpa);
		
		List<KorpaItem> stavke = korpaItemRepository.search(korpa.getId(), null, null, Pageable.unpaged()).getContent();
		return toDTO(korpa, stavke);
	}

	@Override
	public KorpaDTO removeItem(Long korisnikId, KorpaItemDTO dto) {
		var korpaPage = korpaRepository.search(korisnikId, "ACTIVE", PageRequest.of(0, 1));
		Korpa korpa = korpaPage.getContent().stream()
		        .findFirst()
		        .orElseThrow(() -> new IllegalStateException("Aktivna korpa ne postoji"));

		var itemPage = korpaItemRepository.search(korpa.getId(), dto.getProizvodId(), dto.getVelicinaId(), PageRequest.of(0, 1));
		KorpaItem item = itemPage.getContent().stream()
		        .findFirst()
		        .orElse(null);
		
		item.setVidljiv(false);
		korpaItemRepository.save(item);
		
		recalcTotal(korpa);
		korpaRepository.save(korpa);
		
		List<KorpaItem> stavke = korpaItemRepository.search(korpa.getId(), null, null, Pageable.unpaged()).getContent();
		return toDTO(korpa, stavke);
	}

	@Override
	public void softDelete(Long korpaId) {
		Korpa korpa = korpaRepository.findById(korpaId).orElseThrow();
		korpa.setVidljiv(false);
		korpaRepository.save(korpa);
	}

	@Override
	public void delete(Long korpaId) {
		korpaRepository.deleteById(korpaId);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<KorpaDTO> search(Long korisnikId, String status, Pageable pageable) {
		return korpaRepository.search(korisnikId, status, pageable)
				.map(k -> toDTO(k, korpaItemRepository.search(k.getId(), null, null, Pageable.unpaged()).getContent()));
	}
	
	private Korpa createActiveCart(Long korisnikId) {
		Korisnik k = korisnikRepository.findById(korisnikId).orElseThrow();
		Korpa korpa = Korpa.builder()
				.korisnik(k)
				.status("ACTIVE")
				.ukupnaCena(BigDecimal.ZERO)
				.vidljiv(true)
				.build();
		return korpaRepository.save(korpa);
	}
	
	private void recalcTotal(Korpa korpa) {
		List<KorpaItem> stavke = korpaItemRepository.search(korpa.getId(), null, null, Pageable.unpaged()).getContent();
		BigDecimal total = BigDecimal.ZERO;
		for(KorpaItem ki : stavke) {
			BigDecimal cena = ki.getProizvod().getCena();
			total = total.add(cena.multiply(BigDecimal.valueOf(ki.getKolicina())));
		}
		korpa.setUkupnaCena(total);
	}
	
	private KorpaDTO toDTO(Korpa k, List<KorpaItem> stavke) {
		return KorpaDTO.builder()
				.korsnikId(k.getKorisnik().getId())
				.status(k.getStatus())
				.stavke(stavke.stream().map(this::toDTO).collect(Collectors.toList()))
				.build();
	}
	
	private KorpaItemDTO toDTO(KorpaItem ki) {
		return KorpaItemDTO.builder()
				.proizvodId(ki.getProizvod().getId())
				.velicinaId(ki.getVelicina().getId())
				.kolicina(ki.getKolicina())
				.build();
	}
}
