package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.*;
import database.dominio.Venda.CartaoCredito;
import support.Mascara;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends AbstractDAO {

    public ClienteDAO(Connection conn) {
        super(conn, "cliente", "cli_id");
    }

    public ClienteDAO() {
        super("cliente", "cli_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;

        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO cliente ");
        sql.append("(cli_nome, cli_dt_nasc, cli_cpf, ");
        sql.append("gen_id, tel_id, usr_id) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?)");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            UsuarioDAO usuarioDao = new UsuarioDAO(conn);
            usuarioDao.salvar(cliente.getUsuario());

            TelefoneDAO telefoneDao = new TelefoneDAO(conn);
            telefoneDao.salvar(cliente.getTelefone());

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cliente.getNome());
            pst.setString(2, Mascara.converterData(cliente.getDtNascimento()));
            pst.setString(3, cliente.getCpf());
            pst.setInt(4, cliente.getGenero().getValor());
            pst.setInt(5, cliente.getTelefone().getId());
            pst.setInt(6, cliente.getUsuario().getId());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idCliente=0;

            if(rs.next())
                idCliente = rs.getInt(1);

            cliente.setId(idCliente);

            Endereco end = cliente.getEnderecos().get(0);
            end.setCliente(cliente);

            EnderecoDAO enderecoDao = new EnderecoDAO(conn);
            enderecoDao.salvar(end);

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
        Cliente cliente = (Cliente) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE cliente SET ");
        sql.append("cli_nome = ?, gen_id = ?  ");
        sql.append("WHERE (cli_id = ?) ;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cliente.getNome());
            pst.setInt(2, cliente.getGenero().getValor());
            pst.setInt(3, cliente.getId());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idTelefone = 0;

            if(rs.next())
                idTelefone = rs.getInt("tel_id");

            cliente.getTelefone().setId(idTelefone);

            TelefoneDAO telDao = new TelefoneDAO(conn);
            telDao.alterar(cliente.getTelefone());

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
    public void excluir(EntidadeDominio entidade) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM cliente ");
        sql.append("WHERE cli_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, cliente.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                Genero genero = null;
                if(rs.getInt("gen_id") == 1) {
                    genero = Genero.MASCULINO;
                }else if(rs.getInt("gen_id") == 2) {
                    genero = Genero.MASCULINO;
                }else if(rs.getInt("gen_id") == 3) {
                    genero = Genero.NAOBINARIO;
                }

                cliente = new Cliente(
                        rs.getString("cli_nome"),
                        rs.getString("cli_dt_nasc"),
                        rs.getString("cli_cpf"),
                        genero
                );

                cliente.setId(rs.getInt("cli_id"));

                TelefoneDAO telefoneDao = new TelefoneDAO(conn);
                Telefone tel = new Telefone();
                tel.setId(rs.getInt("tel_id"));
                cliente.setTelefone((Telefone) telefoneDao.consultarPorId(tel));

                EnderecoDAO endDao = new EnderecoDAO(conn);
                List<Endereco> enderecos = new ArrayList<>();
                for(EntidadeDominio ent : endDao.consultar(cliente))
                    enderecos.add((Endereco) ent);
                cliente.setEnderecos(enderecos);

                UsuarioDAO usrDao = new UsuarioDAO(conn);
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usr_id"));
                cliente.setUsuario((Usuario) usrDao.consultarPorId(usuario));

                CartaoCreditoDAO cardDao = new CartaoCreditoDAO(conn);
                List<CartaoCredito> cartoes = new ArrayList<>();
                for(EntidadeDominio ent : cardDao.consultar(cliente))
                    cartoes.add((CartaoCredito) ent);
                cliente.setCartoes(cartoes);
            }

            return cliente;

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

    public boolean isCPF(Cliente cliente) {
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT cli_id FROM cliente WHERE cli_cpf = ?;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cliente.getCpf());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                if(cliente.getId() == rs.getInt("cli_id")) {
                    return false;
                }

                return true;

            } else {
                return false;
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

        return true;
    }

    public Cliente consultarPorUsuario(Usuario user) {
        Cliente cliente = null;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM cliente ");
        sql.append("WHERE usr_id = ? ;");

        try {
            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }
            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, user.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                Genero genero = null;
                if(rs.getInt("gen_id") == 1) {
                    genero = Genero.MASCULINO;
                }else if(rs.getInt("gen_id") == 2) {
                    genero = Genero.MASCULINO;
                }else if(rs.getInt("gen_id") == 3) {
                    genero = Genero.NAOBINARIO;
                }

                cliente = new Cliente(
                        rs.getString("cli_nome"),
                        rs.getString("cli_dt_nasc"),
                        rs.getString("cli_cpf"),
                        genero
                );

                cliente.setId(rs.getInt("cli_id"));

                TelefoneDAO telefoneDao = new TelefoneDAO(conn);
                Telefone tel = new Telefone();
                tel.setId(rs.getInt("tel_id"));
                cliente.setTelefone((Telefone) telefoneDao.consultarPorId(tel));

                EnderecoDAO endDao = new EnderecoDAO(conn);
                List<Endereco> enderecos = new ArrayList<>();
                for(EntidadeDominio entidade : endDao.consultar(cliente))
                    enderecos.add((Endereco) entidade);
                cliente.setEnderecos(enderecos);

                CartaoCreditoDAO cardDao = new CartaoCreditoDAO(conn);
                List<CartaoCredito> cartoes = new ArrayList<>();
                for(EntidadeDominio entidade : cardDao.consultar(cliente))
                    cartoes.add((CartaoCredito) entidade);
                cliente.setCartoes(cartoes);

                cliente.setUsuario(user);

            }

            return cliente;

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
