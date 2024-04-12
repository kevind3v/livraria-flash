package web.controller;

import database.dominio.Usuario.Endereco;
import database.dominio.Venda.Carrinho;
import database.dominio.Venda.EnderecoEntrega;
import support.Json;
import support.URI.ClienteURI;
import support.URI.EnderecoURI;
import support.URI.UsuarioURI;
import web.command.AlterarCommand;
import web.command.ExcluirCommand;
import web.command.ICommand;
import web.command.SalvarCommand;
import web.viewHelper.EnderecoEntregaVH;
import web.viewHelper.EnderecoVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {EnderecoURI.ADICIONAR_URI, EnderecoURI.EXCLUIR_URI, EnderecoURI.SELECIONAR_URI})
public class EnderecoController extends AbstractController {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();
        String operacao = request.getParameter("operacao");

        switch (uri) {
            case EnderecoURI.ADICIONAR_URI:
            case EnderecoURI.EXCLUIR_URI:
                EnderecoVH endVh = new EnderecoVH();

                Endereco end = (Endereco) endVh.getEntidade(request);

                ICommand cmd = null;

                Json json = new Json();

                String retorno = null;

                EnderecoEntrega endEntrega = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");

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
                        alert.setValue("message", "Endereco cadastrado!");
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
                        if(endEntrega != null) {
                            if(endEntrega.getEndereco().getId() == end.getId()) {
                               endEntrega.setEndereco(end);
                                request.getSession().setAttribute("endSelecionado", endEntrega);
                            }
                        }
                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Ebaa!");
                        alert.setValue("message", "Endereco atualizado!");
                        alert.setValue("redirect", ClienteURI.PERFIL_ENDERECO_URI);
                        json.setValue("alert", alert);
                    }
                } else if(operacao.equals("Excluir")){
                    cmd = new ExcluirCommand();

                    retorno = (String) cmd.executar(end);

                    if(retorno != null){
                        json.setValue("error", true);
                        json.setValue("message", "Nao foi possivel a excluir");
                    }else {

                        if(endEntrega != null) {
                            if(endEntrega.getEndereco().getId() == end.getId()) {
                                request.getSession().setAttribute("endSelecionado", null);
                            }
                        }
                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Endereco excluido!");
                        alert.setValue("message", "Nao vamos mais entregar nele :)");
                        alert.setValue("redirect", ClienteURI.PERFIL_ENDERECO_URI);
                        json.setValue("alert", alert);
                    }
                }

                response.getWriter().println(json.toJson());
                break;
            case EnderecoURI.SELECIONAR_URI:
                if(operacao != null) {
                    EnderecoEntregaVH endEntVh = new EnderecoEntregaVH();

                    EnderecoEntrega endE = (EnderecoEntrega) endEntVh.getEntidade(request);
                    cmd = null;

                    if (operacao.equals("Selecionar")) {
                        endEntVh.setEntidade(response, request, endE);
                        endEntrega = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");

                        Json jRetorno = new Json();
                        if (endEntrega != null) {
                            Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");

                            jRetorno.setValue("error", false);
                            Json frete = new Json();
                            frete.setValue("prazo", endEntrega.getFrete().getPrazo());
                            frete.setValue("valor", String.format("%.2f", endEntrega.getFrete().getValor()));
                            frete.setValue("valorTotal", String.format("%.2f", endEntrega.getFrete().getValor().add(carrinho.getValorTotal())));
                            jRetorno.setValue("frete", frete);
                        } else {
                            jRetorno.setValue("error", true);
                        }

                        response.getWriter().println(jRetorno.toJson());
                        return;
                    } else if(operacao.equals("SalvarNovo")) {
                        endEntVh.setEntidade(response, request, endE);
                        endEntrega = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");

                        Json jRetorno = new Json();
                        if (endEntrega != null) {
                            if (endEntrega.isSalvar()) {
                                cmd = new SalvarCommand();
                                retorno = (String) cmd.executar(endE.getEndereco());
                                if(retorno != null){
                                    jRetorno.setValue("error", true);
                                } else {
                                    jRetorno.setValue("error", false);
                                    jRetorno.setValue("redirect", EnderecoURI.SELECIONAR_URI);
                                }
                            } else {
                                jRetorno.setValue("error", false);
                                jRetorno.setValue("redirect", EnderecoURI.SELECIONAR_URI);
                            }
                        } else {
                            jRetorno.setValue("error", true);
                        }
                        response.getWriter().println(jRetorno.toJson());
                        return;
                    }
//
//
                    return;
                }

                parametros.put("titulo", "Endereço Entrega | Flash.com.br");
                view.forwardToJSP(request, response, "cli-endereco-entrega", parametros);
                break;
        }
    }
}
