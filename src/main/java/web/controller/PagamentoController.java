package web.controller;

import database.dominio.Venda.CartaoCredito;
import support.Json;
import support.URI.ClienteURI;
import support.URI.PagamentoURI;
import web.command.ExcluirCommand;
import web.command.ICommand;
import web.command.SalvarCommand;
import web.viewHelper.CartaoCreditoVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {PagamentoURI.ADICIONAR_CARTAO_URI, PagamentoURI.EXCLUIR_CARTAO_URI})
public class PagamentoController extends AbstractController {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        switch (uri) {
            case PagamentoURI.ADICIONAR_CARTAO_URI:
            case PagamentoURI.EXCLUIR_CARTAO_URI:
                String operacao = request.getParameter("operacao");

                CartaoCreditoVH cardVh = new CartaoCreditoVH();
                CartaoCredito card = (CartaoCredito) cardVh.getEntidade(request);

                ICommand cmd = null;

                Json json = new Json();

                String retorno = null;

                if(operacao.equals("Salvar")) {
                    cmd = new SalvarCommand();

                    retorno = (String) cmd.executar(card);

                    if(retorno != null){
                        json.setValue("error", true);
                        json.setValue("message", "Erro no Cadastro. Motivos: <b>" + retorno + "</b>");
                    }else {
                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Ebaa!");
                        alert.setValue("message", "Cartao cadastrado!");
                        alert.setValue("redirect", ClienteURI.PERFIL_CARTOES_URI);
                        json.setValue("alert", alert);
                    }
                } else if(operacao.equals("Excluir")){
                    cmd = new ExcluirCommand();

                    retorno = (String) cmd.executar(card);

                    if(retorno != null){
                        json.setValue("error", true);
                        json.setValue("message", "Nao foi possivel a excluir");
                    }else {
                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Ebaa!");
                        alert.setValue("message", "Cartao excluido!");
                        alert.setValue("redirect", ClienteURI.PERFIL_CARTOES_URI);
                        json.setValue("alert", alert);
                    }
                }

                response.getWriter().println(json.toJson());
                break;
        }
    }
}
