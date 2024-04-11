package database.dominio.Venda;

import java.math.BigDecimal;
import java.util.UUID;

import database.dominio.EntidadeDominio;

public class CartaoCompra extends EntidadeDominio {
    private BigDecimal valor;
    private String hash;
    private boolean isRegistrar = false;
    private boolean temporario = true;
    
    private Pedido pedido;
    private CartaoCredito cartao;
    
    public CartaoCompra() {
        this.hash = UUID.randomUUID().toString();
    }

    public boolean isTemporario() {
        return temporario;
    }

    public void setTemporario(boolean temporario) {
        this.temporario = temporario;
    }

    public CartaoCompra(CartaoCredito cartao, BigDecimal valor) {
        super();
        this.cartao = cartao;
        this.valor = valor;
        this.hash = UUID.randomUUID().toString();
    }

    public String getHash() {return hash;}
    public void setHash(String hash) {this.hash = hash;}
    public CartaoCredito getCartao() {
        return cartao;
    }
    public void setCartao(CartaoCredito cartao) {
        this.cartao = cartao;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public boolean isRegistrar() {
        return isRegistrar;
    }
    public void setRegistrar(boolean isRegistrar) {
        this.isRegistrar = isRegistrar;
    }
    
    
    
}
