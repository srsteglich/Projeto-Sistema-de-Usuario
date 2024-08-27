package com.fourcatsdev.siscrud_usario.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fourcatsdev.siscrud_usario.modelo.Usuario;
import com.fourcatsdev.siscrud_usario.repository.UsuarioRepository;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

	private UsuarioRepository usuarioRepository;
	
	public DetalheUsuarioServico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByLogin(username);
		
		if(usuario != null && usuario.isAtivo()) {
			DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
			return detalheUsuario;
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
