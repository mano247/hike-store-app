package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Popust;

@Repository
public interface PopustRepository extends JpaRepository<Popust, Long>{
	List<Popust> findByProizvodId(Long proizvodId);
}
