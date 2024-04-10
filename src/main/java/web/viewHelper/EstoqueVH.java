package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.Estoque;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstoqueVH implements IViewHelper  {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Estoque estoque = new Estoque();

        String search = request.getParameter("s");

        if (search != null) {
            String valorBusca = request.getParameter("s");
            List<String> parametros = new ArrayList<>();

            String campos = request.getParameter("campos");

            if (campos != null) {
                String[] camposSeparados = campos.split("-");

                for (String campo : camposSeparados) {
                    if (campo.equals("titulo")) {
                        parametros.add("lvr_titulo");
                    } else if (campo.equals("autor")) {
                        parametros.add("atr_nome");
                    } else if (campo.equals("categoria")) {
                        parametros.add("cat_descricao");
                    } else if (campo.equals("isbn")) {
                        parametros.add("lvr_isbn");
                    } else if (campo.equals("editora")) {
                        parametros.add("lvr_editora");
                    }
                }
            }

            estoque = new Estoque(parametros, valorBusca);
        } else if (request.getAttribute("ConsultaLimit") != null) {
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
