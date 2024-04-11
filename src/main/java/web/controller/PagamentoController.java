package web.controller;

import business.ValidarDadosCartao;
import business.ValidarUnicidadeCartao;
import database.dominio.Venda.Carrinho;
import database.dominio.Venda.CartaoCredito;
import database.dominio.Venda.EnderecoEntrega;
import database.dominio.Venda.Pagamento;
import support.Json;
import support.URI.ClienteURI;
import support.URI.PagamentoURI;
import web.command.AlterarCommand;
import web.command.ExcluirCommand;
import web.command.ICommand;
import web.command.SalvarCommand;
import web.viewHelper.CartaoCreditoVH;
import web.viewHelper.PagamentoVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {PagamentoURI.ADICIONAR_CARTAO_URI, PagamentoURI.EXCLUIR_CARTAO_URI, PagamentoURI.SELECIONAR_URI})
public class PagamentoController extends AbstractController {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();
        String operacao = request.getParameter("operacao");

        switch (uri) {
            case PagamentoURI.SELECIONAR_URI:

                PagamentoVH pagamentoVh = new PagamentoVH();
                Pagamento pagamento = (Pagamento) pagamentoVh.getEntidade(request);

                if (operacao != null) {
                    if (operacao.equals("SelecionarCartao") ||
                            operacao.equals("SalvarNovo") ||
                            operacao.equals("AlterarCartao") ||
                            operacao.equals("ExcluirCartao")) {
                        ICommand cmd = null;
                        if (operacao.equals("ExcluirCartao")) {
                            cmd = new ExcluirCommand();
                        } else if (operacao.equals("AlterarCartao")) {
                            cmd = new AlterarCommand();
                        } else {
                            cmd = new SalvarCommand();
                        }

                        String retorno = (String) cmd.executar(pagamento);

                        Json jRetorno = new Json();

                        if (retorno == null) {
                            pagamentoVh.setEntidade(response, request, pagamento);
                            jRetorno.setValue("error", false);
                            jRetorno.setValue("redirect", PagamentoURI.SELECIONAR_URI);
                        } else {
                            jRetorno.setValue("error", true);
                            jRetorno.setValue("message", retorno);
                        }

                        response.getWriter().println(jRetorno.toJson());
                        return;
                    } else if(operacao.equals("AdicionarCupom") || operacao.equals("RemoverCupom")) {
                        ICommand cmd = null;

                        if (operacao.equals("AdicionarCupom")) {
                            cmd = new SalvarCommand();
                        } else {
                            cmd = new ExcluirCommand();
                        }

                        String retorno = (String) cmd.executar(pagamento);

                        Json jRetorno = new Json();

                        if (retorno == null) {
                            pagamentoVh.setEntidade(response, request, pagamento);
                            jRetorno.setValue("error", false);
                            jRetorno.setValue("redirect", PagamentoURI.SELECIONAR_URI);
                        } else {
                            jRetorno.setValue("error", true);
                            jRetorno.setValue("message", retorno);
                        }

                        response.getWriter().println(jRetorno.toJson());
                        return;
                    }
                }

                pagamentoVh.setEntidade(response, request, pagamento);

                parametros.put("titulo", "Forma de Pagamento | Flash.com.br");
                view.forwardToJSP(request, response, "cli-pagamento", parametros);
                break;
            case PagamentoURI.ADICIONAR_CARTAO_URI:
            case PagamentoURI.EXCLUIR_CARTAO_URI:


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
                        alert.setValue("title", "Cartao excluido");
                        alert.setValue("message", "Quebramos o cartao :)");
                        alert.setValue("redirect", ClienteURI.PERFIL_CARTOES_URI);
                        json.setValue("alert", alert);
                    }
                }

                response.getWriter().println(json.toJson());
                break;
        }
    }
}
