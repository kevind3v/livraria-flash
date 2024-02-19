package controller;

import business.IStrategy;
import database.dao.IDAO;
import database.dominio.EntidadeDominio;
import support.Resultado;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fachada implements IFachada {
    private Map<String, IDAO> daos = new HashMap<>();
    private Map<String, Map<String, List<IStrategy>>> rns;
    private Resultado resultado;

    @Override
    public String salvar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        return null;
    }
}
