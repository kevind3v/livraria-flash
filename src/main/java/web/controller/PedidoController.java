package web.controller;

import database.dominio.Usuario.Usuario;
import database.dominio.Venda.Pedido;
import support.Json;
import support.Mascara;
import support.URI.CarrinhoURI;
import support.URI.PagamentoURI;
import support.URI.PedidoURI;
import support.URI.UsuarioURI;
import web.command.*;
import web.viewHelper.PedidoVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {PedidoURI.FINALIZAR_URI, PedidoURI.DETALHE_URI, PedidoURI.LISTA_URI, PedidoURI.LISTA_ADMIN_URI, PedidoURI.DETALHE_ADMIN_URI})
public class PedidoController extends AbstractController {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        PedidoVH pedidoVh = new PedidoVH();
        Pedido pedido = (Pedido) pedidoVh.getEntidade(request);
        ICommand cmd = null;

        switch (uri) {
            case PedidoURI.LISTA_ADMIN_URI:
                cmd = new ConsultarCommand();

                @SuppressWarnings("unchecked")
                List<Pedido> p = (List<Pedido>) cmd.executar(pedido);

                request.getSession().setAttribute("pedidos", p);

                parametros.put("titulo", "Lista de Pedidos | Flash.com.br");
                view.forwardToJSP(request, response, "adm_pedidos", parametros);
                break;
            case PedidoURI.LISTA_URI:
                Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

                if (usuario == null) {
                    response.sendRedirect(UsuarioURI.LOGIN_URI);
                    return;
                }

                cmd = new ConsultarCommand();

                @SuppressWarnings("unchecked")
                List<Pedido> pedidos = (List<Pedido>) cmd.executar(pedido);

                request.getSession().setAttribute("pedidos", pedidos);

                parametros.put("titulo", "Meus Pedidos | Flash.com.br");
                view.forwardToJSP(request, response, "cli-pedidos", parametros);
                break;
            case PedidoURI.DETALHE_ADMIN_URI:
                String operacao = request.getParameter("operacao");

                if(operacao != null) {
                    if(operacao.equals("Alterar")) {
                        cmd = new AlterarCommand();
                        cmd.executar(pedido);

                        Json json = new Json();

                        json.setValue("error", false);
                        Json alert = new Json();
                        alert.setValue("type", "success");
                        alert.setValue("title", "Status Atualizado");
                        alert.setValue("message", "O status foi atualizado com sucesso!");
                        alert.setValue("redirect", PedidoURI.DETALHE_ADMIN_URI + "?p=" + pedido.getId());
                        json.setValue("alert", alert);

                        response.getWriter().println(json.toJson());
                        return;
                    }
                }else {
                    cmd = new ConsultarPorIdCommand();
                    pedido = (Pedido) cmd.executar(pedido);
                }

                pedidoVh.setEntidade(response, request, pedido);

                String title = Mascara.doisDigitoAno(pedido.getDtCadastro().toString())+"-"+ Mascara.formatarIdPedido(pedido.getId());

                parametros.put("titulo", "Pedido "+title+" | Flash.com.br");
                view.forwardToJSP(request, response, "adm-detalhe-pedido", parametros);
                break;
            case PedidoURI.DETALHE_URI:
                cmd = new ConsultarPorIdCommand();

                pedido = (Pedido) cmd.executar(pedido);

                pedidoVh.setEntidade(response, request, pedido);

                parametros.put("titulo", "Detalhes do Pedido | Flash.com.br");
                view.forwardToJSP(request, response, "cli-detalhe-pedido", parametros);
                break;
            case PedidoURI.FINALIZAR_URI:
                cmd = new SalvarCommand();
                String retorno = (String) cmd.executar(pedido);

                Json json = new Json();

                if(retorno != null) {
                    json.setValue("error", true);
                    json.setValue("message", retorno);
                } else {
                    pedidoVh.setEntidade(response, request, pedido);
                    request.getSession().setAttribute("pedido", pedido);

                    json.setValue("error", false);
                    Json alert = new Json();
                    alert.setValue("type", "success");
                    alert.setValue("title", "Ebaa!");
                    alert.setValue("message", "Pedido feito!");
                    alert.setValue("redirect", PedidoURI.DETALHE_URI + "?p=" + pedido.getId());
                    json.setValue("alert", alert);
                }

                response.getWriter().println(json.toJson());
                break;
        }
    }
}
