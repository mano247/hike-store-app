package hikeapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.KorpaItemDTO;

public interface KorpaItemService {
	Page<KorpaItemDTO> search(Long korpaId, Long proizvodId, Long velicinaId, Pageable pageable);
}
