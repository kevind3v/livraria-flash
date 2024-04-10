<%@ page import="database.dominio.Venda.Estoque" %>
<%@ page import="database.dominio.Venda.ItemEstoque" %>
<%@ page import="support.URI.EstoqueURI" %>
<%@ page import="database.dominio.Livro.Livro" %>
<%@ page import="database.dominio.Livro.Autor" %>
<%@ page import="database.dominio.Livro.Categoria" %>
<%@ page import="support.URI.CarrinhoURI" %>
<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="database.dominio.Venda.Carrinho" %>
<%@ page import="database.dominio.Venda.ItemCarrinho" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="support.URI.EnderecoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
    Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
    String mensagem = (String) request.getAttribute("mensagemErro");
%>

<style>
    input.quantity-input {
        width: 60px; border: none; text-align: center; background-color: #ffd53b57; padding: 5px; font-weight: bold; border-radius: 10px;
    }
    input.quantity-input::-webkit-inner-spin-button,
    input.quantity-input::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
    input.quantity-input {
        -moz-appearance: textfield;
    }
</style>

<jsp:include page="/components/header.jsp"/>

<div class="container">
    <h2 class="mt-4" style="font-weight: bold;">Carrinho de Compras</h2>
    <div class="msg_response"></div>
    <table class="table table-bordered mt-4">
        <thead>
        <tr>
            <th scope="col">Produto</th>
            <th scope="col">Preço</th>
            <th scope="col" style="width: 150px;">Quantidade</th>
            <th scope="col">Total</th>
            <th scope="col" style="width: 80px;"></th> <!-- Cabeçalho para o botão de remoção -->
        </tr>
        </thead>
        <tbody>
        <!-- Exemplo de produto no carrinho -->
        <%

            if(carrinho!=null){
                if(carrinho.getItens() != null){
                    for(ItemCarrinho item : carrinho.getItens()){
        %>
        <tr>
            <td><%=item.getLivro().getTitulo()%></td>
            <td>R$ <%=item.getValorVenda()%></td>
            <td>
                <%=item.getQuantidade()%>
            </td>
            <td>R$ <%= item.getValorVenda().multiply(new BigDecimal(item.getQuantidade())) %></td></td>
            <td class="">
                <div class="d-flex justify-content-center align-items-center">
                    <a data-toggle="collapse" href="#collapse<%=item.getId()%>" aria-expanded="false" aria-controls="collapseExample">
                        <i class="fa-solid fa-pen-to-square text-primary" style="font-size: 20px;"></i>
                    </a>
                    <a href="#" onclick="setExcluirItem(<%=item.getId()%>)">
                        <i class="fas fa-trash-alt text-danger ml-3" style="font-size: 20px;"></i>
                    </a>
                </div>
            </td>
        </tr>
        <div class="collapse" id="collapse<%=item.getId()%>">
            <form action="<%= CarrinhoURI.EDITAR_ITEM %>" method="post">
                <input type="hidden" name="idItem" value="<%=item.getId()%>">
                <div class="row">
                    <div class="d-flex">
                        <button type="button" class="quantity-down px-2" style="border: none; background: transparent;">
                            <i class="fas fa-minus"></i>
                        </button>
                        <input onkeypress="return event.charCode >= 48 && event.charCode <= 57" type="number" class="quantity-input" value="<%=item.getQuantidade()%>" min="1" id="txtQtd" name="txtQtd">
                        <button type="button" class="quantity-up px-2" style="border: none; background: transparent;">
                            <i class="fas fa-plus"></i>
                        </button>
                    </div>
                    <div class="col-4">
                        <button class="btn btn-warning" name="operacao" value="Editar">Salvar</button>
                    </div>
                </div>
            </form>
        </div>
        <%}}} %>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="3"><strong>Total</strong></td>
            <td><strong>R$ <%=carrinho.getValorTotal() %></strong></td>
        </tr>
        </tfoot>
    </table>

    <div class="text-right">
        <a href="<%= EstoqueURI.LISTA_URI %>" style="font-size: 16px; font-weight: bold;" class="btn btn-lg btn-b-green text-ednd">
            Continuar Comprando
        </a>
        <a href="<%= EnderecoURI.SELECIONAR_URI %>" style="font-size: 16px; font-weight: bold;" class="btn btn-lg btn-warning text-end <%if(carrinho.getItens().size() == 0){%>disabled<%}%>" name="operacao" value="Salvar">
            Finalizar Compra
        </a>
    </div>

</div>


<script type="text/javascript">
    $(document).ready(function() {
        <% if(mensagem != null){%>
        Dialog.response({
            type: "warning",
            message: `<%=mensagem%>`
        });
        <%request.getSession().setAttribute("mensagem", null);%>
        <%}%>

        const vMin = 1;
        const vMax = 100;

        $('.quantity-input').on('blur', function() {
            const valor = parseInt($(this).val());
            if ($(this).val().trim() == '') {
                $(this).val(1);
                atualizarBotoes(1);
            } else {
                $(this).val(valor < vMin ? vMin : (valor > vMax ? vMax : valor));
                atualizarBotoes(valor);
            }
        });

        $('.quantity-up').on('click', function() {
            atualizarQuantidade(1);
        });

        $('.quantity-down').on('click', function() {
            atualizarQuantidade(-1);
        });

        function atualizarQuantidade(incremento) {
            let valor = parseInt($('.quantity-input').val()) + incremento;
            valor = Math.min(Math.max(valor, vMin), vMax);
            $('.quantity-input').val(valor);
            atualizarBotoes(valor);
        }

        function atualizarBotoes(valor) {
            $('.quantity-up').prop('disabled', valor >= vMax);
            $('.quantity-down').prop('disabled', valor <= vMin);
        }

        // Desabilitar botão "up" se a quantidade inicial for igual a vMax
        atualizarBotoes(parseInt($('.quantity-input').val()));
    });

    function setExcluirItem(id) {
        const load = $("#loading");

        Dialog.confirm({
            type: "warning",
            message: "Deseja excluir o item?",
            callback: () => {
                load.show();
                $.ajax({
                    url: "<%= CarrinhoURI.EXCLUIR_ITEM %>",
                    type: "POST",
                    dataType: "json",
                    data: {
                        operacao: "Remover",
                        idItem: id
                    },
                    success: function(data) {
                        load.hide();
                        if (typeof data.error != "undefined" && data.error === true) {
                            Dialog.alert({
                                message: `Não foi possivel excluir item!`,
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
                    },
                    error: function() {
                        Dialog.alert({
                            message: `Não foi possivel excluir item`,
                            type: "error"
                        });
                    }
                });
            }
        })
    }
</script>
