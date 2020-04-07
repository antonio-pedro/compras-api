package com.pessoal.compras.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.compras.event.RecursoCriadoEvent;
import com.pessoal.compras.model.Permissao;
import com.pessoal.compras.model.Usuario;
import com.pessoal.compras.repository.PermissaoRepository;
import com.pessoal.compras.repository.UsuarioRepository;
import com.pessoal.compras.service.UsuarioService;

	@Controller
	@RestController // Esta anotação já transforma meu retorno em formato Json
	@RequestMapping("/usuarios") // Mapeando Requisição para usuarios
	public class UsuarioResource {

		@Autowired
		private UsuarioRepository usuarioRepository;
		
		@Autowired
		private PermissaoRepository permissaoRepository;

		@Autowired
		private UsuarioService usuarioService;

		@Autowired
		private ApplicationEventPublisher publisher;
		
		@PostMapping // mapea as requisições POST para este método
		@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
		public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {			
			Usuario usuarioSalvo = usuarioService.salvar(usuario); // salva os usuarios que estão no repositório
			publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getId()));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);// retorna o Status 201 created e usuarioSalvo retorna o Location
		}
		
		@GetMapping("/{codigo}")
		@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
		public ResponseEntity<Usuario> buscarPeloCodigo(@PathVariable Long codigo) {
			Optional<Usuario> usuario = usuarioRepository.findById(codigo);

			return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
		}

		@DeleteMapping("/{codigo}")
		@ResponseStatus(HttpStatus.NO_CONTENT) // retorna 204 no content não tem conteúdo para retornar
		@PreAuthorize("hasAuthority('ROLE_REMOVER_USUARIO') and #oauth2.hasScope('write')")
		public void remover(@PathVariable Long codigo) {
			usuarioRepository.deleteById(codigo);
		}

		@PutMapping("/{codigo}")
		@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO') and #oauth2.hasScope('write')")
		public ResponseEntity<Usuario> atualizar(@PathVariable Long codigo, @Valid @RequestBody Usuario usuario) {
			Usuario usuarioSalvo = usuarioService.salvar(usuario);
						
			return ResponseEntity.ok(usuarioSalvo);
		}
		
		@GetMapping
		@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO')")
		public Page<Usuario> pesquisar(@RequestParam(required = false, defaultValue = "%") String nome, Pageable pageable) {
			return usuarioRepository.findByNomeContaining(nome, pageable);
		}
				
		@GetMapping("/todos") // mapea as requisições GET para este método
		@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
		public List<Usuario> listar() {
			return usuarioRepository.findAll(); // findAll já está implementado dentro de pessoaRepository e retorna todas as pessoas
		}
		
		@GetMapping("/todas") // mapea as requisições GET para este método
		@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO') and #oauth2.hasScope('read')")
		public List<Permissao> listarPermissoes() {
			return permissaoRepository.findAll(); // findAll já está implementado dentro de pessoaRepository e retorna todas as pessoas
		}
	}
