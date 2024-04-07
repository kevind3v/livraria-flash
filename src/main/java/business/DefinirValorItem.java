package business;

import database.dao.ItemEstoqueDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.GrupoPrecificacao;
import database.dominio.Venda.ItemEstoque;

import java.math.BigDecimal;

public class DefinirValorItem extends AbstractValidador {

    @Override
    public String processar(EntidadeDominio entidade) {
        ItemEstoque item = (ItemEstoque) entidade;

        ItemEstoqueDAO itDao = new ItemEstoqueDAO();

        ItemEstoque itemEstoque = (ItemEstoque) itDao.consultarPorId(item);

        if(item.getPrecoCusto().doubleValue() < itemEstoque.getPrecoCusto().doubleValue()) {
            item.setPrecoCusto(itemEstoque.getPrecoCusto());
        }

        item.setValorVenda( item.getPrecoCusto().multiply( new BigDecimal(
                GrupoPrecificacao.getNumber( item.getLivro().getGpPrecificacao()
                ))));

        return null;
    }

}
