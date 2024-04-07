<%@ page import="database.dominio.Venda.Estoque" %>
<%@ page import="database.dominio.Venda.ItemEstoque" %>
<%@ page import="support.URI.EstoqueURI" %>
<%@ page import="database.dominio.Livro.Livro" %>
<%@ page import="database.dominio.Livro.Autor" %>
<%@ page import="database.dominio.Livro.Categoria" %>
<%@ page import="support.URI.CarrinhoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    ItemEstoque item = (ItemEstoque) request.getSession().getAttribute("item");
    Livro livro = item.getLivro();
%>
<jsp:include page="/components/header.jsp"/>

<main class="container mt-5">
    <div class="row">
        <div id="content" class="col-sm-12">
            <div class="row">
                <div class="col-sm-7">
                    <img style="width: 100%;" src="../img/livros/<%=livro.getUrlCapa()%>" alt="">
                    <div class="description-bloco mt-4">
                        <p style="font-size: 14px; text-align: justify; font-weight: 500;">
                            <%=livro.getSinopse() %>
                        </p>
                    </div>
                </div>
                <div class="col-sm-5">
                    <h3><%=livro.getTitulo()%></h3>

                    <ul class="list-unstyled preco my-3">
                        <li>
                            <h4 style="font-weight: bold;"><span class="price-regular">R$ <%=item.getValorVenda()%></span></h4>
                        </li>
                        <li class="text-muted">Unidade Disponiveis: <%=item.getQuantidade()%></li>
                    </ul>

                    <form action="<%= CarrinhoURI.ADICIONAR_ITEM %>" method="post">
                    <div class="d-flex mb-3 mt-4">
                        <div class="">
                                <div class="d-flex">
                                    <button type="button" class="quantity-down px-2" style="border: none; background: transparent;">
                                        <i class="fas fa-minus"></i>
                                    </button>
                                    <style>
                                        input#txtQtd {
                                            width: 60px; border: none; text-align: center; background-color: #ffd53b57; padding: 5px; font-weight: bold; border-radius: 10px;
                                        }
                                        input#txtQtd::-webkit-inner-spin-button,
                                        input#txtQtd::-webkit-outer-spin-button {
                                            -webkit-appearance: none;
                                            margin: 0;
                                        }
                                        input#txtQtd {
                                            -moz-appearance: textfield;
                                        }
                                    </style>
                                    <input onkeypress="return event.charCode >= 48 && event.charCode <= 57" type="number" class="quantity-input" value="1" min="1" max="<%=item.getQuantidade()%>" id="txtQtd" name="txtQtd">
                                    <button type="button" class="quantity-up px-2" style="border: none; background: transparent;">
                                        <i class="fas fa-plus"></i>
                                    </button>
                                </div>
                        </div>
                        <div class="col-8 col-md-9 pl-3">
                            <button type="submit" name="operacao" value="Adicionar" class="btn btn-yellow btn-block"><i class="fas fa-cart-plus"></i> Adicionar ao Carrinho</button>
                        </div>
                    </div>
                    </form>
                    <button type="button" data-toggle="tooltip" class="btn btn-default px-0 text-dark d-flex align-items-center" title="Lista de desejos">
                        <i class="far fa-heart" style="font-size: 30px;"></i> <span class="ml-2" style="font-weight: bold;">Meu Desejo</span>
                    </button>

                    <div class="card my-3">
                        <div class="card-body py-0">
                            <p class="card-text">
                                <ul class="list-unstyled preco my-0">
                                    <li class="mb-2"><b>Autor(es): </b><% for(Autor autor : livro.getAutores()){ %> <%= autor.getNome() %>; <% }%></li>
                                    <li class="mb-2"><b>Categoria(s): </b><% for(Categoria categoria : livro.getCategorias()){ %> <%= categoria.getDescricao() %>; <% }%></li>
                                    <li class="mb-2"><b>ISBN: </b><%=livro.getISBN()%></li>
                                    <li class="mb-2"><b>Ano de publicação: </b><%=livro.getAno()%></li>
                                    <li class="mb-2"><b>Edicao: </b><%=livro.getEdicao()%></li>
                                    <li class="mb-2"><b>Paginas: </b><%=livro.getNumPaginas()%></li>
                                    <li class="mb-2"><b>Dimensoes: </b>(Altura: <%=livro.getDimensao().getAltura()%> cm X
                                        Largura: <%=livro.getDimensao().getLargura()%> cm
                                        X Profundidade: <%=livro.getDimensao().getProfundidade()%> cm);</li>
                                    <li class="mb-2"><b>Peso aproximado:</b> <%=livro.getDimensao().getPeso()%>g</li>
                                </ul>
                            </p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/components/footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function() {
        const vMin = 1;
        const vMax = <%=item.getQuantidade()%>;

        $('#txtQtd').on('blur', function() {
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
            let valor = parseInt($('#txtQtd').val()) + incremento;
            valor = Math.min(Math.max(valor, vMin), vMax);
            $('#txtQtd').val(valor);
            atualizarBotoes(valor);
        }

        function atualizarBotoes(valor) {
            $('.quantity-up').prop('disabled', valor >= vMax);
            $('.quantity-down').prop('disabled', valor <= vMin);
        }

        // Desabilitar botão "up" se a quantidade inicial for igual a vMax
        atualizarBotoes(parseInt($('#txtQtd').val()));
    });
</script>