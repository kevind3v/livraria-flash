package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Venda.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaoCompraDAO extends AbstractDAO {
    public CartaoCompraDAO(Connection conn) {
        super(conn, "cartoescompras", "ccc_id");
    }

    public CartaoCompraDAO() {
        super("cartoescompras", "ccc_id");
    }


    @Override
    public void salvar(EntidadeDominio entidade) {
        CartaoCompra cartao = (CartaoCompra) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO cartoescompras ");
        sql.append("(ccc_nomeTitular, ccc_numero, ccc_cvv, ");
        sql.append("ccc_validade, ban_id, ccc_valor) ");
        sql.append("VALUES (?,?,?,?,?,?);");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cartao.getCartao().getTitular());
            pst.setString(2, cartao.getCartao().getNumero());
            pst.setString(3, cartao.getCartao().getCvv());
            pst.setString(4, cartao.getCartao().getDataValidade());
            pst.setInt(5, cartao.getCartao().getBandeira().getValor());
            pst.setString(6,  cartao.getValor().toString());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idCartao=0;

            if(rs.next())
                idCartao = rs.getInt(1);

            cartao.setId(idCartao);

            if(cartao.isRegistrar()) {
                CartaoCreditoDAO cartaoDao = new CartaoCreditoDAO(conn);
                cartaoDao.salvar(cartao.getCartao());
            }

            conn.commit();

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if(controleTransacao)
                try {
                    conn.close();
                    pst.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        CartaoCompra cartao = (CartaoCompra) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE cartoescompras SET ");
        sql.append("ccc_valor = ? ");
        if(cartao.getPedido() != null)
            sql.append(", pdd_id = ? ");
        sql.append("WHERE ccc_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cartao.getValor().toString());
            if(cartao.getPedido() == null){
                pst.setInt(2, cartao.getId());
            }else {
                pst.setInt(2, cartao.getPedido().getId());
                pst.setInt(3, cartao.getId());
            }

            pst.executeUpdate();

            conn.commit();

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if(controleTransacao)
                try {

                    conn.close();
                    pst.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        CartaoCompra cartao = (CartaoCompra) entidade;
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("SELECT * FROM cartoescompras ");
        sql.append("WHERE ccc_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, cartao.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                Bandeira bandeira = null;
                if(rs.getInt("ban_id") == 1) {
                    bandeira = Bandeira.VISA;
                }else if(rs.getInt("ban_id") == 2) {
                    bandeira = Bandeira.MASTERCARD;
                }

                CartaoCredito c = new CartaoCredito(
                        rs.getString("ccc_validade"),
                        rs.getString("ccc_nomeTitular"),
                        rs.getString("ccc_numero"),
                        rs.getString("ccc_cvv"),
                        bandeira
                );

                cartao = new CartaoCompra(
                        c,
                        new BigDecimal(rs.getString("ccc_valor"))
                );

            }

            return cartao;

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

    public List<CartaoCompra> consultarPorPedido(Pedido pedido){
        List<CartaoCompra> ccs = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("SELECT * FROM cartoescompras ");
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

                Bandeira bandeira = null;
                if(rs.getInt("ban_id") == 1) {
                    bandeira = Bandeira.VISA;
                }else if(rs.getInt("ban_id") == 2) {
                    bandeira = Bandeira.MASTERCARD;
                }

                CartaoCredito c = new CartaoCredito(
                        rs.getString("ccc_validade"),
                        rs.getString("ccc_nomeTitular"),
                        rs.getString("ccc_numero"),
                        rs.getString("ccc_cvv"),
                        bandeira
                );

                CartaoCompra cc = new CartaoCompra(
                        c,
                        new BigDecimal(rs.getString("ccc_valor"))
                );

                ccs.add(cc);
            }

            return ccs;

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
