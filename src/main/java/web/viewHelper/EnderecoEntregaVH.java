package web.viewHelper;

import business.CalcularFreteEntrega;
import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Endereco;
import database.dominio.Venda.EnderecoEntrega;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnderecoEntregaVH implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        EnderecoEntrega end = null;

        String operacao = request.getParameter("operacao");
        end = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");

        if(end == null)
            end = new EnderecoEntrega();

        if(operacao != null)
            if(operacao.equals("Remover")) {
                end = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");
            } else if(operacao.equals("Salvar") || operacao.equals("SalvarNovo") || operacao.equals("Selecionar")) {
                EnderecoVH endVh = new EnderecoVH();
                Endereco endereco = (Endereco) endVh.getEntidade(request);

                end = new EnderecoEntrega(endereco);

                CalcularFreteEntrega frete = new CalcularFreteEntrega();
                frete.processar(end);

                String isSalvar = request.getParameter("swtSalvarEndereco");

                if(isSalvar != null) {
                    end.setSalvar(true);
                }
            }

        return end;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("endSelecionado", (EnderecoEntrega) resultado);
    }

}
