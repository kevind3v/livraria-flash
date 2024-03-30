package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO extends AbstractDAO {
    public EnderecoDAO(Connection conn) {
        super(conn, "endereco", "end_id");
    }

    public EnderecoDAO() {
        super("endereco", "end_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Endereco endereco = (Endereco) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO endereco ");
        sql.append("(end_logradouro, end_numero, end_bairro, ");
        sql.append("end_cep, end_cidade, end_estado, ");
        sql.append("end_pais, end_complemento, end_identificacao, cli_id) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, endereco.getLogradouro());
            pst.setString(2, endereco.getNumero());
            pst.setString(3, endereco.getBairro());
            pst.setString(4, endereco.getCep());
            pst.setString(5, endereco.getCidade());
            pst.setString(6, endereco.getEstado());
            pst.setString(7, "BR");
            pst.setString(8, endereco.getComplemento());
            pst.setString(9, endereco.getIdentificacao());
            pst.setInt(10, endereco.getCliente().getId());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idEndereco = 0;
            if (rs.next())
                idEndereco = rs.getInt(1);

            endereco.setId(idEndereco);

            if (controleTransacao) {
                conn.commit();
            }

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
        Endereco endereco = (Endereco) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE endereco SET ");
        sql.append("end_logradouro = ?, end_numero = ?, end_bairro = ?, ");
        sql.append("end_cep = ?, end_cidade = ?, end_estado = ?, ");
        sql.append("end_pais = ?, end_complemento = ?, end_identificacao = ? ");
        sql.append("WHERE end_id = ?;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, endereco.getLogradouro());
            pst.setString(2, endereco.getNumero());
            pst.setString(3, endereco.getBairro());
            pst.setString(4, endereco.getCep());
            pst.setString(5, endereco.getCidade());
            pst.setString(6, endereco.getEstado());
            pst.setString(7, "BR");
            pst.setString(8, endereco.getComplemento());
            pst.setString(9, endereco.getIdentificacao());
            pst.setInt(10, endereco.getId());

            pst.executeUpdate();


            if (controleTransacao) {
                conn.commit();
            }
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
        List<EntidadeDominio> enderecos = new ArrayList<>();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM endereco ");
        sql.append("WHERE cli_id = ? ORDER BY cli_id ASC;");

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

                Endereco endereco = new Endereco(
                        rs.getString("end_logradouro"),
                        rs.getString("end_numero"),
                        rs.getString("end_bairro"),
                        rs.getString("end_cep"),
                        rs.getString("end_complemento"),
                        rs.getString("end_estado"),
                        rs.getString("end_cidade"),
                        rs.getString("end_identificacao")
                );

                endereco.setId(rs.getInt("end_id"));

                enderecos.add(endereco);
            }

            return enderecos;

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

        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        Endereco endereco = (Endereco) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM endereco ");
        sql.append("WHERE end_id = ?;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, endereco.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                endereco = new Endereco(
                        rs.getString("end_logradouro"),
                        rs.getString("end_numero"),
                        rs.getString("end_bairro"),
                        rs.getString("end_cep"),
                        rs.getString("end_complemento"),
                        rs.getString("end_estado"),
                        rs.getString("end_cidade"),
                        rs.getString("end_identificacao")
                );

                endereco.setId(rs.getInt("end_id"));

            }

            return endereco;

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

        return null;
    }
}
