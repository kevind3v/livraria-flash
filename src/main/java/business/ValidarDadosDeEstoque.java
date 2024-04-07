package business;

import database.dominio.EntidadeDominio;
import database.dominio.Venda.ItemEstoque;

public class ValidarDadosDeEstoque extends AbstractValidador {
    @Override
    public String processar(EntidadeDominio entidade) {
        ItemEstoque item = (ItemEstoque) entidade;

        if(isNull(item.getPrecoCusto())) {
            sb.append("Para toda entrada em estoque, e obrigatorio informar o preco de custo");
        }

        if(isNull(item.getLivro())){
            sb.append("Para toda entrada em estoque, e obrigatorio informar o livro ");
        }

        if(isNull(item.getQuantidade())) {
            sb.append("Para toda entrada em estoque, e obrigatorio informar a quantidade");
        }

        if(item.getQuantidade() <= 0) {
            sb.append("O valor minimo para adicao de livros em estoque e 1 ");
        }

        DefinirValorItem dvI = new DefinirValorItem();
        dvI.processar(item);

        if(sb.length() > 0)
            return sb.toString();


        return null;
    }
}
