<%@ page import="support.URI.PedidoURI" %>
<%@ page import="database.dominio.Venda.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="support.Mascara" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="database.dominio.Venda.Cupom" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<jsp:include page="/components/header-admin.jsp"/>

<% List<Pedido> pedidos = (List<Pedido>)request.getSession().getAttribute("pedidos"); %>

<main class="container" style="margin-top: 80px">
    <h2 class="mt-4" style="font-weight: bold;">Lista de Pedidos</h2>
    <div class="msg_response"></div>
    <table class="table table-bordered mt-4">
        <thead>
        <tr>
            <th scope="col">N° Pedido</th>
            <th scope="col">Status</th>
            <th scope="col">Data Pedido</th>
            <th scope="col">Valor da Compra</th>
            <th scope="col" style="width: 80px;"></th> <!-- Cabeçalho para o botão de remoção -->
        </tr>
        </thead>
        <tbody>
        <% if(pedidos != null){
            BigDecimal desconto = null;
            for(Pedido pedido: pedidos){%>
            <tr>
                <td><%=Mascara.doisDigitoAno(pedido.getDtCadastro().toString())%>-<%= Mascara.formatarIdPedido(pedido.getId()) %></td>
                <td><%= pedido.getStatus().getDescricao() %></td>
                <td>
                    <%= Mascara.dataExtensa(pedido.getDtCadastro().toString()) %>
                </td>
                <%
                    desconto = new BigDecimal("0.00");
                    if(pedido.getCupons() != null) {
                        for(Cupom cupom : pedido.getCupons()) {
                            desconto = desconto.add(cupom.getValor());
                        }
                    }
                %>
                <td>R$ <%=pedido.getValorTotal().add(pedido.getEndereco().getFrete().getValor()).subtract(desconto)%></td>
                <td>
                    <div class="d-flex justify-content-center align-items-center">
                        <a href="<%= PedidoURI.DETALHE_ADMIN_URI %>?p=<%= pedido.getId() %>" title="Detalhe do Pedido">
                            <i class="fas fa-eye text-secondary" style="font-size: 20px;"></i>
                        </a>
                    </div>
                </td>
            </tr>
            <%}
        }%>
        </tbody>
    </table>
</main>
