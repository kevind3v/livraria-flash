package web.controller;

import database.dominio.Usuario.Endereco;
import support.Json;
import support.URI.ClienteURI;
import support.URI.EnderecoURI;
import support.URI.UsuarioURI;
import web.command.AlterarCommand;
import web.command.ICommand;
import web.command.SalvarCommand;
import web.viewHelper.EnderecoVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {EnderecoURI.ADICIONAR_URI})
public class EnderecoController extends AbstractController {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        switch (uri) {
            case EnderecoURI.ADICIONAR_URI:
                String operacao = request.getParameter("operacao");
                EnderecoVH endVh = new EnderecoVH();

                Endereco end = (Endereco) endVh.getEntidade(request);

                ICommand cmd = null;

                Json json = new Json();

                String retorno = null;

                if(operacao.equals("Salvar")) {
                    cmd = new SalvarCommand();

                    retorno = (String) cmd.executar(end);

                    if(retorno != null){
                        json.setValue("error", true);
                        json.setValue("message", "Nao foi possivel realizar o Cadastro. Motivos: <b>" + retorno + "</b>");
                    }else {
                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Ebaa!");
                        alert.setValue("message", "Endereço cadastrado!");
                        alert.setValue("redirect", ClienteURI.PERFIL_ENDERECO_URI);
                        json.setValue("alert", alert);
                    }
                } else if(operacao.equals("Alterar")) {
                    cmd = new AlterarCommand();

                    retorno = (String) cmd.executar(end);

                    if(retorno != null){
                        json.setValue("error", true);
                        json.setValue("message", "Nao foi possivel a realizar alteração. Motivos: <b>" + retorno + "</b>");
                    }else {
                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Ebaa!");
                        alert.setValue("message", "Endereço atualizado!");
                        alert.setValue("redirect", ClienteURI.PERFIL_ENDERECO_URI);
                        json.setValue("alert", alert);
                    }
                }

                response.getWriter().println(json.toJson());
                break;
        }
    }
}
