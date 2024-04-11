package database.dominio.Venda;

public enum StatusPedido {
    PROCESSAMENTO("Em Processamento", 1), TRANSITO("Em Trânsito", 2) , ENTREGUE("Entregue", 3),
    TROCA("Em Troca", 4), AUTORIZADA("Troca Autorizada",5), RECUSADA("Troca Recusada", 6), 
    TROCADO("Troca Finalizada", 7), PAGTO("Pagamento Recusado", 8);

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
