package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Livro.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends AbstractDAO {

    public LivroDAO(Connection conn) {
        super(conn, "livro", "lvr_id");
    }

    public LivroDAO() {
        super("livro", "lvr_id");
    }


    @Override
    public void salvar(EntidadeDominio entidade) {

    }

    @Override
    public void alterar(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        Livro livro = (Livro) entidade;
        List<EntidadeDominio> livros = new ArrayList<>();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT lvr_id, lvr_titulo, lvr_link_capa FROM livros ;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                livro = new Livro(
                        rs.getString("lvr_titulo"),
                        rs.getString("lvr_link_capa")
                );

                livro.setId(rs.getInt("lvr_id"));

                livros.add(livro);
            }

            return livros;

        } catch (Exception e) {
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

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        Livro livro = (Livro) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM livros ");
        sql.append("WHERE lvr_id = ? ;");

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
                livro = new Livro(
                        rs.getString("lvr_titulo"),
                        rs.getString("lvr_editora"),
                        rs.getInt("lvr_ano"),
                        rs.getInt("lvr_edicao"),
                        rs.getInt("lvr_numero_paginas"),
                        new Dimensao(
                                rs.getDouble("lvr_altura"),
                                rs.getDouble("lvr_largura"),
                                rs.getDouble("lvr_profundidade"),
                                rs.getDouble("lvr_peso")
                        ),
                        rs.getString("lvr_isbn"),
                        rs.getString("lvr_codigo_barras"),
                        GrupoPrecificacao.getValue(rs.getInt("lvr_grupo_precificacao")),
                        rs.getString("lvr_sinopse"),
                        rs.getString("lvr_link_capa")
                );

                livro.setId(rs.getInt("lvr_id"));

                List<Autor> autores = consultarAutor(livro);
                livro.setAutores(autores);

                List<Categoria> categorias = consultarCategoria(livro);
                livro.setCategorias(categorias);

            }

            return livro;

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

    private List<Categoria> consultarCategoria(Livro livro) {
        List<Categoria> categorias = new ArrayList<>();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM categorias ");
        sql.append("INNER JOIN categorias_livros on (cat_id = ctl_cat_id) ");
        sql.append("WHERE ctl_lvr_id = ?;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, livro.getId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                Categoria categoria = new Categoria(
                        rs.getString("cat_descricao")
                );

                categoria.setId(rs.getInt("cat_id"));

                categorias.add(categoria);
            }

            return categorias;

        } catch (Exception e) {
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

    private List<Autor> consultarAutor(Livro livro) {
        List<Autor> autores = new ArrayList<>();
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM autores ");
        sql.append("INNER JOIN autores_livros on (atr_id = atl_atr_id) ");
        sql.append("WHERE atl_lvr_id = ?;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, livro.getId());

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                Autor autor = new Autor(
                        rs.getString("atr_nome")
                );

                autor.setId(rs.getInt("atr_id"));
                autores.add(autor);
            }

            return autores;

        } catch (Exception e) {
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
}
