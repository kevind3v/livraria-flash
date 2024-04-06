package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Venda.Bandeira;
import database.dominio.Venda.CartaoCredito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaoCreditoDAO extends AbstractDAO {
    public CartaoCreditoDAO(Connection conn) {
        super(conn, "cartoes_credito", "ctc_id");
    }

    public CartaoCreditoDAO() {
        super("cartoes_credito", "ctc_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        CartaoCredito cartao = (CartaoCredito) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO cartoes_credito ");
        sql.append("(ctc_nomeidentificacao, ctc_nometitular, ctc_numero, ");
        sql.append("ctc_cvv, ctc_validade, ban_id, cli_id)");
        sql.append("VALUES (?,?,?,?,?,?,?);");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cartao.getNomeIdentificacao());
            pst.setString(2, cartao.getTitular());
            pst.setString(3, cartao.getNumero());
            pst.setString(4, cartao.getCvv());
            pst.setString(5, cartao.getDataValidade());
            pst.setInt(6, cartao.getBandeira().getValor());
            pst.setInt(7, cartao.getCliente().getId());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idCartao=0;
            if(rs.next())
                idCartao = rs.getInt(1);

            cartao.setId(idCartao);

            conn.commit();

        }catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
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

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;
        List<EntidadeDominio> cartoes = new ArrayList<>();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM cartoes_credito ");
        sql.append("WHERE cli_id = ?;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, cliente.getId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                Bandeira bandeira = null;
                if(rs.getInt("ban_id") == 1) {
                    bandeira = Bandeira.VISA;
                }else if(rs.getInt("ban_id") == 2) {
                    bandeira = Bandeira.MASTERCARD;
                }

                CartaoCredito cartao = new CartaoCredito(
                        rs.getString("ctc_nomeidentificacao"),
                        rs.getString("ctc_validade"),
                        rs.getString("ctc_nometitular"),
                        rs.getString("ctc_numero"),
                        rs.getString("ctc_cvv"),
                        bandeira
                );

                cartao.setId(rs.getInt("ctc_id"));

                cartoes.add(cartao);
            }

            return cartoes;

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
