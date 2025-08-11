package hikeapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.Kategorija;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Long>{
	
	@Query(value = """
		SELECT * FROM kategorija k
		WHERE k.vidljiv = true
			AND (:q IS NULL OR k.naziv ILIKE '%' || :q || '%')
		""",
		countQuery = """
		SELECT count(*) FROM kategorija k
		WHERE k.vidljiv = true
			AND (:q IS NULL OR k.naziv ILIKE '%' || :q || '%')	
		""",
		nativeQuery = true)
	
	Page<Kategorija> search(@Param("q") String q, Pageable pageable);
}
