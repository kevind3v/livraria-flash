package database.dominio.Usuario;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.CartaoCredito;

import java.util.List;

public class Cliente extends EntidadeDominio {
    private String nome;
    private String dtNascimento;
    private String cpf;
    private Genero genero;
    private Usuario usuario;

    private Telefone telefone;

    private List<Endereco> enderecos;
    private List<CartaoCredito> cartoes;

    public Cliente() {}

    public Cliente(String nome, String dtNascimento, String cpf, Genero genero) {
        this.nome = nome;
        this.dtNascimento = dtNascimento;
        this.cpf = cpf;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public List<CartaoCredito> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoCredito> cartoes) {
        this.cartoes = cartoes;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
