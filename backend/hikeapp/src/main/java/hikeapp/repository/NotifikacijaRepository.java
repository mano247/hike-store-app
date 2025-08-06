package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Notifikacija;

@Repository
public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Long>{
	List<Notifikacija> findbyKorisnikIdOrderByVremeSlanjaDesc(Long korisnikId);
}
