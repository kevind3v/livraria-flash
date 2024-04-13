package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.*;
import database.dominio.Venda.*;
import support.Mascara;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class PedidoVH implements IViewHelper  {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Pedido pedido = new Pedido();

        String operacao = request.getParameter("operacao");

        String txtIdPedido = request.getParameter("p");

        if (txtIdPedido != null) {
            int idPedido = Integer.valueOf(txtIdPedido);
            pedido.setId(idPedido);
        }else if(operacao != null) {
            if(operacao.equals("Salvar")) {
                Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
                EnderecoEntrega endereco = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");
                Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
                Pagamento pagamento = (Pagamento) request.getSession().getAttribute("pagamento");

                System.out.println(cliente);

                List<CartaoCompra> cartoes = pagamento.getCartoes();
                List<Cupom> cupons = pagamento.getCupons();

                pedido = new Pedido(cliente, carrinho, endereco, cartoes, cupons);

                pedido.setStatus(StatusPedido.PROCESSAMENTO);
            }
        }

        return pedido;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("pedido", (Pedido)resultado);
    }
}
