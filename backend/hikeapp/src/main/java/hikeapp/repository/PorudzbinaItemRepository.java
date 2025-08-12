package hikeapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.KorpaItem;
import hikeapp.model.PorudzbinaItem;

@Repository
public interface PorudzbinaItemRepository extends JpaRepository<PorudzbinaItem, Long>{
	@Query(value = """
			SELECT * FROM porudzbina_item pi
			WHERE pi.vidljiv = true
				AND (:porudzbinaId IS NULL OR pi.porudzbina_id = :porudzbinaId)
				AND (:proizvodId IS NULL OR pi.proizvod_id = :proizvodId)
				AND (:velicinaId IS NULL OR pi.velicina_id = :velicinaId)
			ORDER BY pi.id DESC
			""",
			countQuery = """
			SELECT * FROM porudzbina_item pi
			WHERE pi.vidljiv = true
				AND (:porudzbinaId IS NULL OR pi.porudzbina_id = :porudzbinaId)
				AND (:proizvodId IS NULL OR pi.proizvod_id = :proizvodId)
				AND (:velicinaId IS NULL OR pi.velicina_id = :velicinaId)
			""",nativeQuery = true)
	Page<KorpaItem> search(@Param("porudzbinaId") Long porudzbinaId,
						@Param("proizvodId") Long proizvodId,
						@Param("velicinaId") Long velicinaId,
						Pageable pageable);
}
