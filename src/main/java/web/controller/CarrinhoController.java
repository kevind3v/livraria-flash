package web.controller;

import database.dominio.Venda.Carrinho;
import database.dominio.Venda.ItemCarrinho;
import database.dominio.Venda.ItemEstoque;
import support.Json;
import support.URI.CarrinhoURI;
import support.URI.ClienteURI;
import support.URI.EstoqueURI;
import web.command.*;
import web.viewHelper.CarrinhoVH;
import web.viewHelper.ItemCarrinhoVH;
import web.viewHelper.ItemEstoqueVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {CarrinhoURI.VISUALIZAR_ITENS, CarrinhoURI.EXCLUIR_ITEM})
public class CarrinhoController extends AbstractController {

    private Carrinho carrinho = null;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        String operacao = request.getParameter("operacao");

        ItemCarrinhoVH itemVh = new ItemCarrinhoVH();
        ItemCarrinho item = (ItemCarrinho) itemVh.getEntidade(request);

        ICommand cmd = null;

        if(operacao != null) {
            if (uri.equals(CarrinhoURI.EXCLUIR_ITEM)) {
                if (operacao.equals("Remover")) {
                    cmd = new ExcluirCommand();
                }

                String retorno = (String) cmd.executar(item);

                Json json = new Json();
                if(retorno != null){
                    json.setValue("error", true);
                    json.setValue("message", "Nao foi possivel a excluir");
                }else {
                    json.setValue("error", false);
                    Json alert = new Json();
                    alert.setValue("type", "success");
                    alert.setValue("title", "Item excluido");
                    alert.setValue("message", "Devolvemos para estante!");
                    alert.setValue("redirect", CarrinhoURI.VISUALIZAR_ITENS);
                    json.setValue("alert", alert);
                }

                response.getWriter().println(json.toJson());
                return;
            }


            if (operacao.equals("Adicionar")) {
                if (item.getCarrinho().getItens().contains(item)) {
                    cmd = new AlterarCommand();
                } else {
                    cmd = new SalvarCommand();
                }

            } else if (operacao.equals("Editar")) {
                cmd = new AlterarCommand();

            }

            String retorno = (String) cmd.executar(item);

            if (retorno != null) {
                request.setAttribute("mensagemErro", retorno);
            }
        }

        CarrinhoVH carrinhoVh = new CarrinhoVH();
        carrinho = (Carrinho) carrinhoVh.getEntidade(request);

        carrinhoVh.setEntidade(response, request, carrinho);

        parametros.put("titulo", "Carrinho | Flash.com.br");
        view.forwardToJSP(request, response, "cli-carrinho", parametros);
    }
}
