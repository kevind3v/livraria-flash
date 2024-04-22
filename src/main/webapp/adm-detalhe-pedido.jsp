<%@ page import="support.Mascara" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="database.dominio.Venda.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="database.dominio.Livro.Autor" %>
<%@ page import="support.URI.PedidoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js"></script>

<jsp:include page="/components/header-admin.jsp"/>

<%
    Pedido pedido = (Pedido) request.getSession().getAttribute("pedido");
%>



<div class="container mt-4">
    <h3>Detalhe do pedido</h3>
    <div class="d-flex justify-content-between">
        <div class="d-flex align-items-end">
            <span>Pedido em <%= Mascara.dataExtensa(pedido.getDtCadastro().toString()) %></span>
            <span class="text-muted mx-3">|</span>
            <span><span style="font-weight: bold;">Pedido n°</span> <%=Mascara.doisDigitoAno(pedido.getDtCadastro().toString())%>-<%= Mascara.formatarIdPedido(pedido.getId()) %></span>
        </div>
        <div>
            <% if (pedido.getStatus() == StatusPedido.PROCESSAMENTO) { %>
                <button class="btn btn-success btn-acao" data-status="<%= StatusPedido.ACEITO.getValor() %>">Confirmar Pagamento</button>
                <button class="btn btn-danger btn-acao" data-status="<%= StatusPedido.RECUSADO.getValor() %>">Negar Pagamento</button>
                <button class="btn btn-secondary btn-acao" data-status="<%= StatusPedido.CANCELADO.getValor() %>">Cancelar Pedido</button>
            <%} else if (pedido.getStatus() == StatusPedido.ACEITO) {%>
                <button class="btn btn-warning btn-acao" data-status="<%= StatusPedido.TRANSITO.getValor() %>">Despachar Pedido</button>
            <%} else if (pedido.getStatus() == StatusPedido.TRANSITO) {%>
                <button class="btn btn-success btn-acao" data-status="<%= StatusPedido.ENTREGUE.getValor() %>">Confirmar Entrega</button>
            <%}%>
        </div>
    </div>

    <div class="card mt-3 p-3">
        <div class="d-flex justify-content-between">
            <div class="endereco d-flex flex-column" style="width: 280px;">
                <span style="font-weight: bold;">Endereço de Entrega</span>
                <p class="mb-0"><%= pedido.getEndereco().getEndereco().getLogradouro() %>, <%= pedido.getEndereco().getEndereco().getNumero() %></p>
                <p class="mb-0"><%= pedido.getEndereco().getEndereco().getBairro() %></p>
                <p class="mb-0"><%= pedido.getEndereco().getEndereco().getCidade() %> - <%= pedido.getEndereco().getEndereco().getEstado() %></p>
                <p class="mb-0">CEP: <%= Mascara.cep(pedido.getEndereco().getEndereco().getCep()) %></p>
            </div>
            <div class="pagamento" style="width: 280px;">
                <span style="font-weight: bold;">Métodos de pagamento</span>
                <%if(pedido.getCartoes() != null)
                    if(!pedido.getCartoes().isEmpty()){%>
                        <%for(CartaoCompra cc : pedido.getCartoes()){%>
                            <p class="mb-0"><img src="../img/<%=cc.getCartao().getBandeira() == Bandeira.MASTERCARD ? "mastercard" : "visa"%>-b.png" width="40" alt=""> terminando em <b><%= Mascara.extrairUltimosQuatroDigitos(cc.getCartao().getNumero()) %></b></p>
                            <p class="mb-0 text-muted ml-2" style="font-size: 13px">Pagou <b>R$ <%= cc.getValor() %></b></p>
                        <%}%>
                    <%}%>

                <br/>

                <%
                    BigDecimal desconto = new BigDecimal("0.00");
                    if(pedido.getCupons() != null) {
                    for(Cupom cupom : pedido.getCupons()){
                        desconto = desconto.add(cupom.getValor());
                %>
                        <p class="mb-0">Cupom <span style="text-transform: lowercase"><%= cupom.getTpCupom().getDescricao() %></span> <b><%=cupom.getCodigo()%></b></p>
                        <p class="mb-0 text-muted ml-2" style="font-size: 13px">Descontou <b>R$ <%=cupom.getValor()%></b></p>
                    <%}} %>

            </div>
            <div class="resumo" style="width: 280px;">
                <span style="font-weight: bold;">Resumo do pedido</span>
                <div class="d-flex justify-content-between">
                    <p class="mb-0">Subtotal do(s) item(ns):</p>
                    <p class="mb-0">R$ <%=pedido.getValorTotal()%></p>
                </div>
                <div class="d-flex justify-content-between">
                    <p class="mb-0">Frete e manuseio:</p>
                    <p class="mb-0">R$ <%=pedido.getEndereco().getFrete().getValor()%></p>
                </div>

                <div class="d-flex justify-content-between">
                    <p class="mb-0">Desconto:</p>
                    <p class="mb-0">- R$ <%= desconto %></p>
                </div>
                <div class="d-flex justify-content-between">
                    <p class="mb-0" style="font-weight: bold;">Total:</p>
                    <p class="mb-0" style="font-weight: bold;">R$ <%= pedido.getValorTotal().add(pedido.getEndereco().getFrete().getValor()).subtract(desconto) %></p>
                </div>
            </div>
        </div>
    </div>
    <div class="card mt-3 mb-5">
        <div class="card-header m-0" style="background: transparent;">
            <h4 style="font-weight: 700;" class="mb-0"><%=pedido.getStatus().getDescricao()%></h4>
        </div>
        <div class="card-body">
            <%
                int qtdItems = 0;
                for(ItemPedido item : pedido.getItens()){
                    qtdItems += item.getQuantidade();
                }
            %>
            <span style="font-size: 18px;">Pacote com <%= qtdItems %> Livros(s)</span>
            <style>
                .item-comprado:not(:last-child) {
                    margin-bottom: 20px;
                }
            </style>
            <div class="my-3"></div>
            <%for(ItemPedido item : pedido.getItens()){%>
            <div class="item-comprado d-flex align-items-center">
                <div class="product-tumb mr-4">
                    <img style="width: 70px;" src="../img/livros/<%=item.getLivro().getUrlCapa()%>">
                </div>
                <div class="card-content ">
                    <h4 class="mb-1" style="text-transform: none; font-size: 18px;"><%=item.getLivro().getTitulo()%></h4>
                    <div class="text-muted" style="font-size: 14px">De <%= Mascara.listaParaString(item.getLivro().getAutores()) %></div>
                    <div class="card-price mt-2" style="font-size: 16px; font-weight: 500;"><%= item.getQuantidade() %> x <span>R$ <%=item.getValorVenda()%></span></div>
                </div>
            </div>
            <%} %>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('.btn-acao').on('click', function() {
            const status = ($(this).data()).status;

            let statusString = "";

            if (status == <%= StatusPedido.ACEITO.getValor() %>)
               statusString = "<%= StatusPedido.ACEITO.getDescricao() %>";
            else if(status == <%= StatusPedido.RECUSADO.getValor() %>)
               statusString = "<%= StatusPedido.RECUSADO.getDescricao() %>"
            else if(status == <%= StatusPedido.CANCELADO.getValor() %>)
               statusString = "<%= StatusPedido.CANCELADO.getDescricao() %>";
            else if(status == <%= StatusPedido.TRANSITO.getValor() %>)
               statusString = "<%= StatusPedido.TRANSITO.getDescricao() %>";
            else if(status == <%= StatusPedido.ENTREGUE.getValor() %>)
               statusString = "<%= StatusPedido.ENTREGUE.getDescricao() %>";

            let msg = `Status do pedido ficará como <b>`+statusString+`</b>, e não poderá ser desfeita;`;

            const load = $("#loading");


            Dialog.confirm({
               type: "warning",
               title: "Atualizar Pedido",
               message: msg,
               callback: () => {
                   load.show();
                   $.ajax({
                       url: "<%= PedidoURI.DETALHE_ADMIN_URI %>",
                       type: "POST",
                       dataType: "json",
                       data: {
                           operacao: "Alterar",
                           txtStatus: status,
                           txtIdPedido: '<%= pedido.getId() %>'
                       },
                       success: function(data) {
                           load.hide();
                           if (typeof data.error != "undefined" && data.error === true) {
                               Dialog.alert({
                                   message: `Não foi possivel alterar status do pedido!`,
                                   type: "error"
                               });
                               return;
                           }

                           if (typeof data.alert !== "undefined") {
                               Dialog.alert(data.alert);
                           }

                           if (typeof data.redirect !== "undefined") {
                               window.location.href = data.redirect;
                           }
                       }
                   })
               }
           });
        });
    });
</script>