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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.crud_farmacia.model.Categoria;
import com.generation.crud_farmacia.model.Produto;
import com.generation.crud_farmacia.repository.CategoriaRepository;
import com.generation.crud_farmacia.repository.ProdutoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	Categoria categoriaUm = new Categoria(0L, "Analgésicos", "Aliviam a dor");
	Categoria categoriaDois = new Categoria(1L, "Analgésicos", "Aliviam a dor");
	Categoria categoriaTres = new Categoria(2L, "Analgésicos", "Aliviam a dor");
	Categoria categoriaQuatro = new Categoria(3L, "Analgésicos", "Aliviam a dor");
	
	@BeforeAll
	void start() {
		
		produtoRepository.deleteAll();
		categoriaRepository.deleteAll();
		

		categoriaRepository.save(categoriaUm);
		categoriaRepository.save(categoriaDois);
		categoriaRepository.save(categoriaTres);
		categoriaRepository.save(categoriaQuatro);

		produtoRepository.save(new Produto(0L, "Rivotril", "Rivotril é indicado para transtorno de ansiedade", null));
		produtoRepository.save(new Produto(1L, "Dorflex", "Dorflex é indicado para dor de cabeça", null));
		produtoRepository.save(new Produto(2L, "Dipirona", "Dipirona é indicado para dor de estômago", null));
		produtoRepository.save(new Produto(3L, "Paracetamol", "Paracetamol é indicado para dor de dente", null));

	}

	@Test
	@DisplayName("Listar todos os produtos")
	public void deveMostrarTodosOsprodutos() {

		ResponseEntity<String> resposta = testRestTemplate.exchange("/produtos", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar produto por ID")
	public void deveMostrarProdutoPorId() {

		ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos/2", HttpMethod.GET, null, Produto.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar produto por Nome")
	public void deveMostrarprodutoPorNome() {

		ResponseEntity<String> resposta = testRestTemplate.exchange("/produtos/nome/rivotril", HttpMethod.GET, null,
				String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	/* TODO: Implementar testes de criação e atualização de produtos
	 * @Test
	 * 
	 * @DisplayName("Criar um produto") public void deveCriarproduto() {
	 * 
	 * Produto produto = new Produto(0L, "Rivotril",
	 * "Rivotril é indicado para transtorno de ansiedade e transtorno de humor",
	 * null);
	 * 
	 * produto.setCategoria(categoriaUm);
	 * 
	 * HttpEntity<Produto> corpoRequisicao = new HttpEntity<Produto>(produto);
	 * 
	 * ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos",
	 * HttpMethod.POST, corpoRequisicao, Produto.class);
	 * 
	 * assertEquals(HttpStatus.CREATED, resposta.getStatusCode()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Atualizar um produto") public void deveAtualizarproduto() {
	 * 
	 * Produto produto = new Produto(0L, "Rivotril",
	 * "Rivotril é indicado para transtorno de ansiedade e transtorno de humor",
	 * null);
	 * 
	 * HttpEntity<Produto> corpoRequisicao = new HttpEntity<Produto>(produto);
	 * 
	 * ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos/0",
	 * HttpMethod.PUT, corpoRequisicao, Produto.class);
	 * 
	 * assertEquals(HttpStatus.CREATED, resposta.getStatusCode()); }
	 */

	@Test
	@DisplayName("Deletar um produto")
	public void deveDeletarproduto() {

		ResponseEntity<Produto> resposta = testRestTemplate.exchange("/produtos/3", HttpMethod.DELETE, null,
				Produto.class);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
	}

}
