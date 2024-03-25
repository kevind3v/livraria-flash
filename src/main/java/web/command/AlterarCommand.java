package web.command;

import database.dominio.EntidadeDominio;

public class AlterarCommand extends AbstractCommand {
    @Override
    public Object executar(EntidadeDominio entidade) {
        return fachada.alterar(entidade);
    }
}
