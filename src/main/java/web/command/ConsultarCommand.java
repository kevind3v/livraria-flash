package web.command;

import database.dominio.EntidadeDominio;

public class ConsultarCommand extends AbstractCommand {

    @Override
    public Object executar(EntidadeDominio entidade) {
        return fachada.consultar(entidade);
    }

}
