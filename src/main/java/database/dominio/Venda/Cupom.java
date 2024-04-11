package database.dominio.Venda;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cupom extends EntidadeDominio {
	private String codigo;
	private TipoCupom tpCupom;
	private BigDecimal valor;
	private LocalDate validade;
	private Pedido pedido;
	private Cliente cliente;
	
	public Cupom() {}
	
	public Cupom(String codigo, TipoCupom tpCupom, BigDecimal valor, LocalDate validade) {
        super();
        this.codigo = codigo;
        this.tpCupom = tpCupom;
        this.valor = valor;
        this.validade = validade;
    }

    public Cupom(String codigo, Cliente cliente) {
        super();
        this.codigo = codigo;
        this.cliente = cliente;
    }

    public Cupom(String codigo, BigDecimal valor, LocalDate validade) {
        super();
        this.codigo = codigo;
        this.valor = valor;
        this.validade = validade;
    }
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public TipoCupom getTpCupom() {
        return tpCupom;
    }
    public void setTpCupom(TipoCupom tpCupom) {
        this.tpCupom = tpCupom;
    }
    public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public LocalDate getValidade() {
		return validade;
	}
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
		
}
