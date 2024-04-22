package support;

import database.dominio.EntidadeDominio;
import database.dominio.Livro.Autor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    public static String formatarIdPedido(int id) {
        return String.format("%05d", id);
    }

    public static String dataExtensa(String dataString) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace(); // ou outro tratamento de erro, se necessário
            return null; // retorna null em caso de exceção
        }
    }

    public static String listaParaString(List<Autor> lista) {
        StringBuilder sb = new StringBuilder();

        // Se a lista estiver vazia, retornar uma string vazia
        if (lista.isEmpty()) {
            return "";
        }

        // Se a lista tiver apenas um elemento, retornar esse elemento
        if (lista.size() == 1) {
            return lista.get(0).getNome();
        }

        // Iterar pela lista até o penúltimo elemento
        for (int i = 0; i < lista.size() - 1; i++) {
            sb.append(lista.get(i).getNome());
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        // Adicionar o último elemento
        sb.append(" e ");
        sb.append(lista.get(lista.size() - 1).getNome());

        return sb.toString();
    }

    public static String dataBR(String dataString) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace(); // ou outro tratamento de erro, se necessário
            return null; // retorna null em caso de exceção
        }
    }

    public static String doisDigitoAno(String dataString) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSaida = new SimpleDateFormat("yy", new Locale("pt", "BR"));

        try {
            Date data = formatoEntrada.parse(dataString);
            return formatoSaida.format(data);
        } catch (ParseException e) {
            e.printStackTrace(); // ou outro tratamento de erro, se necessário
            return null; // retorna null em caso de exceção
        }
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
