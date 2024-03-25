package business;

import database.dao.ClienteDAO;
import database.dao.UsuarioDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;

public class ValidarUnicidadeCliente extends AbstractValidador {

    @Override
    public String processar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;

        ClienteDAO cliDao = new ClienteDAO();

        if(cliDao.isCPF(cliente)) {
            sb.append("CPF ja cadastrado; ");

        }

        UsuarioDAO usrDao = new UsuarioDAO();

        if(usrDao.isEmail(cliente.getUsuario())) {
            sb.append("Email ja cadastrado; ");

        }

        if(sb.length()>0) {
            return sb.toString();
        }

        return null;
    }

}
