package business;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Telefone;

public class ValidarDadosTelefone extends AbstractValidador {

    @Override
    public String processar(EntidadeDominio entidade) {
        Telefone telefone = (Telefone) entidade;

        StringBuilder msg = new StringBuilder();

        if(isNull(telefone.getTpTelefone())) {
            msg.append("Tipo Telefone obrigatorio; ");
        }

        if(isNull(telefone.getDdd())) {
            msg.append("DDD obrigatorio; ");
        }

        if(isNull(telefone.getNumero())) {
            msg.append("Numero Telefone obrigatorio; ");
        }else if(telefone.getNumero().length() != 8 && telefone.getNumero().length() != 9) {
            msg.append("Telefone invalido; ");
        }

        if(msg.length()>0) {
            return msg.toString();
        }

        return null;
    }

}
