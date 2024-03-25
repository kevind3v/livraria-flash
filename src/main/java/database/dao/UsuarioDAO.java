package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Usuario;
import support.Passwd;

import java.sql.*;
import java.util.List;

public class UsuarioDAO extends AbstractDAO {
    public UsuarioDAO(Connection conn) {
        super(conn, "usuario", "usr_id");
    }

    public UsuarioDAO() {
        super("usuario", "usr_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Usuario usuario = (Usuario) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO usuario ");
        sql.append("(usr_email, usr_password, usr_is_admin) ");
        sql.append("VALUES (?, ?, ?)");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, usuario.getEmail());
            pst.setString(2, Passwd.gerarHashSenha(usuario.getSenha()));
            pst.setBoolean(3, false);

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idUsuario = 0;

            if (rs.next())
                idUsuario = rs.getInt(1);

            usuario.setId(idUsuario);

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
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        Usuario usuario = (Usuario) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("select usr_email, usr_is_admin ");
        sql.append("from usuario where usr_id = ?;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, usuario.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                usuario.setEmail(rs.getString("usr_email"));
                usuario.setAdmin(rs.getBoolean("usr_is_admin"));

                if(!usuario.isAdmin()) {
                    ClienteDAO cliDao = new ClienteDAO(conn);
                    usuario.setCliente(cliDao.consultarPorUsuario(usuario));
                }
            }

            return usuario;

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
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

        return null;
    }

    public boolean auth(Usuario usuario) {
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT usr_id, usr_password FROM usuario ");
        sql.append("WHERE usr_email = ? ;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, usuario.getEmail());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                String senha = rs.getString("usr_password");

                if (Passwd.verificarSenha(usuario.getSenha(), senha)) {
                    usuario.setSenha(null);
                    usuario.setId(rs.getInt("usr_id"));

                    return true;
                }
            }

            return false;

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
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

        return false;
    }

    public boolean isEmail(Usuario usuario) {
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT usr_id FROM usuario WHERE usr_email = ?;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, usuario.getEmail());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                if(usuario.getId() == rs.getInt("usr_id")) {
                    return false;
                }

                return true;

            }else {
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
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

        return true;
    }
}
