package database.dominio.Livro;

import java.util.List;

import database.dominio.EntidadeDominio;

public class Livro extends EntidadeDominio {
	private String titulo;
	private String editora;
	private int ano;
	private int edicao;
	private int numPaginas;
	private String ISBN;
	private String codigoBarras;
	private String sinopse;
	private String urlCapa;
	
	private Dimensao dimensao;
	private GrupoPrecificacao gpPrecificacao;
	
	private List<Autor> autores;
    private List<Categoria> categorias;
	
	public Livro(){}	

	public Livro(String titulo, String urlCapa) {
		this.titulo = titulo;
		this.urlCapa = urlCapa;
	}

	public Livro(String titulo, String editora, int ano, int edicao, int numPaginas, Dimensao dimensao, String ISBN,
            String codigoBarras, GrupoPrecificacao gpPrecificacao, String sinopse, String urlCapa) {
        super();
        this.titulo = titulo;
        this.editora = editora;
        this.ano = ano;
        this.edicao = edicao;
        this.numPaginas = numPaginas;
        this.dimensao = dimensao;
        this.ISBN = ISBN;
        this.codigoBarras = codigoBarras;
        this.gpPrecificacao = gpPrecificacao;
        this.sinopse = sinopse;
        this.urlCapa = urlCapa;
        
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	public int getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}

	public Dimensao getDimensao() {
		return dimensao;
	}

	public void setDimensao(Dimensao dimensao) {
		this.dimensao = dimensao;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public GrupoPrecificacao getGpPrecificacao() {
		return gpPrecificacao;
	}

	public void setGpPrecificacao(GrupoPrecificacao gpPrecificacao) {
		this.gpPrecificacao = gpPrecificacao;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getUrlCapa() {
		return urlCapa;
	}

	public void setUrlCapa(String urlCapa) {
		this.urlCapa = urlCapa;
	}

}
