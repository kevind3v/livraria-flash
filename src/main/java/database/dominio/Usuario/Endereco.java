package database.dominio.Usuario;

import database.dominio.EntidadeDominio;

public class Endereco extends EntidadeDominio {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;

    private String estado;

    private String cidade;

    private String identificacao;


    private Cliente cliente;

    public Endereco() {}

    public Endereco(String logradouro, String numero, String bairro, String cep, String complemento, String estado, String cidade, String identificacao) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.estado = estado;
        this.cidade = cidade;
        this.identificacao = identificacao;
    }

    public Endereco(String logradouro, String numero, String bairro, String cep, String complemento, String estado, String cidade) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.estado = estado;
        this.cidade = cidade;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }


}
