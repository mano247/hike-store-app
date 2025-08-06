package hikeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.PorudzbinaItem;

@Repository
public interface PorudzbinaItemRepository extends JpaRepository<PorudzbinaItem, Long>{
	List<PorudzbinaItem> findByPorudzbinaId(Long porudzbinaId);
}
