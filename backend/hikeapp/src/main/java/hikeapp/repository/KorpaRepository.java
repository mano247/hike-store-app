package hikeapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.Korpa;

@Repository
public interface KorpaRepository extends JpaRepository<Korpa, Long>{
	@Query(value = """
			SELECT * FROM korpa k
			WHERE k.vidljiv = true
				AND (:korisnikId IS NULL OR k.korisnik_id = :korisnikId)
				AND (:status IS NULL OR k.status = :status)
			ORDER BY k.id DESC
			""",
			countQuery = """
			SELECT count(*) FROM korpa k
			WHERE k.vidljiv = true
				AND (:korisnikId IS NULL OR k.korisnik_id = :korisnikId)
				AND (:status IS NULL OR k.status = :status)
			""",nativeQuery = true)
	Page<Korpa> search(@Param("korisnikId") Long korisnikId,
						@Param("status") String status,
						Pageable pageable);
	
}
