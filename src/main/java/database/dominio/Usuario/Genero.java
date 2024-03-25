package database.dominio.Usuario;

import database.dominio.EntidadeDominio;

public enum Genero {
    MASCULINO("Masculino", 1) , FEMININO("Feminino", 2), NAOBINARIO("Não Binário", 3);

    private String descricao;
    private int valor;

    Genero(String descricao, int valor){
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
