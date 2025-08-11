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

import hikeapp.dto.BrendDTO;
import hikeapp.service.BrendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/brendovi")
@RequiredArgsConstructor
public class BrendController {
	@Autowired
	private final BrendService brendService;
	
	@GetMapping
	public Page<BrendDTO> getAll(Pageable pageable){
		return brendService.getAll(pageable);
	}
	
	@GetMapping("/{id}")
	public BrendDTO getById(@PathVariable Long id) {
		return brendService.getById(id);
	}
	
	@PostMapping
	public BrendDTO create(@Valid @RequestBody BrendDTO dto) {
		return brendService.create(dto);
	}
	
	@PutMapping("/{id}")
	public BrendDTO update(@PathVariable Long id, @Valid @RequestBody BrendDTO dto) {
		return brendService.update(id, dto);
	}
	
	@PatchMapping("/{id}/soft_delete")
	public void softDelete(@PathVariable Long id) {
		brendService.softDelete(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		brendService.delete(id);
	}
	
	@GetMapping("/search")
	public Page<BrendDTO> search(@RequestParam(required = false) String q, Pageable pageable){
		return brendService.search(q, pageable);
	}
	
}
