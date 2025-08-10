package hikeapp.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.ProizvodDTO;

public interface ProizvodService {
	ProizvodDTO create(ProizvodDTO dto);
	
	ProizvodDTO update(Long id, ProizvodDTO dto);
	
	void softDelete(Long id);
	
	void delete(Long id);
	
	ProizvodDTO getById(Long id);
	
	Page<ProizvodDTO> getAll(Pageable pageable);
 	
	Page<ProizvodDTO> search(String ime, String pol, Long kategorijaId, Long brendId, Pageable pageable);
}
