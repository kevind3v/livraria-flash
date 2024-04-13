package database.dao;

import database.Connect;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Venda.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends AbstractDAO {
    public PedidoDAO(Connection conn) {
        super(conn, "pedidos", "pdd_id");
    }

    public PedidoDAO() {
        super("pedidos", "pdd_id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) {
        Pedido pedido = (Pedido) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO pedidos ");
        sql.append("(cli_id, pdd_valor_total, stp_id, ede_id) ");
        sql.append("VALUES (?,?,?,?) ");

        try {
            if(conn == null){
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            conn.setAutoCommit(false);

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            EnderecoEntregaDAO ede = new EnderecoEntregaDAO(conn);
            ede.salvar(pedido.getEndereco());

            pst.setInt(1, pedido.getCliente().getId());
            pst.setString(2, pedido.getCarrinho().getValorTotal().toString());
            pst.setInt(3, 1);
            pst.setInt(4, pedido.getEndereco().getId());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int idPedido=0;

            if(rs.next()) {
                idPedido = rs.getInt(1);
                pedido.setDtCadastro(rs.getDate("pdd_data").toLocalDate());
            }
            pedido.setId(idPedido);

            CartaoCompraDAO ccDao = new CartaoCompraDAO(conn);
            for(CartaoCompra cc : pedido.getCartoes()) {
                cc.setPedido(pedido);
                ccDao.alterar(cc);
            }

            CupomDAO cupomDao = new CupomDAO(conn);
            for(Cupom cpm: pedido.getCupons()) {
                cpm.setPedido(pedido);
                cupomDao.alterar(cpm);
            }

            ItemPedidoDAO itemPedidoDao = new ItemPedidoDAO(conn);
            for(ItemCarrinho i : pedido.getCarrinho().getItens()) {
                ItemPedido item = new ItemPedido(i.getLivro(), i.getQuantidade(), i.getValorVenda());
                item.setPedido(pedido);
                itemPedidoDao.salvar(item);
                pedido.getItens().add(item);
            }

            ItemCarrinhoDAO itemCarrinhoDao = new ItemCarrinhoDAO(conn);
            itemCarrinhoDao.esvaziarCarrinho(pedido.getCarrinho());

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

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return List.of();
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {
        Pedido pedido = (Pedido) entidade;
        PreparedStatement pst = null;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM pedidos ");
        sql.append("WHERE pdd_id = ? ;");

        try {

            if(conn == null) {
                conn = Connect.getConnectionPostgres();
            }else {
                controleTransacao = false;
            }

            pst = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, pedido.getId());

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                StatusPedido status = null;
                int idStatus = rs.getInt("stp_id");

                if(idStatus == 1) {
                    status = StatusPedido.PROCESSAMENTO;
                }else if (idStatus == 2) {
                    status = StatusPedido.TRANSITO;
                }else if (idStatus == 3) {
                    status = StatusPedido.ENTREGUE;
                }else if (idStatus == 4) {
                    status = StatusPedido.TROCA;
                }else if (idStatus == 5) {
                    status = StatusPedido.AUTORIZADA;
                }else if (idStatus == 6) {
                    status = StatusPedido.RECUSADA;
                }else if (idStatus == 7) {
                    status = StatusPedido.TROCADO;
                }else if (idStatus == 8) {
                    status = StatusPedido.PAGTO;
                }

                pedido = new Pedido(
                        status,
                        new BigDecimal(rs.getString("pdd_valor_total")),
                        rs.getDate("pdd_data").toLocalDate()
                );

                pedido.setId(rs.getInt("pdd_id"));

                ClienteDAO clienteDao = new ClienteDAO(conn);
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cli_id"));
                cliente = (Cliente) clienteDao.consultarPorId(cliente);

                pedido.setCliente(cliente);

                ItemPedidoDAO itemPedidoDao = new ItemPedidoDAO(conn);
                List<ItemPedido> itens = new ArrayList<>();
                for(EntidadeDominio e : itemPedidoDao.consultar(pedido))
                    itens.add((ItemPedido) e);

                pedido.setItens(itens);

                EnderecoEntregaDAO endDao = new EnderecoEntregaDAO(conn);
                EnderecoEntrega endereco = new EnderecoEntrega();
                endereco.setId(rs.getInt("ede_id"));
                endereco = (EnderecoEntrega) endDao.consultarPorId(endereco);
                pedido.setEndereco(endereco);

                CartaoCompraDAO ccDao = new CartaoCompraDAO(conn);
                List<CartaoCompra> cartoes = ccDao.consultarPorPedido(pedido);
                pedido.setCartoes(cartoes);

                CupomDAO crDao = new CupomDAO(conn);
                List<Cupom> cupons = crDao.consultarPorPedido(pedido);
                pedido.setCupons(cupons);

            }

            return pedido;

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
