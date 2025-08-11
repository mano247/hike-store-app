package hikeapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.KategorijaDTO;

public interface KategorijaService {
	
	KategorijaDTO getById(Long id);
	
	Page<KategorijaDTO> getAll(Pageable pageable);
	
	KategorijaDTO create(KategorijaDTO dto);
	
	KategorijaDTO update(Long id, KategorijaDTO dto);
	
	void softDelete(Long id);
	
	void delete(Long id);
	
	Page<KategorijaDTO> search(String q, Pageable pageable);
}
