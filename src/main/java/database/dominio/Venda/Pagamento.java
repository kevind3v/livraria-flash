package database.dominio.Venda;

import database.dominio.EntidadeDominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Pagamento extends EntidadeDominio {
    private List<CartaoCompra> cartoes = new ArrayList<>();
    private List<Cupom> cupons = new ArrayList<>();
    private BigDecimal totalCompra = new BigDecimal("0");
    private BigDecimal totalAlocado = new BigDecimal("0");
    private CartaoCompra novoCartao;
    private Cupom novoCupom;
    private Carrinho carrinho;
    private EnderecoEntrega endereco;
    
    public Pagamento() {}
    
    public Pagamento(List<CartaoCompra> cartoes, List<Cupom> cupons, BigDecimal totalCompra) {
        super();
        this.cartoes = cartoes;
        this.cupons = cupons;
        this.totalCompra = totalCompra;
    }

    public List<CartaoCompra> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoCompra> cartoes) {
        this.cartoes = cartoes;
    }

    public List<Cupom> getCupons() {
        return cupons;
    }

    public void setCupons(List<Cupom> cupons) {
        this.cupons = cupons;
    }

    public BigDecimal getTotalCompra() {
        totalCompra = new BigDecimal("0");
        
        totalCompra = carrinho.getValorTotal().add(endereco.getFrete().getValor());
        
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public BigDecimal getTotalAlocado() {
        
        totalAlocado = new BigDecimal("0");
        
        for(CartaoCompra cartao : cartoes) {
            totalAlocado = totalAlocado.add(cartao.getValor());
        }
        
        for(Cupom cupom : cupons) {
            totalAlocado = totalAlocado.add(cupom.getValor());
        }
        
        return totalAlocado;
    }

    public void setTotalAlocado(BigDecimal totalAlocado) {
        this.totalAlocado = totalAlocado;
    }

    public CartaoCompra getNovoCartao() {
        return novoCartao;
    }

    public void setNovoCartao(CartaoCompra novoCartao) {
        this.novoCartao = novoCartao;
    }

    public Cupom getNovoCupom() {
        return novoCupom;
    }

    public void setNovoCupom(Cupom novoCupom) {
        this.novoCupom = novoCupom;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public EnderecoEntrega getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoEntrega endereco) {
        this.endereco = endereco;
    }
    
    
}
