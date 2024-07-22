package com.generation.crud_farmacia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.crud_farmacia.model.Categoria;
import com.generation.crud_farmacia.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@BeforeAll
	void start() {
		categoriaRepository.deleteAll();

		categoriaRepository.save(new Categoria(0L, "Analgésicos", "Aliviam a dor"));
		categoriaRepository.save(new Categoria(1L, "Antibióticos", "Combatem infecções bacterianas"));
		categoriaRepository.save(new Categoria(2L, "Antiinflamatórios", "Reduzem a inflamação"));
		categoriaRepository.save(new Categoria(3L, "Antidepressivos", "Tratam depressão e ansiedade"));
	}

	@Test
	@DisplayName("Listar todas as Categorias")
	public void deveMostrarTodasAsCategorias() {

		ResponseEntity<String> resposta = testRestTemplate
				.exchange("/categorias", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar Categoria por ID")
	public void deveMostrarCategoriaPorId() {

		ResponseEntity<Categoria> resposta = testRestTemplate
				.exchange("/categorias/2", HttpMethod.GET, null, Categoria.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar Categoria por Descrição")
	public void deveMostrarCategoriaPorDescricao() {

		ResponseEntity<String> resposta = testRestTemplate
				.exchange("/categorias/descricao/rivotril", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Criar uma Categoria")
	public void deveCriarCategoria() {
		
		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(new Categoria(4L, "Antihistamínicos", "Aliviam alergias"));

		ResponseEntity<Categoria> resposta = testRestTemplate
				.exchange("/categorias", HttpMethod.POST, corpoRequisicao, Categoria.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Atualizar uma Categoria")
	public void deveAtualizarCategoria() {

		Categoria categoria = new Categoria(1L, "Antibióticos", "Combatem infecções bacterianas e não virais");

		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(categoria);

		ResponseEntity<Categoria> resposta = testRestTemplate.exchange("/categorias", HttpMethod.PUT, corpoRequisicao,
				Categoria.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Deletar uma Categoria")
	public void deveDeletarCategoria() {

		ResponseEntity<Categoria> resposta = testRestTemplate.exchange("/categorias/3", HttpMethod.DELETE, null,
				Categoria.class);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
	}
	
}
