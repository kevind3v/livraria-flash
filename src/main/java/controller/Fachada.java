package controller;

import business.IStrategy;
import business.ValidarDadosCliente;
import business.ValidarDadosEndereco;
import business.ValidarDadosUsuario;
import database.dao.ClienteDAO;
import database.dao.EnderecoDAO;
import database.dao.IDAO;
import database.dao.UsuarioDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Endereco;
import database.dominio.Usuario.Usuario;
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

        daos.put(nmCliente, new ClienteDAO());
        daos.put(nmUsuario, new UsuarioDAO());
        daos.put(nmEndereco, new EnderecoDAO());

        ValidarDadosCliente vCliente = new ValidarDadosCliente();
        ValidarDadosUsuario vUsuario = new ValidarDadosUsuario();
        ValidarDadosEndereco vEndereco = new ValidarDadosEndereco();

        List<IStrategy> rnsCliente = new ArrayList<IStrategy>();
        rnsCliente.add(vCliente);
        rns.put(nmCliente, rnsCliente);

        List<IStrategy> rnsUsuario = new ArrayList<IStrategy>();
        rnsUsuario.add(vUsuario);
        rns.put(nmUsuario, rnsUsuario);

        List<IStrategy> rnsEndereco = new ArrayList<IStrategy>();
        rnsEndereco.add(vEndereco);
        rns.put(nmEndereco, rnsEndereco);
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
        return null;
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        return null;
    }

    @Override
    public EntidadeDominio consultarPorId(EntidadeDominio entidade) {

        String nmEntidade = entidade.getClass().getName();

        IDAO dao = daos.get(nmEntidade);

        return dao.consultarPorId(entidade);
    }
}
