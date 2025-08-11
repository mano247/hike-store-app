package hikeapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.BrendDTO;
import hikeapp.model.Brend;
import hikeapp.repository.BrendRepository;
import hikeapp.service.BrendService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BrendServiceImpl implements BrendService{
	@Autowired
	private final BrendRepository brendRepository;

	@Override
	@Transactional(readOnly = true)
	public BrendDTO getById(Long id) {
		Brend b = brendRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Brend nije pronadjen"));
		return toDTO(b);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BrendDTO> getAll(Pageable pageable) {
		return brendRepository.search(null, pageable)
				.map(this::toDTO);
	}

	@Override
	public BrendDTO create(BrendDTO dto) {
		Brend b = fromDTO(dto);
		if(b.getVidljiv() == null) b.setVidljiv(true);
		Brend saved = brendRepository.save(b);
		return toDTO(saved);
	}

	@Override
	public BrendDTO update(Long id, BrendDTO dto) {
		Brend existing = brendRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Brend nije pronadjen"));
		
		if(dto.getNaziv() != null) existing.setNaziv(dto.getNaziv());
		if(dto.getOpis() != null) existing.setOpis(dto.getOpis());
		if(dto.getVidljiv() != null) existing.setVidljiv(dto.getVidljiv());
		
		Brend saved = brendRepository.save(existing);
		return toDTO(saved);
	}

	@Override
	public void softDelete(Long id) {
		Brend existing = brendRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Brend nije prondajen"));
		existing.setVidljiv(false);
		brendRepository.save(existing);
		
	}

	@Override
	public void delete(Long id) {
		if(!brendRepository.existsById(id)) {
			throw new EntityNotFoundException("Brend nije pronadjen");
		}
		brendRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BrendDTO> search(String q, Pageable pageable) {
		String query = (q != null && !q.isBlank()) ? q : null;
		return brendRepository.search(query, pageable)
				.map(this::toDTO);
	}
	
	private BrendDTO toDTO(Brend b) {
		BrendDTO dto = new BrendDTO();
		dto.setNaziv(b.getNaziv());
		dto.setOpis(b.getOpis());
		dto.setVidljiv(b.getVidljiv());
		return dto;
	}
	
	private Brend fromDTO(BrendDTO dto) {
		Brend b = new Brend();
		b.setNaziv(dto.getNaziv());
		b.setOpis(dto.getOpis());
		b.setVidljiv(dto.getVidljiv());
		return b;
	}

}
