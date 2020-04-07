package com.pessoal.compras.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pessoal.compras.model.Usuario;
import com.pessoal.compras.repository.UsuarioRepository;

	@Service // esta classe será um componente do spring e poderá injetá-la
	public class UsuarioService {

		@Autowired
		private UsuarioRepository usuarioRepository;
		
		public Usuario salvar(Usuario usuario) {
			Usuario usuarioComSenhaEncodada;
			Usuario resposta;
			
			if (usuario.getId() == null) { //aqui verifico se o usuário é novo ou atualização
				usuarioComSenhaEncodada = encodar(usuario); // criptografo a senha do usuário novo
				resposta = usuarioRepository.save(usuarioComSenhaEncodada); // salvo o novo usuário
				
			} if(usuario.getId() !=null && comparaSenha(usuario)) { // verifico se a senha é igual a que está no banco
				usuarioComSenhaEncodada = encodar(usuario); // criptografo a senha do usuário novo
				resposta = atualizar(usuarioComSenhaEncodada.getId(), usuarioComSenhaEncodada); // atualizo o usuário
				
			} else {
				usuarioComSenhaEncodada = encodar(usuario);
				resposta = atualizar(usuarioComSenhaEncodada.getId(), usuarioComSenhaEncodada);
			}			
		
			resposta.setSenha(null);
			return resposta;
		}

		public Usuario atualizar(Long codigo, Usuario usuario) {
			Usuario usuarioSalvo = buscarUsuarioPeloCodigo(codigo);
						
			BeanUtils.copyProperties(usuario, usuarioSalvo); // copia de usuarioComSenhaEncodada para usuarioSalvo
			return usuarioRepository.save(usuarioSalvo);
		}

		public Usuario buscarUsuarioPeloCodigo(Long codigo) {
			Optional<Usuario> usuarioSalvo = usuarioRepository.findById(codigo);
			if (usuarioSalvo.isPresent()) {
				throw new EmptyResultDataAccessException(1);
			}
			
			return usuarioSalvo.get();
		}
		
		public Usuario encodar(Usuario usuario) {						
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();						
			usuario.setSenha(encoder.encode(usuario.getSenha()));
			
			return usuario;
		}
		
		public Boolean comparaSenha(Usuario usuario) {						
			BCryptPasswordEncoder compara = new BCryptPasswordEncoder();						
						
			return compara.matches(usuario.getSenha(), 
					buscarUsuarioPeloCodigo(usuario.getId()).getSenha());
		}
	}

