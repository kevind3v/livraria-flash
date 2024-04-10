package web.controller;

import database.dominio.Venda.Estoque;
import database.dominio.Venda.ItemEstoque;
import support.URI.EstoqueURI;
import web.command.ConsultarCommand;
import web.command.ConsultarPorIdCommand;
import web.command.ICommand;
import web.viewHelper.EstoqueVH;
import web.viewHelper.ItemEstoqueVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {EstoqueURI.DETALHE_LIVRO_URI, EstoqueURI.LISTA_URI})
public class EstoqueController extends AbstractController {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();
        String operacao = request.getParameter("operacao");

        switch (uri) {
            case EstoqueURI.LISTA_URI:
                EstoqueVH estoqueVh = new EstoqueVH();
                Estoque estoque = (Estoque) estoqueVh.getEntidade(request);

                ICommand cmd = new ConsultarCommand();

                @SuppressWarnings("unchecked")
                List<ItemEstoque> itens = (List<ItemEstoque>) cmd.executar(estoque);

                estoque.setItens(itens);

                estoqueVh.setEntidade(response, request, estoque);

                parametros.put("titulo", "Estante de Livros | Flash.com.br");
                view.forwardToJSP(request, response, "cli_estante", parametros);
                break;
            case EstoqueURI.DETALHE_LIVRO_URI:
                ItemEstoqueVH itemVh = new ItemEstoqueVH();
                ItemEstoque it = (ItemEstoque) itemVh.getEntidade(request);

                cmd = new ConsultarPorIdCommand();
                it = (ItemEstoque) cmd.executar(it);

                itemVh.setEntidade(response, request, it);

                parametros.put("titulo", it.getLivro().getTitulo() + " | Flash.com.br");
                view.forwardToJSP(request, response, "cli-livro", parametros);
                break;
        }
    }
}
