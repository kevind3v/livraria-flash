<%@ page import="support.Mascara" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="database.dominio.Venda.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="database.dominio.Livro.Autor" %>
<%@ page import="java.util.List" %>
<%@ page import="support.URI.PedidoURI" %>
<%@ page import="java.util.Collections" %>
<%@ page import="support.URI.EstoqueURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js"></script>

<jsp:include page="/components/header.jsp"/>

<%
    List<Pedido> pedidos = (List<Pedido>)request.getSession().getAttribute("pedidos");
%>

<style>
    span.title-card {
        font-size: 14px;
        text-transform: capitalize;
    }
</style>

<div class="container mt-4">
    <h3>Meus pedidos</h3>

    <%
        BigDecimal desconto = null;
        if (pedidos != null && !pedidos.isEmpty()) { %>
    <%for(Pedido pedido : pedidos){%>
    <div class="card mt-3 mb-5">
        <div class="card-header d-flex justify-content-between align-items-center">
            <div class="d-flex">
                <div class="mr-5 d-flex flex-column" style="width: 140px;">
                    <span class="title-card text-muted">PEDIDO REALIZADO</span>
                    <span class="text-muted" style="font-weight: 500"><%= Mascara.dataExtensa(pedido.getDtCadastro().toString()) %></span>
                </div>
                <%
                    desconto = new BigDecimal("0.00");
                    if(pedido.getCupons() != null) {
                        for(Cupom cupom : pedido.getCupons()) {
                            desconto = desconto.add(cupom.getValor());
                        }
                    }
                %>
                <div class="d-flex flex-column" style="width: 140px;">
                    <span class="title-card text-muted">TOTAL</span>
                    <span class="text-muted" style="font-weight: 500">R$ <%= pedido.getValorTotal().add(pedido.getEndereco().getFrete().getValor()).subtract(desconto) %></span>
                </div>
            </div>
            <div class="d-flex flex-column align-items-end">
                <span class="title-card text-muted">PEDIDO Nº <%=Mascara.doisDigitoAno(pedido.getDtCadastro().toString())%>-<%= Mascara.formatarIdPedido(pedido.getId()) %></span>
                <a href="<%= PedidoURI.DETALHE_URI %>?p=<%=pedido.getId()%>" class="text-info" style="text-decoration: none">Exibir detalhes do pedido</a>
            </div>
        </div>
        <div class="card-body">
            <h5 style="font-weight: 700;" class="mb-4">Em Processamento</h5>
            <%
                int qtdItems = 0;
                for(ItemPedido item : pedido.getItens()){
                    qtdItems += item.getQuantidade();
                }
            %>
            <style>
                .item-comprado:not(:last-child) {
                    margin-bottom: 30px;
                }
            </style>
            <%for(ItemPedido item : pedido.getItens()){%>
                <div class="item-comprado d-flex align-items-center">
                    <div class="product-tumb mr-3">
                        <img style="width: 100px; max-width: 100px;" src="../img/livros/<%=item.getLivro().getUrlCapa()%>"/>
                    </div>
                    <div class="card-content">
                        <a href="<%= EstoqueURI.DETALHE_LIVRO_URI %>?l=<%= item.getLivro().getId() %>" class="mb-1" style="text-transform: none; font-size: 16px;"><%=item.getLivro().getTitulo()%></a>
                        <div class="text-muted" style="font-size: 12px">De <% for(Autor autor : item.getLivro().getAutores()){ %> <%= autor.getNome() %>; <% }%></div>
                        <div class="card-price mt-2" style="font-size: 14px; font-weight: 500;"><%= item.getQuantidade() %> x <span>R$ <%=item.getValorVenda()%></span></div>
                    </div>
                </div>
            <%} %>
        </div>
    </div>
    <%}%>
    <%}%>
</div>


<jsp:include page="/components/footer.jsp"/>