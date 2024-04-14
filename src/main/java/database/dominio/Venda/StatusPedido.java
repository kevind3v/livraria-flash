package database.dominio.Venda;

public enum StatusPedido {
    PROCESSAMENTO("Em Processamento", 1),
    ACEITO("Pagamento Realizado", 2),
    RECUSADO("Pagamento Recusado", 3),
    CANCELADO("Cancelado", 4),
    TRANSITO("Em Trânsito", 5),
    ENTREGUE("Entregue", 6);

    private String descricao;
    private int valor;
    
    private StatusPedido(String descricao, int valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public int getValor() {
        return valor;
    }
    
}
