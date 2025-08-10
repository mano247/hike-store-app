package hikeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import hikeapp.dto.ProizvodDTO;
import hikeapp.service.ProizvodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proizvodi")
@RequiredArgsConstructor
public class ProizvodController {
	@Autowired
	private final ProizvodService proizvodService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProizvodDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(proizvodService.getById(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProizvodDTO>> getAll(Pageable pageable){
		return ResponseEntity.ok(proizvodService.getAll(pageable));
	}
	
	@PostMapping
	public ResponseEntity<ProizvodDTO> create(@Valid @RequestBody ProizvodDTO dto){
		ProizvodDTO created = proizvodService.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProizvodDTO> update(@PathVariable Long id, @Valid @RequestBody ProizvodDTO dto){
		return ResponseEntity.ok(proizvodService.update(id, dto));
	}
	
	@PatchMapping("/{id}/soft-delete")
	public ResponseEntity<Void> softDelete(@PathVariable Long id){
		proizvodService.softDelete(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		proizvodService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<ProizvodDTO>> search(
			@RequestParam(required = false) String ime,
			@RequestParam(required = false) String pol,
			@RequestParam(required = false) Long kategorijaId,
			@RequestParam(required = false) Long brendId,
			Pageable pageable
		){
		return ResponseEntity.ok(proizvodService.search(ime, pol, kategorijaId, brendId, pageable));
	}
}
