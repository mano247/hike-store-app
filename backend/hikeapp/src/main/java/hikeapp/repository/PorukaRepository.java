package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Poruka;

@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long>{
	List<Poruka> findByKorisnikIdAndVidljivTrue(Long korisnikId); 
}
