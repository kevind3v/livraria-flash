package web.command;

import database.dominio.EntidadeDominio;
import web.command.AbstractCommand;

public class ExcluirCommand extends AbstractCommand {

    @Override
    public Object executar(EntidadeDominio entidade) {
        return fachada.excluir(entidade);
    }

}
