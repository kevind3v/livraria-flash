package web.viewHelper;

import database.dao.EnderecoDAO;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Endereco;
import support.Mascara;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnderecoVH implements IViewHelper  {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Endereco endereco = null;

        String nmOperacao = request.getParameter("operacao");
        String idEndereco = request.getParameter("txtEnderecoId");

        if(idEndereco != null && (!nmOperacao.equals("Salvar")) && (!nmOperacao.equals("SalvarNovo"))) {
            EnderecoDAO enderecoDao = new EnderecoDAO();
            endereco = new Endereco();
            endereco.setId(Integer.parseInt(idEndereco));

            endereco = (Endereco) enderecoDao.consultarPorId(endereco);
            if (!nmOperacao.equals("Alterar")) {
                return endereco;
            }
        }

        String nmCep = Mascara.removerMascara(request.getParameter("txtCep"));
        String nmLogradouro = request.getParameter("txtLogradouro");
        String nmBairro = request.getParameter("txtBairro");
        String nmCidade = request.getParameter("txtCidade");
        String nmEstado = request.getParameter("txtEstado");
        String nmNumero = request.getParameter("txtNumero");
        String nmComplemento = request.getParameter("txtComplemento");
        String nmIdentificacao = request.getParameter("txtIdentificacao");

        endereco = new Endereco(nmLogradouro, nmNumero, nmBairro, nmCep, nmComplemento, nmEstado, nmCidade, nmIdentificacao);

        if(!nmOperacao.equals("Salvar") && !nmOperacao.equals("SalvarNovo") && idEndereco != null) {
            endereco.setId(Integer.parseInt(idEndereco));
        }

        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        endereco.setCliente(cliente);

        return endereco;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {

    }
}
