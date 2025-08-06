package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Velicina;

@Repository
public interface VelicinaRepository extends JpaRepository<Velicina, Long>{
	List<Velicina> findByProizvodId(Long proizvodId);
}
