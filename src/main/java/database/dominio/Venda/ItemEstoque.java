package database.dominio.Venda;

import database.dominio.Livro.Livro;

import java.math.BigDecimal;

public class ItemEstoque extends Item {
    private BigDecimal precoCusto;
    private boolean isAtivo;

    private String justificativaStatus;
    private CategoriaStatus catStatus;


    public ItemEstoque() {
        super(null, 0, null);
    }

    public ItemEstoque(Livro livro, int quantidade) {
        super(livro, quantidade, null);

    }

    public ItemEstoque(Livro livro, int quantidade, BigDecimal valorVenda) {
        super(livro, quantidade, valorVenda);
    }

    public ItemEstoque(Livro livro, int quantidade,
                       BigDecimal valorVenda, boolean status, CategoriaStatus catStatus) {
        super(livro, quantidade, valorVenda);

        this.isAtivo = status;
        this.catStatus = catStatus;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }

    public String getJustificativaStatus() {
        return justificativaStatus;
    }

    public void setJustificativaStatus(String justificativaStatus) {
        this.justificativaStatus = justificativaStatus;
    }

    public CategoriaStatus getCatStatus() {
        return catStatus;
    }

    public void setCatStatus(CategoriaStatus catStatus) {
        this.catStatus = catStatus;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

}


