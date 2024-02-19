package business;

import database.dominio.EntidadeDominio;

public interface IStrategy {
    public String processar(EntidadeDominio entidade);
}
