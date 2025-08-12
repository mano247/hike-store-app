package hikeapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hikeapp.dto.KorpaItemDTO;
import hikeapp.repository.KorpaItemRepository;
import hikeapp.service.KorpaItemService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KorpaItemServiceImpl implements KorpaItemService{

	@Autowired
	private final KorpaItemRepository korpaItemRepository;

	@Override
	public Page<KorpaItemDTO> search(Long korpaId, Long proizvodId, Long velicinaId, Pageable pageable) {
		return korpaItemRepository.search(korpaId, proizvodId, velicinaId, pageable)
				.map(e -> KorpaItemDTO.builder()
						.proizvodId(e.getProizvod().getId())
						.velicinaId(e.getVelicina().getId())
						.kolicina(e.getKolicina())
						.build());
	}
	
	
}
