package database.dominio.Venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import database.dominio.EntidadeDominio;

public class Carrinho extends EntidadeDominio {
	private List<ItemCarrinho> itens = new ArrayList<>();
	private BigDecimal valorTotal = new BigDecimal("0");
	
	public Carrinho() {
		
	}
	
	public Carrinho(List<ItemCarrinho> itens) {
		super();
		this.itens = itens;
	}

	public List<ItemCarrinho> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinho> itens) {
		this.itens = itens;
	}

    public BigDecimal getValorTotal() {
        valorTotal = new BigDecimal("0");
        
        for(ItemCarrinho i : itens){
            valorTotal = valorTotal.add(
                    i.getValorVenda().multiply(
                            new BigDecimal (i.getQuantidade())
                            )
                    );
                        
        }
        
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
	
	
}
