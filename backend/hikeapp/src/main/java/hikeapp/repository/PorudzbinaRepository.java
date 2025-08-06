package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Porudzbina;

@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Long>{
	List<Porudzbina> findByKorisnikId(Long korisnikId);
	List<Porudzbina> findByStatus(String status);
	List<Porudzbina> findByStatusIgnoreCase(String status);
	List<Porudzbina> findByStatusAndKorisnikId(String status, Long korisnikId);
}
