package database.dominio.Venda;

import java.math.BigDecimal;

public class Frete {
    private int prazo = 0;
    private BigDecimal valor = new BigDecimal(0);

    private EnderecoEntrega endereco;

    public Frete(int prazo, BigDecimal valor) {
        super();
        this.prazo = prazo;
        this.valor = valor;
    }

    public Frete(EnderecoEntrega endereco) {
        super();
        this.endereco = endereco;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public BigDecimal getValor() {

        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public EnderecoEntrega getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoEntrega endereco) {
        this.endereco = endereco;
    }

}
