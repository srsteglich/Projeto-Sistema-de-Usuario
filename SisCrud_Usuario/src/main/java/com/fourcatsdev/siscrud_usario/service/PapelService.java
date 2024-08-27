package com.fourcatsdev.siscrud_usario.service;

import java.util.List;

import com.fourcatsdev.siscrud_usario.modelo.Papel;

public interface PapelService {
	public Papel buscarPapelPorId(Long id);
	public Papel buscarPapel(String papel);
	public List<Papel> listarPapel();
}
