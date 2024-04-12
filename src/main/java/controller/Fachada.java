package controller;

import business.*;
import database.dao.*;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Endereco;
import database.dominio.Usuario.Usuario;
import database.dominio.Venda.*;
import support.Resultado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fachada implements IFachada {
    private Map<String, IDAO> daos = new HashMap<>();
    private Map<String, List<IStrategy>> rns = new HashMap<String, List<IStrategy>>();
    private Resultado resultado;

    public Fachada() {
        String nmCliente = Cliente.class.getName();
        String nmUsuario = Usuario.class.getName();
        String nmEndereco = Endereco.class.getName();
        String nmCartao = CartaoCredito.class.getName();
        String nmItemEstoque = ItemEstoque.class.getName();
        String nmCartaoCompra = CartaoCompra.class.getName();
        String nmItemCarrinho = ItemCarrinho.class.getName();
        String nmCarrinho = Carrinho.class.getName();
        String nmEstoque = Estoque.class.getName();
        String nmPagamento = Pagamento.class.getName();
        String nmEnderecoEntrega = EnderecoEntrega.class.getName();

        daos.put(nmCliente, new ClienteDAO());
        daos.put(nmUsuario, new UsuarioDAO());
        daos.put(nmEndereco, new EnderecoDAO());
        daos.put(nmCartao, new CartaoCreditoDAO());
        daos.put(nmEstoque, new EstoqueDAO());
        daos.put(nmItemEstoque, new ItemEstoqueDAO());
        daos.put(nmCarrinho, new ItemCarrinhoDAO());
        daos.put(nmCartaoCompra, new CartaoCompraDAO());
        daos.put(nmItemCarrinho, new ItemCarrinhoDAO());
        daos.put(nmEnderecoEntrega, new EnderecoEntregaDAO());
        daos.put(nmPagamento, new PagamentoDAO());


        ValidarDadosCliente vCliente = new ValidarDadosCliente();
        ValidarDadosUsuario vUsuario = new ValidarDadosUsuario();
        ValidarDadosEndereco vEndereco = new ValidarDadosEndereco();

        ValidarDadosCartao vCartao = new ValidarDadosCartao();
        ValidarUnicidadeCartao vUniCartao = new ValidarUnicidadeCartao();

        List<IStrategy> rnsCliente = new ArrayList<IStrategy>();
        rnsCliente.add(vCliente);
        rns.put(nmCliente, rnsCliente);

        List<IStrategy> rnsUsuario = new ArrayList<IStrategy>();
        rnsUsuario.add(vUsuario);
        rns.put(nmUsuario, rnsUsuario);

        List<IStrategy> rnsEndereco = new ArrayList<IStrategy>();
        rnsEndereco.add(vEndereco);
        rns.put(nmEndereco, rnsEndereco);

        List<IStrategy> rnsCartaoCredito = new ArrayList<>();
        rnsCartaoCredito.add(vCartao);
        rnsCartaoCredito.add(vUniCartao);
        rns.put(nmCartao, rnsCartaoCredito);

        List<IStrategy> rnsCartaoCompra = new ArrayList<>();
        rnsCartaoCompra.add(vCartao);
        rnsCartaoCompra.add(vUniCartao);
        rns.put(nmCartaoCompra, rnsCartaoCompra);

        ValidarDadosDeEstoque vDadosEstoque = new ValidarDadosDeEstoque();
        List<IStrategy> rnsItemEstoque = new ArrayList<>();
        rnsItemEstoque.add(vDadosEstoque);
        rns.put(nmItemEstoque, rnsItemEstoque);

        ValidarDisponibilidadeEstoque vEstoque = new ValidarDisponibilidadeEstoque();
        List<IStrategy> rnsItemCarrinho = new ArrayList<>();
        rnsItemCarrinho.add(vEstoque);
        rns.put(nmItemCarrinho, rnsItemCarrinho);

        CalcularFreteEntrega cFrete = new CalcularFreteEntrega();
        List<IStrategy> rnsEnderecoEntrega = new ArrayList<>();
        rnsEnderecoEntrega.add(vEndereco);
        rnsEnderecoEntrega.add(cFrete);
        rns.put(nmEnderecoEntrega, rnsEnderecoEntrega);

        ValidarPagamento vPagamento = new ValidarPagamento();
        List<IStrategy> rnsPagamento = new ArrayList<>();
        rnsPagamento.add(vPagamento);
        rns.put(nmPagamento, rnsPagamento);
    }

    @Override
    public String salvar(EntidadeDominio entidade) {
        StringBuilder sb = new StringBuilder();
        String nmEntidade = entidade.getClass().getName();

        List<IStrategy> regras = rns.get(nmEntidade);
        String msg;

        for(IStrategy s: regras){
            msg = s.processar(entidade);
            if(msg != null){
                sb.append(msg);
                sb.append("\n");
            }
        }

        if(sb.length() == 0){
            IDAO dao = daos.get(nmEntidade);
            dao.salvar(entidade);
        }else{
            return sb.toString();
        }

        return null;
    }

    @Override
    public String alterar(EntidadeDominio entidade) {
        StringBuilder sb = new StringBuilder();
        String nmEntidade = entidade.getClass().getName();
        List<IStrategy> regras = rns.get(nmEntidade);
        String msg;

        for(IStrategy s: regras){
            msg = s.processar(entidade);
            if(msg != null){
                sb.append(msg);
                sb.append("\n");
            }
        }

        if(sb.length() == 0){
            IDAO dao = daos.get(nmEntidade);
            dao.alterar(entidade);
        }else{
            return sb.toString();
        }

        return null;
    }

    @Override
    public String excluir(EntidadeDominio entidade) {
        String nmEntidade = entidade.getClass().getName();

        IDAO dao = daos.get(nmEntidade);

        dao.excluir(entidade);

        return null;
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        String nmEntidade = entidade.getClass().getName();

        IDAO dao = daos.get(nmEntidade);

        return dao.consultar(entidade);
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {

        String nmEntidade = entidade.getClass().getName();

        IDAO dao = daos.get(nmEntidade);

        return dao.consultarPorId(entidade);
    }
}
