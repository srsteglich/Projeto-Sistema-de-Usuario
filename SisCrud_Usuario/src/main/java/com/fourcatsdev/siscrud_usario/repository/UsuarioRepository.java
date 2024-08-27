package com.fourcatsdev.siscrud_usario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fourcatsdev.siscrud_usario.modelo.Usuario;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByLogin(String login);

	@Query("SELECT usr FROM Usuario usr WHERE usr.nome LIKE %?1%")
	List<Usuario> findByNomeContaining(String nome);

}
