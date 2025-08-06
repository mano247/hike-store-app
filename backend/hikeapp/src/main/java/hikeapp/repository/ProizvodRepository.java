package hikeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hikeapp.model.Proizvod;
import java.util.List;

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Long>{
	List<Proizvod> findByVidljivTrue();
	List<Proizvod> findByNazivContainingIgnoreCaseAndVidljivTrue(String naziv);
	List<Proizvod> findByPolAndVidljivTrue(String pol);
	List<Proizvod> findByBrendIdAndVidljivTrue(Long brendId);
	List<Proizvod> findByKategorijaIdAndVidljivTrue(Long kategorijaId);
	List<Proizvod> findByPolAndKategorijaIdAndBrendIdAndVidljivTrue(String pol, Long kategorijaId, Long brendId);

}
