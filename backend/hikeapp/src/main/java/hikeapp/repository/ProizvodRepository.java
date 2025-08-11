package hikeapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.Proizvod;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Long>{
	
	@Query(value = """
			  SELECT * FROM proizvod p
			  WHERE p.vidljiv = true
			    AND (:pol IS NULL OR p.pol = :pol)
			    AND (:kategorijaId IS NULL OR p.kategorija_id = :kategorijaId)
			    AND (:brendId IS NULL OR p.brend_id = :brendId)
			    AND (:ime IS NULL OR LOWER(p.ime) LIKE '%' || :ime || '%')
			""",
			countQuery = """
			  SELECT count(*) FROM proizvod p
			  WHERE p.vidljiv = true
			    AND (:pol IS NULL OR p.pol = :pol)
			    AND (:kategorijaId IS NULL OR p.kategorija_id = :kategorijaId)
			    AND (:brendId IS NULL OR p.brend_id = :brendId)
			    AND (:ime IS NULL OR LOWER(p.ime) LIKE '%' || :ime || '%')
			""",
			nativeQuery = true)
	Page<Proizvod> search(
			@Param("ime") String ime,
			@Param("pol") String pol,
			@Param("kategorijaId") Long kategorijaId,
			@Param("brendId") Long brendId,
			Pageable pageable
			
		);

}
