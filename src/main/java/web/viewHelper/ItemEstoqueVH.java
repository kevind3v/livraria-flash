package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Livro.Livro;
import database.dominio.Venda.ItemEstoque;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemEstoqueVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        ItemEstoque item = new ItemEstoque();

        String operacao = request.getParameter("operacao");

        String idItemString = request.getParameter("l");;

        if (idItemString != null && !idItemString.isEmpty()) {
            Livro livro = new Livro();
            livro.setId(Integer.parseInt(idItemString));

            item.setLivro(livro);
        }
//        if(operacao != null) {
//
//        } else {
//
//        }

        return item;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("item", (ItemEstoque) resultado);
    }
}
