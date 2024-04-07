package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.ItemEstoque;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemEstoqueVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        ItemEstoque item = new ItemEstoque();

        String operacao = request.getParameter("operacao");

        if(operacao != null) {

        } else {
            int idItem = Integer.valueOf(request.getParameter("l"));
            item.setId(idItem);
        }

        return item;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("item", (ItemEstoque) resultado);
    }
}
