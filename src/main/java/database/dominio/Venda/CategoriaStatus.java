package database.dominio.Venda;

public enum CategoriaStatus {
    EM_ESTOQUE("Em estoque", 1), FORA_DE_MERCADO("Fora de Mercado", 2), SEM_ESTOQUE("Sem estoque", 3);

    private String descricao;
    private int valor;
    
    CategoriaStatus(String descricao, int valor){
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
