package web.controller;

import database.dominio.Venda.Pedido;
import support.Json;
import support.URI.PagamentoURI;
import support.URI.PedidoURI;
import support.URI.UsuarioURI;
import web.command.ConsultarPorIdCommand;
import web.command.ICommand;
import web.command.SalvarCommand;
import web.viewHelper.PedidoVH;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {PedidoURI.FINALIZAR_URI, PedidoURI.DETALHE_URI})
public class PedidoController extends AbstractController {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Map<String, Object> parametros = new HashMap<>();

        PedidoVH pedidoVh = new PedidoVH();
        Pedido pedido = (Pedido) pedidoVh.getEntidade(request);
        ICommand cmd = null;

        switch (uri) {
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
