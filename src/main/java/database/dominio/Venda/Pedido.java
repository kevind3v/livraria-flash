package database.dominio.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;

public class Pedido extends EntidadeDominio {
    private BigDecimal valorTotal = new BigDecimal("0");
    
	private Cliente cliente;
	
	private Carrinho carrinho;
	
	private EnderecoEntrega endereco;
	private List<CartaoCompra> cartoes;
	private List<Cupom> cupons;
	private List<ItemPedido> itens = new ArrayList<>();
	
	private StatusPedido status;
	
	private PedidoTroca troca;
	
	public Pedido() {}
	
	public Pedido(StatusPedido status, BigDecimal valorTotal, LocalDate data) {
	    setDtCadastro(data);
	    this.status = status;
	    this.valorTotal = valorTotal;
	}
	
	public Pedido(Cliente cliente, Carrinho carrinho, 
	        EnderecoEntrega endereco, List<CartaoCompra> cartoes, List<Cupom> cupons) {
		super();
		this.cliente = cliente;
		this.carrinho = carrinho;
		this.endereco = endereco;
		this.cartoes = cartoes;
		this.cupons = cupons;
	}
	
	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}


	public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public EnderecoEntrega getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoEntrega endereco) {
		this.endereco = endereco;
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

    public BigDecimal getValorTotal() {
        if(valorTotal.doubleValue() != 0)
            return valorTotal;
        
        for(ItemPedido item: itens)
            valorTotal = valorTotal.add(item.getValorVenda().multiply(new BigDecimal(item.getQuantidade())));
        
        return valorTotal;
    }

    public void setValorTotal( BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public PedidoTroca getTroca() {
        return troca;
    }

    public void setTroca(PedidoTroca troca) {
        this.troca = troca;
    }

}
