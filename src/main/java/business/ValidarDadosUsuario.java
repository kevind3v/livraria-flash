package business;

import database.dao.UsuarioDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Usuario;

public class ValidarDadosUsuario extends AbstractValidador {

    @Override
    public String processar(EntidadeDominio entidade) {
        Usuario usuario = (Usuario) entidade;

        if(!usuario.getSenha().equals(usuario.getConfirmarSenha())) {
            sb.append("Senhas diferentes; ");
        }

        String senha = usuario.getSenha();

        if(senha.matches("^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$")) {
            sb.append("A senha deve conter no minimo: 1 letra maiuscula, 1 letra minuscula, 1 numero e 1 caracter especial, "
                    + "alem de ter um tamanho minimo correspondente a 8 digitos; ");
        }

        if(sb.length()>0) {
            return sb.toString();
        }

        return null;
    }

}