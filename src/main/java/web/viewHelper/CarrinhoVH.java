package web.viewHelper;

import database.dao.ClienteDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Usuario;
import database.dominio.Venda.Carrinho;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarrinhoVH implements IViewHelper  {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Usuario usr = (Usuario) request.getSession().getAttribute("usuario");

        ClienteDAO clienteDao = new ClienteDAO();
        Cliente cliente = clienteDao.consultarPorUsuario(usr);

        request.getSession().setAttribute("cliente", cliente);
        Carrinho carrinho = clienteDao.consultarCarrinho(cliente);

        return carrinho;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("carrinho", (Carrinho) resultado);
    }
}
