package business;

import database.dao.CartaoCreditoDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Endereco;
import database.dominio.Venda.CartaoCompra;
import database.dominio.Venda.CartaoCredito;

import java.util.List;

public class ValidarUnicidadeCartao extends AbstractValidador {
    @Override
    public String processar(EntidadeDominio entidade) {
        CartaoCredito cartao = null;

        if(entidade.getClass().getName().equals(CartaoCompra.class.getName())) {
            CartaoCompra cartaoCompra = (CartaoCompra) entidade;
            cartao = cartaoCompra.getCartao();
        }else {
            cartao = (CartaoCredito) entidade;
        }

        Cliente cliente = cartao.getCliente();

        if(isNull(cartao.getId()) || cartao.getId() == 0){
            CartaoCreditoDAO cartaoDao = new CartaoCreditoDAO();
            List<EntidadeDominio> cartoes = cartaoDao.consultar(cliente);

            for(EntidadeDominio ent : cartoes) {
                CartaoCredito card = (CartaoCredito) ent;
                if(cartao.getNumero().equals(card.getNumero()))
                    sb.append("Cartao com esse mesmo numero ja cadastrado para esse cliente");
            }
        }

        if(sb.length() > 0) {
            return sb.toString();
        }

        return null;
    }
}
