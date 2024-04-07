package database.dominio.Venda;

import database.dominio.EntidadeDominio;

import java.util.ArrayList;
import java.util.List;

public class Estoque extends EntidadeDominio {
    private String valorBusca;

    private List<ItemEstoque> itens = new ArrayList<>();
    private List<String> parametros = new ArrayList<>();

    private Integer limit;

    public Estoque() {}

    public Estoque(List<String> parametros, String valorBusca) {
        super();
        this.parametros = parametros;
        this.valorBusca = valorBusca;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    public String getValorBusca() {
        return valorBusca;
    }

    public void setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
    }

    public List<ItemEstoque> getItens() {
        return itens;
    }

    public void setItens(List<ItemEstoque> itens) {
        this.itens = itens;
    }

}