package hikeapp.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.PorudzbinaDTO;
import hikeapp.dto.PorudzbinaItemDTO;
import hikeapp.model.Korisnik;
import hikeapp.model.Korpa;
import hikeapp.model.KorpaItem;
import hikeapp.model.Porudzbina;
import hikeapp.model.PorudzbinaItem;
import hikeapp.repository.KorisnikRepository;
import hikeapp.repository.KorpaItemRepository;
import hikeapp.repository.KorpaRepository;
import hikeapp.repository.PorudzbinaItemRepository;
import hikeapp.repository.PorudzbinaRepository;
import hikeapp.service.PorudzbinaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PoridzbinaServiceImpl implements PorudzbinaService{
	@Autowired
	private final PorudzbinaRepository porudzbinaRepository;
	
	@Autowired
	private final PorudzbinaItemRepository porudzbinaItemRepository;
	
	@Autowired
	private final KorpaRepository korpaRepository;
	
	@Autowired
	private final KorpaItemRepository korpaItemRepository;
	
	@Autowired
	private final KorisnikRepository korisnikRepository;
	
	
	@Override
	public PorudzbinaDTO checkout(PorudzbinaDTO dto) {
		Long korisnikId = dto.getKorisnikId();
		
		Korpa korpa = korpaRepository.search(korisnikId, "ACTIVE", PageRequest.of(0, 1))
				.map(p -> p.getContent().get(0).orElseThrow(() -> new IllegalStateException("Aktivna korpa ne postoji")));
				
		var stavke = korpaItemRepository.search(korpa.getId(), null, null, Pageable.unpaged()).getContent();
		
		if(stavke.isEmpty()) throw new IllegalStateException("Korpa je prazna");
		
		Korisnik k = korisnikRepository.findById(korisnikId).orElseThrow();
		String adresa = composeAdresa(dto, k);
//		String ulica = dto.getUlica() != null ? dto.getUlica() : k.getAdresa();
//		String broj = dto.getBroj() != null ? dto.getBroj() : k.getBrojTelefona();
		String grad = dto.getGrad() != null ? dto.getGrad() : k.getGrad();
		String postanski = dto.getPostanskiBroj() != null ? dto.getPostanskiBroj() : k.getPostanskiBroj();
		
		Porudzbina p = Porudzbina.builder()
				.korisnik(k)
				.status(dto.getStatus() == null ? "KREIRANO" : dto.getStatus())
				.vreme(OffsetDateTime.now())
				.adresa(ulica)
				.grad(grad)
				.postanskiBroj(postanski)
				.drzava(k.getDrzava())
				.korpaId(korpa.getId())
				.vidljiv(true)
				.build();
		
		BigDecimal ukupno = BigDecimal.ZERO;
		for(KorpaItem ki : stavke) {
			BigDecimal cena = ki.getProizvod().getCena();
			ukupno = ukupno.add(cena.multiply(BigDecimal.valueOf(ki.getKolicina())));
			PorudzbinaItem pi = PorudzbinaItem.builder()
					.porudzbina(p)
//					.proizvod(ki.getProizvod())
					.velicina(ki.getVelicina())
					.kolicina(ki.getKolicina())
					.cenaItema(cena)
					.vidljiv(true)
					.build();
			p.getProizvodi().add(pi);
		}
		
		p.setUkupnaCena(ukupno);
		p = porudzbinaRepository.save(p);
		
		if(p.getProizvodi() == null || p.getProizvodi().isEmpty()){
			var items = stavke.stream().map(ki -> PorudzbinaItem.builder()
					.porudzbina(p)
//					.proizvod(ki.getProizvod())
					.velicina(ki.getVelicina())
					.kolicina(ki.getKolicina())
					.cenaItema(ki.getProizvod.getCena())
					.vidljiv(true)
					.build()
					).toList();
			porudzbinaItemRepository.saveAll(items);
					
		}
		
		korpa.setStatus("CHECKED_OUT");
		korpaRepository.save(korpa);
		
		var itemsPage = porudzbinaItemRepository.search(p.getId(), null, null, Pageable.unpaged());
		return toDTOFull(p, itemsPage.getContent());
	}

	@Override
	@Transactional(readOnly = true)
	public PorudzbinaDTO findById(Long id) {
		Porudzbina p = porudzbinaRepository.findById(id).orElseThrow();
		var items = porudzbinaItemRepository.search(p.getId(), null, null, Pageable.unpaged()).getContent();
		return toDTOFull(p, items);
	}

	@Override
	public void updateStatus(Long id, String status) {
		Porudzbina p = porudzbinaRepository.findById(id).orElseThrow();
		p.setStatus(status);
		porudzbinaRepository.save(p);
		
	}

	@Override
	public void softDelete(Long id) {
		Porudzbina p = porudzbinaRepository.findById(id).orElseThrow();
		p.setVidljiv(false);
		porudzbinaRepository.save(p);
	}

	@Override
	public void delete(Long id) {
		porudzbinaRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PorudzbinaDTO> search(Long korisnikId, String status, OffsetDateTime datumOd, OffsetDateTime datumDo,
			Pageable pageable) {
		return porudzbinaRepository.search(korisnikId, status, datumOd, datumDo, pageable)
				.map(this::toDTOBasic);
	}
	
	private PorudzbinaDTO toDTOBasic(Porudzbina p) {
		return PorudzbinaDTO.builder()
				.korisnikId(p.getKorisnik().getId())
				.status(p.getStatus())
				.vreme(p.getVreme())
				.ukupnaCena(p.getUkupnaCena())
				.ulica(extractUlica(p.getAdresa()))
				.broj(extractBroj(p.getAdresa()))
				.grad(p.getGrad())
				.postanskiBroj(p.getPostanskiBroj())
				.build();
	}
	
	private PorudzbinaDTO toDTOFull(Porudzbina p, List<PorudzbinaItem> items) {
        List<PorudzbinaItemDTO> dtoItems = items.stream().map(it -> PorudzbinaItemDTO.builder()
                .velicinaId(it.getVelicina().getId())
                .kolicina(it.getKolicina())
                .cenaItema(it.getCenaItema())
                .build()).toList();

        return PorudzbinaDTO.builder()
                .korisnikId(p.getKorisnik().getId())
                .status(p.getStatus())
                .vreme(p.getVreme())
                .ukupnaCena(p.getUkupnaCena())
                .ulica(extractUlica(p.getAdresa()))
                .broj(extractBroj(p.getAdresa()))
                .grad(p.getGrad())
                .postanskiBroj(p.getPostanskiBroj())
                .proizvodi(dtoItems)
                .build();
    }

    private String composeAdresa(PorudzbinaDTO dto, Korisnik k) {
        String ulica = dto.getUlica() != null ? dto.getUlica() : (k.getAdresa() != null ? k.getAdresa() : "");
        String broj = dto.getBroj() != null ? dto.getBroj() : "";
        return (ulica + " " + broj).trim();
    }

    private String extractUlica(String adresa) {
        if (adresa == null) return null;
        String[] parts = adresa.trim().split("\\s+", 2);
        return parts.length == 2 ? parts[0] : adresa;
    }

    private String extractBroj(String adresa) {
        if (adresa == null) return null;
        String[] parts = adresa.trim().split("\\s+", 2);
        return parts.length == 2 ? parts[1] : null;
    }

}
