package web.command;

import database.dominio.EntidadeDominio;

public interface ICommand {
    public Object executar(EntidadeDominio entidade);
}
