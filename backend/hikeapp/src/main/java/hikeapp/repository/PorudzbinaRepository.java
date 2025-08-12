package hikeapp.repository;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.KorpaItem;
import hikeapp.model.Porudzbina;

@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Long>{
	@Query(value = """
			SELECT * FROM porudzbina p
			WHERE p.vidljiv = true
				AND (:korisnikId IS NULL OR p.korisnik_id = :korisnikId)
				AND (:status IS NULL OR p.status ILIKE CONCAT('%', :status, '%'))
				AND (:datumOd IS NULL OR p.vreme >= :datumOd)
				AND (:datumDo IS NULL OR p.vreme < (:datumDo + INTERVAL '1 day'))
			ORDER BY p.vreme DESC, p.id DESC
			""",
			countQuery = """
			SELECT * FROM porudzbina p
			WHERE p.vidljiv = true
				AND (:korisnikId IS NULL OR p.korisnik_id = :korisnikId)
				AND (:status IS NULL OR p.status ILIKE CONCAT('%', :status, '%'))
				AND (:datumOd IS NULL OR p.vreme >= :datumOd)
				AND (:datumDo IS NULL OR p.vreme < (:datumDo + INTERVAL '1 day'))
			""",nativeQuery = true)
	Page<KorpaItem> search(@Param("korisnikId") Long korisnikId,
						@Param("status") String status,
						@Param("datumOd") OffsetDateTime datumOd,
						@Param("datumDo") OffsetDateTime datumDo,
						Pageable pageable);
}
