package web.viewHelper;

import database.dao.CartaoCreditoDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Venda.Bandeira;
import database.dominio.Venda.CartaoCredito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartaoCreditoVH implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        CartaoCredito cartao = null;

        String operacao = request.getParameter("operacao");

        if(operacao.equals("Salvar") || operacao.equals("Adicionar")){

            Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

            String txtNumero = request.getParameter("txtNumero").replaceAll("\\s", "");
            String txtCvv = request.getParameter("txtCVV");
            String txtTitular = request.getParameter("txtTitular");
            String txtValidade = request.getParameter("txtValidade");
            String txtNomeIdentificacao = request.getParameter("txtNomeIdentificacao");

            int nmBandeira= Integer.valueOf(request.getParameter("txtBandeira"));
            Bandeira bandeira = null;
            if(nmBandeira == 1) {
                bandeira = Bandeira.VISA;
            }else if(nmBandeira == 2) {
                bandeira = Bandeira.MASTERCARD;
            }

            cartao = new CartaoCredito(txtNomeIdentificacao, txtValidade, txtTitular,
                    txtNumero, txtCvv, bandeira);

            cartao.setCliente(cliente);

        }else if(operacao.equals("Excluir") || operacao.equals("Selecionar")) {

            String txtCartaoId = request.getParameter("txtIdCartao");

            cartao = new CartaoCredito();
            cartao.setId(Integer.valueOf(txtCartaoId));

//            if(operacao.equals("Selecionar")) {
//                CartaoCreditoDAO cardDao = new CartaoCreditoDAO();
//                cartao = (CartaoCredito) cardDao.consultarPorId(cartao);
//            }

        }

        return cartao;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object msg) {
        // TODO Auto-generated method stub

    }
}
