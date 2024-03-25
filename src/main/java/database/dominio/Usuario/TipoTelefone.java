package database.dominio.Usuario;

import database.dominio.EntidadeDominio;

public enum TipoTelefone {
    FIXO("Fixo", 1), CELULAR("Celular", 2);

    private String descricao;
    private int valor;

    TipoTelefone(String descricao, int valor){
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
