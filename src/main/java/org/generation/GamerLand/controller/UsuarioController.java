package org.generation.GamerLand.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.GamerLand.model.Usuario;
import org.generation.GamerLand.model.UsuarioLogin;
import org.generation.GamerLand.repository.UsuarioRepository;
import org.generation.GamerLand.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins="*", allowedHeaders="*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping("/all")
	public ResponseEntity <List<Usuario>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	};
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastraUsuario(@Valid @RequestBody Usuario usuario){
		return usuarioService.cadastrarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	};
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> logaUsuario(@RequestBody Optional<UsuarioLogin> user){
		return usuarioService.autenticarUsuario(user)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){
        return usuarioService.atualizarUsuario(usuario)
            .map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}
