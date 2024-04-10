package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.Livro;
import database.dominio.Venda.Estoque;
import database.dominio.Venda.ItemEstoque;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO extends AbstractDAO {

    public EstoqueDAO(Connection conn) {
        super(conn, "estoque", "etq_id");
    }

    public EstoqueDAO() {
        super("estoque", "etq_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {

    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        Estoque estoque = (Estoque) entidade;
        List<EntidadeDominio> itens = new ArrayList<>();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT distinct(etq_id), etq_quantidade, etq_valor_venda, l.lvr_id ");
        sql.append("FROM estoque e inner join livros l on (e.lvr_id = l.lvr_id) ");

        if(estoque.getLimit() != null) {
            sql.append(" LIMIT ").append(estoque.getLimit());
        } else if(!estoque.getParametros().isEmpty()) {

            sql.append("inner join autores_livros on (atl_lvr_id = l.lvr_id) ");
            sql.append("inner join autores on (atl_atr_id = atr_id) ");
            sql.append("inner join categorias_livros on (ctl_lvr_id = l.lvr_id) ");
            sql.append("inner join categorias on (ctl_cat_id = cat_id) ");

            StringBuilder clausulaWhere = new StringBuilder("WHERE ");
            for(String parametro : estoque.getParametros()) {
                clausulaWhere.append(parametro).append(" like '%").append(estoque.getValorBusca()).append("%' or ");
            }
            clausulaWhere = new StringBuilder(clausulaWhere.substring(0, clausulaWhere.length() - 4));
            sql.append(clausulaWhere);
        }

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                Livro livro = new Livro();
                LivroDAO livroDao = new LivroDAO(conn);
                livro.setId(rs.getInt("lvr_id"));

                ItemEstoque item = new ItemEstoque(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("etq_quantidade"),
                        new BigDecimal(rs.getString("etq_valor_venda"))
                );

                item.setId(rs.getInt("etq_id"));

                itens.add(item);
            }

            return itens;

        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            if(controleTransacao){
                try {
                    pst.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        return null;
    }
}
