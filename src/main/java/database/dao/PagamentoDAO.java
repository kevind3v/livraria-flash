package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Endereco;
import database.dominio.Venda.EnderecoEntrega;
import database.dominio.Venda.Pagamento;

import java.sql.*;
import java.util.List;

public class PagamentoDAO extends AbstractDAO {
    public PagamentoDAO(Connection conn) {
        super(conn, "cartoescompras", "ccc_id");
    }

    public PagamentoDAO() {
        super("cartoescompras", "ccc_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Pagamento pagamento = (Pagamento) entidade;

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            if(pagamento.getNovoCartao() != null) {
                CartaoCompraDAO ccDao = new CartaoCompraDAO(conn);
                ccDao.salvar(pagamento.getNovoCartao());
            }

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally{
            if(controleTransacao)
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) {
        Pagamento pagamento = (Pagamento) entidade;

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            if(pagamento.getNovoCartao() != null) {
                CartaoCompraDAO ccDao = new CartaoCompraDAO(conn);
                ccDao.alterar(pagamento.getNovoCartao());
            }

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if(controleTransacao)
                try {
                    conn.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void excluir(EntidadeDominio entidade) {
        Pagamento pagamento = (Pagamento) entidade;

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            if(pagamento.getNovoCartao() != null) {
                CartaoCompraDAO ccDao = new CartaoCompraDAO(conn);
                ccDao.excluir(pagamento.getNovoCartao());
            }

        } catch (SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if(controleTransacao)
                try {
                    conn.close();
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
        return null;
    }
}
