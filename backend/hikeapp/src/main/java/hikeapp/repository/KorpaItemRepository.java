package hikeapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.KorpaItem;

@Repository
public interface KorpaItemRepository extends JpaRepository<KorpaItem, Long>{
	@Query(value = """
			SELECT * FROM korpa_item ki
			WHERE ki.vidljiv = true
				AND (:korpaId IS NULL OR ki.korpa_id = :korpaId)
				AND (:proizvodId IS NULL OR ki.proizvod_id = :proizvodId)
				AND (:velicinaId IS NULL OR ki.velicina_id = :velicinaId)
			ORDER BY ki.id DESC
			""",
			countQuery = """
			SELECT count(*) FROM korpa_item ki
			WHERE ki.vidljiv = true
				AND (:korpaId IS NULL OR ki.korpa_id = :korpaId)
				AND (:proizvodId IS NULL OR ki.proizvod_id = :proizvodId)
				AND (:velicinaId IS NULL OR ki.velicina_id = :velicinaId)
			""",nativeQuery = true)
	Page<KorpaItem> search(@Param("korpaId") Long korpaId,
						@Param("proizvodId") Long proizvodId,
						@Param("velicinaId") Long velicinaId,
						Pageable pageable);
}
