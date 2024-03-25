package web.controller;

import database.dao.UsuarioDAO;
import database.dominio.Usuario.Usuario;
import support.Json;
import support.URI.UsuarioURI;
import web.viewHelper.UsuarioVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {UsuarioURI.CLIENTE_HOME_URI, UsuarioURI.LOGIN_URI, UsuarioURI.CADASTRO_URI, UsuarioURI.AUTENTICAR_URI, UsuarioURI.ADMIN_INDEX_URI})
public class UsuarioController extends AbstractController {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        switch (uri) {
            case UsuarioURI.LOGIN_URI:
                request.getSession().setAttribute("usuario", null);
                request.getSession().setAttribute("cliente", null);
                parametros.put("titulo", "Entrar | Flash.com.br");
                view.forwardToJSP(request, response, "login", parametros);
                break;
            case UsuarioURI.CADASTRO_URI:
                request.getSession().setAttribute("usuario", null);
                request.getSession().setAttribute("cliente", null);
                parametros.put("titulo", "Cadastro | Flash.com.br");
                view.forwardToJSP(request, response, "cli_cadastro", parametros);
                break;
            case UsuarioURI.AUTENTICAR_URI:
                UsuarioVH usuarioVh = new UsuarioVH();
                Usuario usuario = (Usuario) usuarioVh.getEntidade(request);
                UsuarioDAO usrDao = new UsuarioDAO();

                if (usrDao.auth(usuario)) {
                    usrDao = new UsuarioDAO();

                    usuario = (Usuario)usrDao.consultarPorId(usuario);

                    usuarioVh.setEntidade(response, request, usuario);

                    Json json = new Json();
                    json.setValue("error", false);

                    if(usuario.isAdmin()) {
                        json.setValue("redirect", UsuarioURI.ADMIN_INDEX_URI);
                    }else {
                        json.setValue("redirect", UsuarioURI.CLIENTE_HOME_URI);
                    }
                    response.getWriter().println(json.toJson());
                } else {
                    Json json = new Json();
                    json.setValue("error", true);
                    json.setValue("message", "E-mail ou Senha invalidos");
                    response.getWriter().println(json.toJson());
                }
                break;
            case UsuarioURI.ADMIN_INDEX_URI:
                parametros.put("titulo", "Dashboard | Flash.com.br");
                view.forwardToJSP(request, response, "adm_home", parametros);
                break;
            case UsuarioURI.CLIENTE_HOME_URI:
                parametros.put("titulo", "Flash.com.br | Sua roupa aqui");
                view.forwardToJSP(request, response, "cli_home", parametros);
                break;
        }
    }
}
