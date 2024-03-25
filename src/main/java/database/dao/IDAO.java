package database.dao;

import database.dominio.EntidadeDominio;

import java.sql.SQLException;
import java.util.List;

public interface IDAO {

    public void salvar(EntidadeDominio entidade);
    public void alterar(EntidadeDominio entidade);
    public void excluir(EntidadeDominio entidade);
    public List<EntidadeDominio> consultar(EntidadeDominio entidade);
    public EntidadeDominio consultarPorId(EntidadeDominio entidade);

}
