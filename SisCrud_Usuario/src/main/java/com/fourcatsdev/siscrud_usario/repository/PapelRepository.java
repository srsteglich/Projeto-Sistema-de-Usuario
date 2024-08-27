package com.fourcatsdev.siscrud_usario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourcatsdev.siscrud_usario.modelo.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
	Papel findByPapel(String papel);
}
