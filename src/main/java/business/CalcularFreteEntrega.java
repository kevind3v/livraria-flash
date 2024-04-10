package business;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.EnderecoEntrega;
import database.dominio.Venda.Frete;

import java.math.BigDecimal;

public class CalcularFreteEntrega extends AbstractValidador {

    @Override
    public String processar(EntidadeDominio entidade) {
        EnderecoEntrega endereco = (EnderecoEntrega) entidade;

        Frete frete = new Frete(endereco);


        frete.setValor(new BigDecimal("17.60"));
        frete.setPrazo(2);
        endereco.setFrete(frete);

        return null;
    }

}
