package business;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.CartaoCredito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarDadosCartao extends AbstractValidador {
    @Override
    public String processar(EntidadeDominio entidade) {
        CartaoCredito cartao = null;

//        if(entidade.getClass().getName().equals(CartaoCompra.class.getName())) {
//            CartaoCompra cc = (CartaoCompra) entidade;
//            cartao = cc.getCartao();
//        }else {
//        }
        cartao = (CartaoCredito) entidade;

        if(isNull(cartao.getNumero())) {
            sb.append("Numero do cartao obrigatorio; ");
        } else if (!this.setValidarNumero(cartao.getNumero())) {
            sb.append("Numero do cartao invalido, deve conter 16 numeros; ");
        }

        if(isNull(cartao.getBandeira())) {
            sb.append("Bandeira cartao não encontrada; ");
        } else if(cartao.getBandeira().getValor()!= 1 && cartao.getBandeira().getValor()!= 2) {
            sb.append("Bandeira cartao não encontrada; ");
        }

        if(isNull(cartao.getCvv())) {
            sb.append("CVV obrigatorio;");
        }

        if(cartao.getCvv().length()!=3) {
            sb.append("CVV deve ter 3 digitos; ");
        }

        if (!validarData(cartao.getDataValidade())) {
            sb.append("Data validade invalida; ");
        }

        if(sb.length()>0) {
            return sb.toString();
        }

        return null;
    }

    private boolean setValidarNumero(String numeroCartao)
    {
        String regex = "^(\\d{4}[- ]?){4}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(numeroCartao);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarData(String dataValidade) {
        // Define o formato esperado da data
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        sdf.setLenient(false); // Define para não permitir datas inválidas, como 13/20

        try {
            // Tenta analisar a data fornecida
            Date data = sdf.parse(dataValidade);

            // Verifica se a data fornecida é posterior à data atual
            return data.after(new Date());
        } catch (ParseException e) {
            // Se ocorrer uma exceção ao analisar a data, considera-se inválida
            return false;
        }
    }
}
