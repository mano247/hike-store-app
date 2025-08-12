package hikeapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hikeapp.dto.PorudzbinaItemDTO;

public interface PorudzbinaItemService {
	Page<PorudzbinaItemDTO> search(Long porudzbinaId, Long proizvodId, Long velicinaId, Pageable pageable);
}
