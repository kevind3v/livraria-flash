package business;

import database.dao.CupomDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Venda.CartaoCompra;
import database.dominio.Venda.Cupom;
import database.dominio.Venda.Pagamento;

import java.math.BigDecimal;

public class ValidarPagamento extends AbstractValidador {
    @Override
    public String processar(EntidadeDominio entidade) {
        Pagamento pagamento = (Pagamento) entidade;

        CartaoCompra cartao = pagamento.getNovoCartao();

        BigDecimal restante = pagamento.getTotalCompra().subtract(pagamento.getTotalAlocado());

        if (restante.toString().equals("0"))
            sb.append("Valor da compra ja esta totalmente alocada. " +
                    "Para adicionar mais cartoes, diminua os valores dos selecionados");

        if (cartao != null) {

            if (cartao.getCartao() != null) {
                ValidarDadosCartao vCartao = new ValidarDadosCartao();
                ValidarUnicidadeCartao vUniCartao = new ValidarUnicidadeCartao();
                String msg = vCartao.processar(cartao.getCartao());

                if( msg != null ){
                    sb.append(msg);
                }

                if (!cartao.isTemporario()) {
                    msg = vUniCartao.processar(cartao.getCartao());
                    if( msg != null ){
                        sb.append(msg);
                    }
                }
            }

            if (cartao.getValor().doubleValue() < 10) {
                if (restante.doubleValue() >= 10) {
                    sb.append("Cada cartao precisa ter pelo menos RS 10,00.");
                } else {
                    BigDecimal totalCupons = new BigDecimal("0");

                    for (Cupom c : pagamento.getCupons()) {
                        totalCupons = totalCupons.add(c.getValor());
                    }


                    if (!totalCupons.equals(pagamento.getTotalAlocado())) {
                        sb.append("Cada cartao precisa ter pelo menos RS 10,00.");
                    } else {
                        if (!cartao.getValor().equals(restante))
                            sb.append("O valor a ser alocado deve ser equivalente ao Restante da compra");
                    }
                }
            } else {

                for (CartaoCompra c : pagamento.getCartoes())
                    if (c.getId() == cartao.getId()) {
                        restante = restante.add(c.getValor());
                        break;
                    }

                if (cartao.getValor().doubleValue() > pagamento.getTotalCompra().doubleValue()) {
                    sb.append("O valor digitado para ser alocado, é superior ao Total da compra");

                } else if (cartao.getValor().doubleValue() > restante.doubleValue()) {
                    sb.append("O valor a ser alocado e superior ao Restante da compra");

                }

            }

        } else {
            Cupom cupom = pagamento.getNovoCupom();

            CupomDAO cupDao = new CupomDAO();

            if (!cupDao.validarCupom(cupom)) {
                sb.append("Cupom invalido, ja foi resgatado, nao pertence a esse cliente ou Cupom vencido; <br>");
                sb.append("Verifique cupons disponiveis em <b>sua conta > cupons</b>;");
            } else {
                for (Cupom c : pagamento.getCupons())
                    if (c.getCodigo().equals(cupom.getCodigo())) {
                        sb.append("Cupom ja foi resgatado nessa compra. ");
                        break;
                    } else if (c.getTpCupom().getValor() == 2 && cupom.getTpCupom().getValor() == 2) {
                        sb.append("So e possivel resgatar um cupom promocional por compra. ");
                        break;
                    }

            BigDecimal restanteC = restante;
            restanteC = restanteC.subtract(cupom.getValor());

            if (restante.doubleValue() <= 0 || restanteC.doubleValue() <= 0) {
                sb.append("Nao foi possivel adicionar o cupom. O valor excede o total da compra. ");
            }

        }
    }


        if(sb.length()>0) {
            return sb.toString();
        }else {
            return null;
        }
    }
}
