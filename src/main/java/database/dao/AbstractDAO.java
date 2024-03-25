package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDAO implements IDAO {

    protected Connection conn;
    protected String entity;
    protected String primary;

    protected boolean controleTransacao = true;

    public AbstractDAO(Connection conn, String entity, String primary) {
        this.conn = conn;
        this.entity = entity;
        this.primary = primary;
    }

    public AbstractDAO(String entity, String primary) {
        this.entity = entity;
        this.primary = primary;
    }

    protected void initConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = Connect.getConnectionPostgres();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {
        initConnection();
        PreparedStatement pst = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("DELETE FROM ");
            sb.append(entity);
            sb.append(" WHERE ");
            sb.append(primary);
            sb.append("=");
            sb.append("?");

            conn.setAutoCommit(false);
            pst = conn.prepareStatement(sb.toString());
            pst.setInt(1, entidade.getId());

            pst.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
