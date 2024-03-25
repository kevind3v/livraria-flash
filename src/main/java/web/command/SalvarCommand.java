package web.command;

import database.dominio.EntidadeDominio;

public class SalvarCommand extends AbstractCommand{

    @Override
    public Object executar(EntidadeDominio entidade) {
        return fachada.salvar(entidade);
    }

}
