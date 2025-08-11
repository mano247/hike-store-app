package hikeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hikeapp.dto.KategorijaDTO;
import hikeapp.service.KategorijaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/kategorije")
@RequiredArgsConstructor
public class KategorijaController {
	@Autowired
	private final KategorijaService kategorijaService;
	
	@GetMapping
	public Page<KategorijaDTO> getAll(Pageable pageable){
		return kategorijaService.getAll(pageable);
	}
	
	@GetMapping("/{id}")
	public KategorijaDTO getById(@PathVariable Long id) {
		return kategorijaService.getById(id);
	}
	
	@PostMapping
	public KategorijaDTO create(@Valid @RequestBody KategorijaDTO dto) {
		return kategorijaService.create(dto);
	}
	
	@PutMapping("/{id}")
	public KategorijaDTO update(@PathVariable Long id, @Valid @RequestBody KategorijaDTO dto) {
		return kategorijaService.update(id, dto);
	}
	
	@PatchMapping("/{id}/soft_delete")
	public void softDelete(@PathVariable Long id) {
		kategorijaService.softDelete(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		kategorijaService.delete(id);
	}
	
	@GetMapping("/search")
	public Page<KategorijaDTO> search(@RequestParam(required = false) String q, Pageable pageable){
		return kategorijaService.search(q, pageable);
	}
}
