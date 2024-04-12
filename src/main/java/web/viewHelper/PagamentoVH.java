package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Venda.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class PagamentoVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Pagamento pagamento = (Pagamento) request.getSession().getAttribute("pagamento");

        CartaoCompra cardCompra = null;

        String operacao = request.getParameter("operacao");

        String valor = request.getParameter("txtValor");

        if(operacao != null) {
            if(operacao.equals("ExcluirCartao")) {
                pagamento = (Pagamento) request.getSession().getAttribute("pagamento");
                String idCartao = request.getParameter("idCartao");

                CartaoCompra cartao = new CartaoCompra();

                cartao.setId(Integer.valueOf(idCartao));

                pagamento.setNovoCartao(cartao);
            } else if(operacao.equals("AlterarCartao") ) {
                pagamento = (Pagamento) request.getSession().getAttribute("pagamento");

                CartaoCompra cartao = new CartaoCompra();

                String idCartao = request.getParameter("idCartao");

                cartao.setId(Integer.valueOf(idCartao));
                cartao.setValor(new BigDecimal(valor));

                pagamento.setNovoCartao(cartao);

            } else if(operacao.equals("SelecionarCartao") || operacao.equals("SalvarNovo")) {
                CartaoCreditoVH cardVh = new CartaoCreditoVH();
                CartaoCredito cartao = (CartaoCredito) cardVh.getEntidade(request);

                cardCompra = new CartaoCompra(cartao, new BigDecimal(valor != null ? valor : "0"));

                String isForm = request.getParameter("txtBandeira");

                if(isForm != null)
                    cardCompra.setTemporario(false);

                String isSalvar = request.getParameter("swtSalvarCartao");

                if(isSalvar != null)
                    cardCompra.setRegistrar(true);

                pagamento.setNovoCartao(cardCompra);
            } else if (operacao.equals("AdicionarCupom")) {
                Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
                String codigo = request.getParameter("txtCodigo");

                Cupom cupom = null;
                if(codigo != null)
                    cupom = new Cupom(codigo, cliente);

                pagamento.setNovoCupom(cupom);
            } else if (operacao.equals("RemoverCupom")) {
                String idCupom = request.getParameter("6");
                Cupom cupom = new Cupom();

                cupom.setId(Integer.valueOf(idCupom));

                pagamento.setNovoCupom(cupom);
            }
        } else {
            CarrinhoVH cartVh = new CarrinhoVH();
            Carrinho carrinho = (Carrinho) cartVh.getEntidade(request);

            EnderecoEntregaVH endVh = new EnderecoEntregaVH();
            EnderecoEntrega endereco = (EnderecoEntrega) endVh.getEntidade(request);

            if(pagamento == null)
                pagamento = new Pagamento();

            pagamento.setCarrinho(carrinho);
            pagamento.setEndereco(endereco);
        }

        return pagamento;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        Pagamento pagamento = (Pagamento) resultado;
        String operacao = request.getParameter("operacao");

        if(operacao != null)
            if(operacao.equals("SelecionarCartao") || operacao.equals("SalvarNovo")) {
                if(pagamento.getNovoCartao() != null)
                    pagamento.getCartoes().add(pagamento.getNovoCartao());
            } else if(operacao.equals("AlterarCartao")) {
                if(pagamento.getNovoCartao() != null)
                    for(CartaoCompra cartao : pagamento.getCartoes()) {
                        if(cartao.getId() == pagamento.getNovoCartao().getId()) {
                            cartao.setValor(pagamento.getNovoCartao().getValor());
                        }
                    }
            }else if(operacao.equals("ExcluirCartao")) {
                if(pagamento.getNovoCartao() != null) {
                    for(CartaoCompra cartao : pagamento.getCartoes()) {
                        if(cartao.getId() == pagamento.getNovoCartao().getId()) {
                            pagamento.getCartoes().remove(cartao);
                            break;
                        }
                    }
                }
            } else if (operacao.equals("AdicionarCupom")) {
                if(pagamento.getNovoCupom() != null)
                    pagamento.getCupons().add(pagamento.getNovoCupom());
            } else if (operacao.equals("RemoverCupom")) {
                if(pagamento.getNovoCupom() != null)
                    for(Cupom cupom : pagamento.getCupons()) {
                        if(cupom.getId() == pagamento.getNovoCupom().getId()) {
                            pagamento.getCupons().remove(cupom);
                            break;
                        }
                    }
            }

        pagamento.setNovoCartao(null);
        pagamento.setNovoCupom(null);

        request.getSession().setAttribute("pagamento", pagamento);
    }

}