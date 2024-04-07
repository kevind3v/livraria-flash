package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.Estoque;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class EstoqueVH implements IViewHelper  {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Estoque estoque = new Estoque();

        if (request.getAttribute("ConsultaLimit") != null) {
            List<String> parametros = new ArrayList<>();
            estoque = new Estoque();
            estoque.setLimit(4);
        }

        return estoque;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("estoque", (Estoque) resultado);
    }
}
