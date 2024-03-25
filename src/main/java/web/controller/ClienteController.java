package web.controller;

import database.dominio.Usuario.Cliente;
import support.Json;
import support.URI.ClienteURI;
import support.URI.UsuarioURI;
import web.command.AlterarCommand;
import web.command.ConsultarPorIdCommand;
import web.command.ICommand;
import web.command.SalvarCommand;
import web.viewHelper.ClienteVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {ClienteURI.CADASTRAR_URI, ClienteURI.PERFIL_URI, ClienteURI.PERFIL_ENDERECO_URI, ClienteURI.PERFIL_ALTERAR_URI})
public class ClienteController extends AbstractController {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        ClienteVH clienteVh = new ClienteVH();
        Cliente cliente = null;
        ICommand cmd = null;
        String retorno = null;

        switch (uri) {
            case ClienteURI.CADASTRAR_URI:
                cliente = (Cliente) clienteVh.getEntidade(request);

                cmd = new SalvarCommand();
                retorno = (String) cmd.executar(cliente);

                Json json = new Json();

                if(retorno != null){
                    json.setValue("error", true);
                    json.setValue("message", "Nao foi possivel realizar o Cadastro. Motivos: <b>" + retorno + "</b>");
                }else {
                    json.setValue("error", false);
                    Json alert = new Json();
                    alert.setValue("type", "success");
                    alert.setValue("title", "Ebaa!");
                    alert.setValue("message", "Cliente cadastrado com Sucesso");
                    alert.setValue("redirect", UsuarioURI.LOGIN_URI);
                    json.setValue("alert", alert);
                }

                response.getWriter().println(json.toJson());
                break;
            case ClienteURI.PERFIL_URI:
            case ClienteURI.PERFIL_ENDERECO_URI:
                cliente = (Cliente) request.getSession().getAttribute("cliente");

                if(cliente == null) {
                    cliente = (Cliente)clienteVh.getEntidade(request);
                }

                if (cliente == null) {
                    response.sendRedirect(UsuarioURI.LOGIN_URI);
                    return;
                }

                cmd = new ConsultarPorIdCommand();
                cliente = (Cliente) cmd.executar(cliente);

                clienteVh.setEntidade(response, request, cliente);

                String path = null;

                if (uri.equals(ClienteURI.PERFIL_URI)) {
                    path = "cli_perfil";
                } else {
                    path = "cli_enderecos";
                }

                parametros.put("titulo", cliente.getNome() + " | Flash.com.br");
                view.forwardToJSP(request, response, path, parametros);
                break;
            case ClienteURI.PERFIL_ALTERAR_URI:
                cliente = (Cliente) clienteVh.getEntidade(request);

                cmd = new AlterarCommand();
                retorno = (String) cmd.executar(cliente);

                Json jsonAlterar = new Json();

                if(retorno != null){
                    jsonAlterar.setValue("error", true);
                    jsonAlterar.setValue("message", "Nao foi possivel realizar o alterar os dados. Motivos: <b>" + retorno + "</b>");
                }else {
                    jsonAlterar.setValue("error", false);
                    Json alert = new Json();
                    alert.setValue("type", "success");
                    alert.setValue("title", "Ebaa!");
                    alert.setValue("message", "Dados alterados com Sucesso");
                    alert.setValue("redirect", ClienteURI.PERFIL_URI);
                    jsonAlterar.setValue("alert", alert);
                }

                response.getWriter().println(jsonAlterar.toJson());
                break;
        }
    }
}
