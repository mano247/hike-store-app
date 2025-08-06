package hikeapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long>{
	Optional<Korisnik> findByEmail(String email);
	boolean existsByEmail(String email);
}
