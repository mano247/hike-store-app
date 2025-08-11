package hikeapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.ProizvodDTO;
import hikeapp.model.Brend;
import hikeapp.model.Kategorija;
import hikeapp.model.Proizvod;
import hikeapp.repository.BrendRepository;
import hikeapp.repository.KategorijaRepository;
import hikeapp.repository.ProizvodRepository;
import hikeapp.service.ProizvodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProizvodServiceImpl implements ProizvodService{
	@Autowired
	private final ProizvodRepository proizvodRepository;
	@Autowired
	private final BrendRepository brendRepository;
	@Autowired
	private final KategorijaRepository kategorijaRepository;

	@Override
	public ProizvodDTO create(ProizvodDTO dto) {
		Proizvod entity = mapToEntity(dto, new Proizvod());
		
		Brend brend = brendRepository.findById(dto.getBrendId()).orElseThrow(() -> new EntityNotFoundException("Brend nije pronadjen"));
		
		Kategorija kategorija = kategorijaRepository.findById(dto.getKategorijaId()).orElseThrow(() -> new EntityNotFoundException("Kategorija nije pronadjena"));
		
		entity.setBrend(brend);
		entity.setKategorija(kategorija);
		
		if(entity.getVidljiv() == null) entity.setVidljiv(true);
		if(entity.getDostupno() == null) entity.setDostupno(true);
		
		Proizvod saved = proizvodRepository.save(entity);
		return mapToDTO(saved);
		
	}

	@Override
	public ProizvodDTO update(Long id, ProizvodDTO dto) {
		
		Proizvod entity = proizvodRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Proizvod nije pronadjen"));
		
		mapToEntity(dto, entity);
		
		if(dto.getBrendId() != null) {
			Brend brend = brendRepository.findById(dto.getBrendId())
					.orElseThrow(() -> new EntityNotFoundException("Brend nije pronadjen"));
			entity.setBrend(brend);
		}
		
		if(dto.getKategorijaId() != null) {
			Kategorija kategorija = kategorijaRepository.findById(dto.getKategorijaId())
					.orElseThrow(() -> new EntityNotFoundException("Kategorija nije pronadjen"));
			entity.setKategorija(kategorija);
		}
		
		Proizvod saved = proizvodRepository.save(entity);
		return mapToDTO(saved);
		
	}

	@Override
	public void softDelete(Long id) {
		Proizvod entity = proizvodRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Proizvod nije pronadjen"));
		
		entity.setVidljiv(false);
		
		proizvodRepository.save(entity);
		
	}

	@Override
	public void delete(Long id) {
		if(!proizvodRepository.existsById(id)) {
			throw new EntityNotFoundException("Proizvod nije pronadjen");
		}
		proizvodRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public ProizvodDTO getById(Long id) {
		Proizvod entity = proizvodRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Proizvod nije pronadjen"));
		return mapToDTO(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProizvodDTO> getAll(Pageable pageable) {
		return proizvodRepository.findAll(pageable)
				.map(this::mapToDTO);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ProizvodDTO> search(String ime, String pol, Long kategorijaId, Long brendId, Pageable pageable) {
		String qIme = (ime != null && !ime.isBlank()) ? ime.toLowerCase() : null;
		String qPol = (pol != null && !pol.isBlank() ? pol : null);
		
		Page<Proizvod> page = proizvodRepository.search(qIme, qPol, kategorijaId, brendId, pageable);
		return page.map(this::mapToDTO);
	}
	
	private Proizvod mapToEntity(ProizvodDTO dto, Proizvod entity) {
		if(dto.getIme() != null) entity.setIme(dto.getIme());
		if(dto.getOpis() != null) entity.setOpis(dto.getOpis());
		if(dto.getCena() != null) entity.setCena(dto.getCena());
		if(dto.getPol() != null) entity.setPol(dto.getPol());
		if(dto.getSifra() != null) entity.setSifra(dto.getSifra());
		if(dto.getSlikaURL() != null) entity.setSlikaURL(dto.getSlikaURL());
		if(dto.getDostupno() != null) entity.setDostupno(dto.getDostupno());
		if(dto.getVidljiv() != null) entity.setVidljiv(dto.getVidljiv());
		return entity;
	}
	
	private ProizvodDTO mapToDTO(Proizvod p) {
		ProizvodDTO dto = new ProizvodDTO();
		dto.setIme(p.getIme());
		dto.setOpis(p.getOpis());
		dto.setCena(p.getCena());
		dto.setPol(p.getPol());
		dto.setSifra(p.getSifra());
		dto.setSlikaURL(p.getSlikaURL());
		dto.setDostupno(p.getDostupno());
		dto.setVidljiv(p.getVidljiv());
		dto.setBrendId(p.getBrend() != null ? p.getBrend().getId() : null);
		dto.setKategorijaId(p.getKategorija() != null ? p.getKategorija().getId() : null);
		return dto;
	}

	

}
