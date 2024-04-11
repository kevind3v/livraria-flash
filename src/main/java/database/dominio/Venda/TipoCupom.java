package database.dominio.Venda;

public enum TipoCupom {
    TROCA("Troca", 1), PROMOCIONAL("Promocional", 2);
    
    private String descricao;
    private int valor;
    
    TipoCupom(String descricao, int valor){
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
