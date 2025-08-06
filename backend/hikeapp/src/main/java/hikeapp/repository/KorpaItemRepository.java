package hikeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.KorpaItem;

@Repository
public interface KorpaItemRepository extends JpaRepository<KorpaItem, Long>{
	List<KorpaItem> findByKorisnikId(Long korisnikId);
	Optional<KorpaItem> findByKorisnikIdAndProizvodId(Long korisnikId, Long proizvodId);
}
