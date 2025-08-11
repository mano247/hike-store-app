package hikeapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.KategorijaDTO;
import hikeapp.model.Kategorija;
import hikeapp.repository.KategorijaRepository;
import hikeapp.service.KategorijaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class KategorijaServiceImpl implements KategorijaService{
	
	@Autowired
	private final KategorijaRepository kategorijaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public KategorijaDTO getById(Long id) {
		Kategorija k = kategorijaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Kategorija nije pronadjena"));
		return toDTO(k);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<KategorijaDTO> getAll(Pageable pageable) {
		return kategorijaRepository.search(null, pageable)
				.map(this::toDTO);
	}

	@Override
	public KategorijaDTO create(KategorijaDTO dto) {
		Kategorija k = fromDTO(dto);
		if(k.getVidljiv() == null) k.setVidljiv(true);
		return toDTO(kategorijaRepository.save(k));
	}

	@Override
	public KategorijaDTO update(Long id, KategorijaDTO dto) {
		Kategorija existing = kategorijaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Kategorija nije pronadjena"));
		
		if(dto.getNaziv() != null) existing.setNaziv(dto.getNaziv());
		if(dto.getOpis() != null) existing.setOpis(dto.getOpis());
		if(dto.getVidljiv() != null) existing.setVidljiv(dto.getVidljiv());
		
		return toDTO(kategorijaRepository.save(existing));
	}

	@Override
	public void softDelete(Long id) {
		Kategorija existing = kategorijaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Kategorija nije pronadjena"));
		
		existing.setVidljiv(false);
		kategorijaRepository.save(existing);
		
	}

	@Override
	public void delete(Long id) {
		if(!kategorijaRepository.existsById(id)) {
			throw new EntityNotFoundException("Kategorija nije pronadjena");
		}
		kategorijaRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<KategorijaDTO> search(String q, Pageable pageable) {
		String query = (q != null && !q.isBlank()) ? q : null;
		return kategorijaRepository.search(query, pageable)
				.map(this::toDTO);
	}
	
	private KategorijaDTO toDTO(Kategorija k) {
		KategorijaDTO dto = new KategorijaDTO();
		dto.setNaziv(k.getNaziv());
		dto.setOpis(k.getOpis());
		dto.setVidljiv(k.getVidljiv());
		return dto;
	}
	
	private Kategorija fromDTO(KategorijaDTO dto) {
		Kategorija k = new Kategorija();
		k.setNaziv(dto.getNaziv());
		k.setOpis(dto.getOpis());
		k.setVidljiv(dto.getVidljiv());
		return k;
	}

}
