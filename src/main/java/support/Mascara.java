package support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mascara {
    public static String removerMascara(String valor)
    {
        return valor.replaceAll("\\D", "");
    }

    public static String converterData(String dataEntrada) {
        return converterData(dataEntrada, "dd/MM/yyyy", "yyyy-MM-dd");
    }

    public static String cep(String cep) {
        cep = cep.replaceAll("[^\\d]", "");

        Pattern pattern = Pattern.compile("(\\d{5})(\\d{3})");
        Matcher matcher = pattern.matcher(cep);

        if (matcher.matches()) {
            return matcher.group(1) + "-" + matcher.group(2);
        } else {
            return cep;
        }
    }

    public static String limitarString(String texto, int limite) {
        if (texto == null || texto.length() <= limite) {
            return texto;
        }
        return texto.substring(0, limite) + "...";
    }

    public static String extrairUltimosQuatroDigitos(String numeroCartao) {
        // Verifica se o número do cartão possui pelo menos 4 caracteres
        if (numeroCartao.length() < 4) {
            return "****";
        }

        // Retorna os últimos quatro caracteres do número do cartão
        return numeroCartao.substring(numeroCartao.length() - 4);
    }

    public static String converterData(String data, String formatoEntrada, String formatoSaida)
    {
        if (formatoEntrada == null) {
            formatoEntrada = "dd/MM/yyyy";
        }
        if (formatoSaida == null) {
            formatoSaida = "yyyy-MM-dd";
        }

        SimpleDateFormat sdfEntrada = new SimpleDateFormat(formatoEntrada);
        SimpleDateFormat sdfSaida = new SimpleDateFormat(formatoSaida);

        Date date = null;
        try {
            date = sdfEntrada.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return sdfSaida.format(date);
    }
}
