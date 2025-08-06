package hikeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Kategorija;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Long>{
	Optional<Kategorija> findByNazivIgnoreCase(String naziv);
	List<Kategorija> findByVidljivTrue();
	
}
