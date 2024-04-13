package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.Livro;
import database.dominio.Venda.CartaoCompra;
import database.dominio.Venda.Cupom;
import database.dominio.Venda.ItemPedido;
import database.dominio.Venda.Pedido;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO extends AbstractDAO {
    public ItemPedidoDAO(Connection conn) {
        super(conn, "itenspedidos", "itp_id");
    }

    public ItemPedidoDAO() {
        super("itenspedidos", "itp_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        ItemPedido item = (ItemPedido) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO itenspedidos   ");
        sql.append("(lvr_id, itp_quantidade, pdd_id, itp_valor_unitario) ");
        sql.append("VALUES (?,?,?, ?);");

        try {
            if(conn == null){
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, item.getLivro().getId());
            pst.setInt(2, item.getQuantidade());
            pst.setInt(3, item.getPedido().getId());
            pst.setString(4, (item.getValorVenda()
                    ).toString()
            );
            pst.executeUpdate();

            conn.commit();

        }catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {

        Pedido pedido = (Pedido) entidade;
        List<EntidadeDominio> itens = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("SELECT * FROM itenspedidos ");
        sql.append("WHERE pdd_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, pedido.getId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Livro livro = new Livro();
                LivroDAO livroDao = new LivroDAO(conn);

                livro.setId(rs.getInt("lvr_id"));

                ItemPedido ic = new ItemPedido(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("itp_quantidade"),
                        new BigDecimal(rs.getString("itp_valor_unitario"))
                );

                ic.setId(rs.getInt("itp_id"));


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

        ItemPedido item = (ItemPedido) entidade;
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("SELECT * FROM itenspedidos ");
        sql.append("WHERE itp_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, item.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                LivroDAO livroDao = new LivroDAO(conn);
                Livro livro = new Livro();
                livro.setId(rs.getInt("lvr_id"));

                item = new ItemPedido(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("itp_quantidade"),
                        new BigDecimal(rs.getString("itp_valor_unitario"))
                );

                item.setId(rs.getInt("itp_id"));

            }

            return item;

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
}
