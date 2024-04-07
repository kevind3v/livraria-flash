package business;

import database.dao.ItemEstoqueDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.Livro;
import database.dominio.Venda.ItemCarrinho;
import database.dominio.Venda.ItemEstoque;

public class ValidarDisponibilidadeEstoque extends AbstractValidador {

    @Override
    public String processar(EntidadeDominio entidade) {

        ItemCarrinho item = (ItemCarrinho) entidade;
        Livro livro = item.getLivro();

        ItemEstoqueDAO estoqueDao = new ItemEstoqueDAO();
        ItemEstoque itRet = estoqueDao.consultarPorLivro(livro);

        int qnt = item.getQuantidade();

//        if(item.getCarrinho().getItens() != null)
//            for(ItemCarrinho it : item.getCarrinho().getItens()) {
//                if(item.getLivro().getId() == it.getLivro().getId()) {
//                    qnt = item.getQuantidade() - it.getQuantidade();
//                    break;
//                }
//            }
//

        if(qnt > itRet.getQuantidade()) {
            sb.append("Número de items indiponivel. Qtd disponivel: " + itRet.getQuantidade());
        }

        if(!itRet.isAtivo()) {
            sb.append("O produto nao esta disponivel para venda. Motivo: "+itRet.getCatStatus());
        }

        if(sb.length()>0) {
            return sb.toString();
        }

        return null;
    }

}