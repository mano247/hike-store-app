package hikeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Brend;

@Repository
public interface BrendRepository extends JpaRepository<Brend, Long>{
	Optional<Brend> findByNazivIgnoreCase(String naziv);
	List<Brend> findByVidljivTrue();
}
