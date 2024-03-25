package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsuarioVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Usuario usuario = null;

        String nmOperacao = request.getParameter("operacao");

        if(nmOperacao != null) {
            if(nmOperacao.equals("Salvar")) {
                usuario = new Usuario();

                String email = request.getParameter("txtEmail");
                String senha = request.getParameter("txtSenha");
                String confirmaSenha = request.getParameter("txtConfirmaSenha");

                usuario.setEmail(email);
                usuario.setSenha(senha);
                usuario.setConfirmarSenha(confirmaSenha);
            }  else if(nmOperacao.equals("Consultar")) {
                usuario = new Usuario();

                String email = request.getParameter("txtEmail");
                String senha = request.getParameter("txtSenha");

                usuario.setEmail(email);
                usuario.setSenha(senha);
            }
        }

        return usuario;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("usuario", (Usuario)resultado);
    }
}
