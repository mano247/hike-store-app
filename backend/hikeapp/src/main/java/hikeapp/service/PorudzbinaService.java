package hikeapp.service;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.PorudzbinaDTO;

public interface PorudzbinaService {
	
	PorudzbinaDTO checkout(PorudzbinaDTO dto);
	
	PorudzbinaDTO findById(Long id);
	
	void updateStatus(Long id, String status);
	
	void softDelete(Long id);
	
	void delete(Long id);
	
	Page<PorudzbinaDTO> search(Long korisnikId, String status, OffsetDateTime datumOd, OffsetDateTime datumDo, Pageable pageable);
}
