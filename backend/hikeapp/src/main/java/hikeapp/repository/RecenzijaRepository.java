package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Recenzija;

@Repository
public interface RecenzijaRepository extends JpaRepository<Recenzija, Long> {
	List<Recenzija> findByProizvodIdAndVidljivTrue(Long proizvodId);
}
