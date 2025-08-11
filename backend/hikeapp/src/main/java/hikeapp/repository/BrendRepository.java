package hikeapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hikeapp.model.Brend;

@Repository
public interface BrendRepository extends JpaRepository<Brend, Long>{
	
	@Query(value = """
		SELECT * FROM brend b
		WHERE b.vidljiv = true
			AND (:q IS NULL OR b.naziv ILIKE '%' || :q || '%')
		""",
		countQuery = """
		SELECT count(*) FROM brend b
		WHERE b.vidljiv = true
				AND (:q IS NULL OR b.naziv ILIKE '%' || :q || '%')
		""",
		nativeQuery = true)
	
	Page<Brend> search(@Param("q") String q, Pageable pageable);
	
}
