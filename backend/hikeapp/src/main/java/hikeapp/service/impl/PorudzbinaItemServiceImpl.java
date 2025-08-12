package hikeapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.PorudzbinaItemDTO;
import hikeapp.repository.PorudzbinaItemRepository;
import hikeapp.service.PorudzbinaItemService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PorudzbinaItemServiceImpl implements PorudzbinaItemService{
	
	@Autowired
	private final PorudzbinaItemRepository porudzbinaItemRepository;
	
	@Override
	public Page<PorudzbinaItemDTO> search(Long porudzbinaId, Long proizvodId, Long velicinaId, Pageable pageable) {
		return porudzbinaItemRepository.search(porudzbinaId, proizvodId, velicinaId, pageable)
				.map(e -> PorudzbinaItemDTO.builder()
						.velicinaId(e.getVelicina().getId())
						.kolicina(e.getKolicina())
						.build()
						);
	}

}
