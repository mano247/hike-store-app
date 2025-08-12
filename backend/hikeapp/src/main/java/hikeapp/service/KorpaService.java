package hikeapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.KorpaDTO;
import hikeapp.dto.KorpaItemDTO;

public interface KorpaService {
	
	KorpaDTO getActive(Long korisnikId);
	
	KorpaDTO addOrIncrementItem(Long korisnikId, KorpaItemDTO dto);
	
	KorpaDTO setItemQuantity(Long korisnikId, KorpaItemDTO dto);
	
	KorpaDTO removeItem(Long korisnikId, KorpaItemDTO dto);
	
	void softDelete(Long korpaId);
	
	void delete(Long korpaId);
	
	Page<KorpaDTO> search(Long korisnikId, String status, Pageable pageable);
}
