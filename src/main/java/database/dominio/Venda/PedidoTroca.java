package database.dominio.Venda;

import java.math.BigDecimal;
import java.util.List;

import database.dominio.EntidadeDominio;

public class PedidoTroca extends EntidadeDominio {
    private Pedido pedido;
    private BigDecimal valorTotal;
    
    private List<ItemTroca> itens;
    private Cupom cupom;
    
    public PedidoTroca(){}
    
    public PedidoTroca(Pedido pedido, List<ItemTroca> itens) {
        super();
        this.pedido = pedido;
        this.itens = itens;
    }
    
    public List<ItemTroca> getItens() {
        return itens;
    }
    public void setItens(List<ItemTroca> itens) {
        this.itens = itens;
    }
   
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getValorTotal() {
        valorTotal = new BigDecimal("0");
        
        for(ItemTroca item : itens) {
            valorTotal = valorTotal.add(item.getValorVenda().multiply(new BigDecimal(item.getQuantidade())));
        }
        
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }
     
}
