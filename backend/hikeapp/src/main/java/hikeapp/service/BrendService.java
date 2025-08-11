package hikeapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.BrendDTO;

public interface BrendService {
	
	BrendDTO getById(Long id);
	
	Page<BrendDTO> getAll(Pageable pageable);
	
	BrendDTO create(BrendDTO dto);
	
	BrendDTO update(Long id, BrendDTO dto);
	
	void softDelete(Long id);
	
	void delete(Long id);
	
	Page<BrendDTO> search(String q, Pageable pageable);
	
}
