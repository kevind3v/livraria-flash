package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Endereco;
import database.dominio.Venda.EnderecoEntrega;

import java.sql.*;
import java.util.List;

public class EnderecoEntregaDAO extends AbstractDAO {
    public EnderecoEntregaDAO(Connection conn) {
        super(conn, "endereco_entregas", "ede_id");
    }

    public EnderecoEntregaDAO() {
        super("endereco_entregas", "ede_id");
    }


    @Override
    public void salvar(EntidadeDominio entidade) {
        EnderecoEntrega endEntrega = (EnderecoEntrega) entidade;
        Endereco endereco = endEntrega.getEndereco();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO endereco_entregas ");
        sql.append("(ede_logradouro, ede_numero, ede_bairro, ");
        sql.append("ede_cep, ede_cidade, ede_estado, ");
        sql.append("ede_pais, ede_complemento, ");
        sql.append("ede_prazo_entrega, ede_valor_frete) ");
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
            pst.setInt(9, endEntrega.getFrete().getPrazo());
            pst.setString(10, endEntrega.getFrete().getValor().toString());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idEndereco = 0;
            if (rs.next())
                idEndereco = rs.getInt(1);

            endEntrega.setId(idEndereco);

            if(endEntrega.isSalvar()) {
                EnderecoDAO endDao = new EnderecoDAO(conn);
                endDao.salvar(endEntrega.getEndereco());
            }

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
        return null;
    }
}
