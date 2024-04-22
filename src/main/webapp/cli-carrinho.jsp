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

    a.disabled {
        pointer-events: none;
        opacity: 0.5;
        cursor: not-allowed;
    }
    a.disabled i {
        opacity: 0.5;
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
            <td class="m-0 p-0">
                <form action="<%= CarrinhoURI.EDITAR_ITEM %>" method="post" id="formEdit<%=item.getId()%>" style="min-height: 46px">
                    <input type="hidden" name="idItem" value="<%=item.getId()%>">
                    <select id="qtd<%=item.getId()%>" data-item="<%=item.getId()%>" onchange="habilitarEdicao(this);" class="form-control" style="min-height: 46px" name="txtQtd">
                        <% for(int i = 1; i <= 20; i++){ %>
                            <option value="<%= i %>" <%= (i == item.getQuantidade()) ? "selected" : "" %> ><%=i%></option>
                        <%} %>
                    </select>
                    <input type="hidden" name="operacao" value="Editar">
                </form>
            </td>
            <td>R$ <%= item.getValorVenda().multiply(new BigDecimal(item.getQuantidade())) %></td>
            <td class="">
                <div class="d-flex justify-content-center align-items-center">
                    <a data-item="<%=item.getId()%>" class="disabled" href="#">
                        <i class="fa-solid fa-pen-to-square text-primary" style="font-size: 20px;"></i>
                    </a>
                    <a href="#" onclick="setExcluirItem(<%=item.getId()%>)">
                        <i class="fas fa-trash-alt text-danger ml-3" style="font-size: 20px;"></i>
                    </a>
                </div>
            </td>
        </tr>
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
        <a href="<%= EnderecoURI.SELECIONAR_URI %>" style="font-size: 16px; font-weight: bold;" class="btn btn-lg btn-warning text-end <%if(carrinho.getItens().size() == 0){%>disabled<%}%>" id="FinalizarCompra">
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

        // const vMin = 1;
        // const vMax = 100;
        //
        // $('.quantity-input').on('blur', function() {
        //     const valor = parseInt($(this).val());
        //     if ($(this).val().trim() == '') {
        //         $(this).val(1);
        //         atualizarBotoes(1);
        //     } else {
        //         $(this).val(valor < vMin ? vMin : (valor > vMax ? vMax : valor));
        //         atualizarBotoes(valor);
        //     }
        // });
        //
        // $('.quantity-up').on('click', function() {
        //     atualizarQuantidade(1);
        // });
        //
        // $('.quantity-down').on('click', function() {
        //     atualizarQuantidade(-1);
        // });
        //
        // function atualizarQuantidade(incremento) {
        //     let valor = parseInt($('.quantity-input').val()) + incremento;
        //     valor = Math.min(Math.max(valor, vMin), vMax);
        //     $('.quantity-input').val(valor);
        //     atualizarBotoes(valor);
        // }
        //
        // function atualizarBotoes(valor) {
        //     $('.quantity-up').prop('disabled', valor >= vMax);
        //     $('.quantity-down').prop('disabled', valor <= vMin);
        // }
        //
        // // Desabilitar botão "up" se a quantidade inicial for igual a vMax
        // atualizarBotoes(parseInt($('.quantity-input').val()));

        $('a[data-item]').on('click', function() {
            const form = $('#formEdit'+($(this).data().item));
            form.trigger('submit')
        });
    });

    function habilitarEdicao(e)
    {
        const item = $(e);
        const data = item.data();
        $('a[data-item="'+data.item+'"]').removeClass('disabled');
    }

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
