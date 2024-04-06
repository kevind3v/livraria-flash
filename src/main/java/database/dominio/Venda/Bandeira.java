package database.dominio.Venda;

public enum Bandeira {
    VISA("Visa", 1), MASTERCARD("Mastercard",2);

    private String descricao;
    private int valor;

    Bandeira(String descricao, int valor){
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

