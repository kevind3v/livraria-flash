package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.Livro;
import database.dominio.Venda.CategoriaStatus;
import database.dominio.Venda.ItemEstoque;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class ItemEstoqueDAO extends AbstractDAO {
    public ItemEstoqueDAO(Connection conn) {
        super(conn, "estoque", "etq_id");
    }

    public ItemEstoqueDAO() {
        super("estoque", "etq_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {

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
        ItemEstoque item = (ItemEstoque) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM estoque ");

        if (item.getLivro() != null)
            sql.append("WHERE lvr_id = ?;");
        else
            sql.append("WHERE etq_id = ?;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, item.getLivro() != null ? item.getLivro().getId() : item.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                LivroDAO livroDao = new LivroDAO(conn);
                Livro livro = new Livro();
                livro.setId(rs.getInt("lvr_id"));

                CategoriaStatus catStatus = null;

                if(rs.getInt("cgs_id") == 1) {
                    catStatus = CategoriaStatus.EM_ESTOQUE;
                }else if(rs.getInt("cgs_id") == 2) {
                    catStatus = CategoriaStatus.FORA_DE_MERCADO;
                }else if(rs.getInt("cgs_id") == 3) {
                    catStatus = CategoriaStatus.SEM_ESTOQUE;
                }

                item = new ItemEstoque(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("etq_quantidade"),
                        new BigDecimal(rs.getString("etq_valor_venda")),
                        rs.getBoolean("etq_status"),
                        catStatus
                );

                item.setId(rs.getInt("etq_id"));

            }

            return item;

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

    public ItemEstoque consultarPorLivroId(ItemEstoque item) {
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM estoque ");
        sql.append("WHERE lvr_id = ?;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, item.getLivro().getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                LivroDAO livroDao = new LivroDAO(conn);
                Livro livro = new Livro();
                livro.setId(rs.getInt("lvr_id"));

                CategoriaStatus catStatus = null;

                if(rs.getInt("cgs_id") == 1) {
                    catStatus = CategoriaStatus.EM_ESTOQUE;
                }else if(rs.getInt("cgs_id") == 2) {
                    catStatus = CategoriaStatus.FORA_DE_MERCADO;
                }else if(rs.getInt("cgs_id") == 3) {
                    catStatus = CategoriaStatus.SEM_ESTOQUE;
                }

                item = new ItemEstoque(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("etq_quantidade"),
                        new BigDecimal (rs.getString("etq_valor_venda")),
                        rs.getBoolean("etq_status"),
                        catStatus
                );

                item.setId(rs.getInt("etq_id"));

            }

            return item;

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

    public ItemEstoque consultarPorLivro(Livro livro) {
        ItemEstoque item = null;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM estoque ");
        sql.append("WHERE lvr_id = ?;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, livro.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                LivroDAO livroDao = new LivroDAO(conn);

                CategoriaStatus catStatus = null;

                if(rs.getInt("cgs_id") == 1) {
                    catStatus = CategoriaStatus.EM_ESTOQUE;
                }else if(rs.getInt("cgs_id") == 2) {
                    catStatus = CategoriaStatus.FORA_DE_MERCADO;
                }else if(rs.getInt("cgs_id") == 3) {
                    catStatus = CategoriaStatus.SEM_ESTOQUE;
                }

                item = new ItemEstoque(
                        (Livro) livroDao.consultarPorId(livro),
                        rs.getInt("etq_quantidade"),
                        new BigDecimal(rs.getString("etq_valor_venda")),
                        rs.getBoolean("etq_status"),
                        catStatus
                );

                item.setId(rs.getInt("etq_id"));

            }

            return item;

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
