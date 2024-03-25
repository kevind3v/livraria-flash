package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Telefone;
import database.dominio.Usuario.TipoTelefone;

import java.sql.*;
import java.util.List;

public class TelefoneDAO extends AbstractDAO {
    public TelefoneDAO(Connection conn) {
        super(conn, "telefone", "tel_id");
    }

    public TelefoneDAO() {
        super("telefone", "tel_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Telefone telefone = (Telefone) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO telefone ");
        sql.append("(tel_ddd, tel_numero, tpt_id) ");
        sql.append("VALUES (?, ?, ?)");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }
            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, telefone.getDdd());
            pst.setString(2, telefone.getNumero());
            pst.setInt(3, telefone.getTpTelefone().getValor());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idTelefone = 0;
            if (rs.next())
                idTelefone = rs.getInt(1);

            telefone.setId(idTelefone);

            if (controleTransacao) {
                conn.commit();
            }

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
        Telefone telefone = (Telefone) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE telefone SET ");
        sql.append("tel_ddd = ?, tel_numero = ?, tpt_id = ?  ");
        sql.append("WHERE (tel_id = ?) ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, telefone.getDdd());
            pst.setString(2, telefone.getNumero());
            pst.setInt(3, telefone.getTpTelefone().getValor());

            pst.setInt(4, telefone.getId());

            pst.executeUpdate();

            if (controleTransacao) {
                conn.commit();
            }

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
        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        Telefone telefone = (Telefone) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM telefone ");
        sql.append("WHERE tel_id = ?;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, telefone.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                TipoTelefone tpTelefone = null;
                if(rs.getInt("tpt_id") == 1){
                    tpTelefone = TipoTelefone.FIXO;
                }else if(rs.getInt("tpt_id") == 2){
                    tpTelefone = TipoTelefone.CELULAR;
                }

                telefone = new Telefone(
                        tpTelefone,
                        rs.getString("tel_ddd"),
                        rs.getString("tel_numero")
                );
            }

            return telefone;

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally{
            if(controleTransacao) {
                try {
                    conn.close();
                    pst.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
