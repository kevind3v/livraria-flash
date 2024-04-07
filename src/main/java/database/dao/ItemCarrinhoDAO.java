package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.Livro;
import database.dominio.Venda.Carrinho;
import database.dominio.Venda.ItemCarrinho;
import database.dominio.Venda.ItemEstoque;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCarrinhoDAO extends AbstractDAO {
    public ItemCarrinhoDAO(Connection conn) {
        super(conn, "itensCarrinhos", "itc_id");
    }

    public ItemCarrinhoDAO() {
        super("itensCarrinhos", "itc_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        ItemCarrinho item = (ItemCarrinho) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO itensCarrinhos ");
        sql.append("(crr_id, lvr_id, itc_quantidade, itc_valor_venda) ");
        sql.append("VALUES (?,?,?,?);");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, item.getCarrinho().getId());
            pst.setInt(2, item.getLivro().getId());
            pst.setInt(3, item.getQuantidade());
            pst.setString(4, item.getValorVenda().toString());

            pst.executeUpdate();

            ItemEstoqueDAO estoqueDao = new ItemEstoqueDAO(conn);
            ItemEstoque itemEstoque = estoqueDao.consultarPorLivro(item.getLivro());

            itemEstoque.setQuantidade( itemEstoque.getQuantidade() - item.getQuantidade() );

            estoqueDao.alterar(itemEstoque);

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(controleTransacao){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        ItemCarrinho item = (ItemCarrinho) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE itensCarrinhos ");
        sql.append("SET itc_quantidade = ? ");
        sql.append("WHERE itc_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            int quantidadeBloqueada = 0;

            for(ItemCarrinho i :item.getCarrinho().getItens())
                if(i.getId() == item.getId())
                    quantidadeBloqueada = i.getQuantidade() - item.getQuantidade();

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, item.getQuantidade());
            pst.setInt(2, item.getId());

            pst.executeUpdate();

            ItemEstoqueDAO itemEstoqueDao = new ItemEstoqueDAO(conn);
            ItemEstoque itemEstoque = itemEstoqueDao.consultarPorLivro(item.getLivro());

            itemEstoque.setQuantidade( itemEstoque.getQuantidade() + quantidadeBloqueada );

            itemEstoqueDao.alterar(itemEstoque);

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            if(controleTransacao){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        Carrinho carrinho = (Carrinho) entidade;
        List<EntidadeDominio> itens = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("SELECT * FROM itensCarrinhos ");
        sql.append("WHERE crr_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, carrinho.getId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                LivroDAO livroDao = new LivroDAO(conn);
                Livro livro = new Livro();
                livro.setId(rs.getInt("lvr_id"));

                ItemCarrinho ic = new ItemCarrinho(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("itc_quantidade")
                );
                ic.setValorVenda(new BigDecimal(rs.getString("itc_valor_venda")));

                ic.setId(rs.getInt("itc_id"));

                ic.setCarrinho(carrinho);

                itens.add(ic);
            }

            return itens;

        }catch (Exception e) {

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
