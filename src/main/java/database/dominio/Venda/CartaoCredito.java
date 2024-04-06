package database.dominio.Venda;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;

public class CartaoCredito extends EntidadeDominio {
    private String nomeIdentificacao;
    private String dataValidade;
    private String titular;
    private String numero;
    private String cvv;

    private Bandeira bandeira;
    private Cliente cliente;

    public CartaoCredito() {}

    public CartaoCredito(String nomeIdentificacao, String dataValidade, String titular, String numero, String cvv,
                         Bandeira bandeira) {
        super();
        this.nomeIdentificacao = nomeIdentificacao;
        this.dataValidade = dataValidade;
        this.titular = titular;
        this.numero = numero;
        this.cvv = cvv;
        this.bandeira = bandeira;
    }

    public CartaoCredito(String dataValidade, String titular, String numero, String cvv,
                         Bandeira bandeira) {
        super();
        this.dataValidade = dataValidade;
        this.titular = titular;
        this.numero = numero;
        this.cvv = cvv;
        this.bandeira = bandeira;
    }

    public String getNomeIdentificacao() {
        return nomeIdentificacao;
    }
    public void setNomeIdentificacao(String nomeIdentificacao) {
        this.nomeIdentificacao = nomeIdentificacao;
    }
    public String getDataValidade() {
        return dataValidade;
    }
    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }
    public String getTitular() {
        return titular;
    }
    public void setTitular(String titular) {
        this.titular = titular;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public Bandeira getBandeira() {
        return bandeira;
    }
    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
