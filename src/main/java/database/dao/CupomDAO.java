package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Venda.Cupom;
import database.dominio.Venda.Pedido;
import database.dominio.Venda.TipoCupom;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CupomDAO  extends AbstractDAO {
    public CupomDAO(Connection conn) {
        super(conn, "cupons", "ccc_id");
    }

    public CupomDAO() {
        super("cupons", "ccc_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {

        Cupom cupom = (Cupom) entidade;
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("INSERT INTO cupons ");
        sql.append("(cpm_codigo, cpm_valor, cpm_validade, tpc_id, cli_id) ");
        sql.append("VALUES (?,?,?,?,?);");

        try {
            if(conn == null){
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cupom.getCodigo());
            pst.setString(2,  cupom.getValor().toString());
            pst.setDate(3, Date.valueOf(cupom.getValidade()));
            pst.setInt(4, cupom.getTpCupom().getValor());
            pst.setInt(5, cupom.getCliente().getId());

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
        Cupom cupom = (Cupom) entidade;
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("UPDATE cupons SET ");
        sql.append("cpm_isResgatado = true, pdd_id = ? ");
        sql.append("WHERE cpm_id = ?");

        try {
            if(conn == null){
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, cupom.getPedido().getId());
            pst.setInt(2, cupom.getId());

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
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;
        List<EntidadeDominio> cupons = new ArrayList<>();

        sql.append("SELECT * ");
        sql.append("FROM cupons ");
        sql.append("WHERE cli_id = ? and ");
        sql.append("cpm_isResgatado = false ;");

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
                Cupom cupom = new Cupom(
                        rs.getString("cpm_codigo"),
                        new BigDecimal(rs.getString("cpm_valor")),
                        rs.getDate("cpm_validade").toLocalDate()
                );

                TipoCupom tpCupom = null;
                if(rs.getInt("tpc_id") == 1) {
                    tpCupom = TipoCupom.TROCA;
                }else if (rs.getInt("tpc_id") == 2) {
                    tpCupom = TipoCupom.PROMOCIONAL;
                }

                cupom.setId(rs.getInt("cpm_id"));
                cupom.setTpCupom(tpCupom);
                cupom.setCliente(cliente);

                cupons.add(cupom);
            }

            return cupons;

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally{
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

    public boolean validarCupom(Cupom cupom) {

        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;

        sql.append("SELECT * ");
        sql.append("FROM cupons ");
        sql.append("WHERE cpm_codigo = ? and ");
        sql.append("cli_id = ? and ");
        sql.append("cpm_isResgatado = false ;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cupom.getCodigo());
            pst.setInt(2, cupom.getCliente().getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                if(!rs.getDate("cpm_validade").toLocalDate().isAfter(LocalDate.now()))
                    return false;

                BigDecimal valor = new BigDecimal(rs.getString("cpm_valor"));

                TipoCupom tpCupom = null;
                if(rs.getInt("tpc_id") == 1) {
                    tpCupom = TipoCupom.TROCA;
                }else if (rs.getInt("tpc_id") == 2) {

                    tpCupom = TipoCupom.PROMOCIONAL;
                }

                cupom.setValor(valor);
                cupom.setTpCupom(tpCupom);

                cupom.setId(rs.getInt("cpm_id"));

                return true;
            }

            return false;

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally{
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
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        return null;
    }

    public List<Cupom> consultarPorPedido(Pedido pedido) {
        StringBuilder sql = new StringBuilder();
        PreparedStatement pst = null;
        List<Cupom> cupons = new ArrayList<>();

        sql.append("SELECT * ");
        sql.append("FROM cupons ");
        sql.append("WHERE cpm_pdd_id = ? ");

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
                Cupom cupom = new Cupom(
                        rs.getString("cpm_codigo"),
                        new BigDecimal(rs.getString("cpm_valor")),
                        rs.getDate("cpm_validade").toLocalDate()
                );

                TipoCupom tpCupom = null;
                if(rs.getInt("tpc_id") == 1) {
                    tpCupom = TipoCupom.TROCA;
                }else if (rs.getInt("tpc_id") == 2) {
                    tpCupom = TipoCupom.PROMOCIONAL;
                }

                cupom.setId(rs.getInt("cpm_id"));
                cupom.setTpCupom(tpCupom);

                cupons.add(cupom);
            }

            return cupons;

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally{
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
}
