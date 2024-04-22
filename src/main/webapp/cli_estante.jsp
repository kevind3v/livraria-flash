<%@ page import="database.dominio.Venda.Estoque" %>
<%@ page import="database.dominio.Venda.ItemEstoque" %>
<%@ page import="support.URI.EstoqueURI" %>
<%@ page import="database.dominio.Livro.Livro" %>
<%@ page import="database.dominio.Livro.Autor" %>
<%@ page import="database.dominio.Livro.Categoria" %>
<%@ page import="support.URI.CarrinhoURI" %>
<%@ page import="support.Mascara" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Estoque estoque = (Estoque) request.getSession().getAttribute("estoque");
%>

<jsp:include page="/components/header.jsp"/>

<style>
    .search-input {
        transition: all 0.3s;
        border-radius: 30px;
    }

    .search-input:focus {
        border-color: #007bff;
        box-shadow: 0 0 0 0.25rem rgba(0, 123, 255, 0.25);
    }

    #search-icon {
        border-radius: 0 30px 30px 0;
    }
</style>


    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3 text-center">
                <div class="input-group mb-1">
                    <input type="text" required name="txtPesquisa" class="form-control search-input" placeholder="Pesquisar" aria-label="Search" aria-describedby="search-icon">
                    <button class="btn btn-warning" type="button" id="search-icon"><i class="fas fa-search"></i></button>
                </div>
                <div class="form-group mb-0">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input campo-pesquisa me-1" checked type="checkbox" name="titulo" id="swtTitulo">
                        <label class="form-check-label stretched-link" for="swtTitulo">Titulo</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input campo-pesquisa me-1" type="checkbox" name="autor" id="swtAutores">
                        <label class="form-check-label stretched-link" for="swtAutores">Autores</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input campo-pesquisa me-1" type="checkbox" name="categoria" id="swtCategorias">
                        <label class="form-check-label stretched-link" for="swtCategorias">Categorias</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input campo-pesquisa me-1" type="checkbox" name="isbn" id="swtIsbn">
                        <label class="form-check-label stretched-link" for="swtIsbn">ISBN</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input campo-pesquisa me-1" type="checkbox" name="editora" id="swtEditora">
                        <label class="form-check-label stretched-link" for="swtEditora">Editora</label>
                    </div>
                </div>
                <a id="limpar" class="text-warning" href="<%= EstoqueURI.LISTA_URI %>" style="font-weight: bold; text-decoration: none">Limpar Filtro</a>
            </div>
        </div>
    </div>

<main class="container-fluid">

    <section class="products jumbotron bg-white pt-4 pb-0">
        <div class="container">
            <header class="d-flex align-items-center">
                <h4 style="font-weight: 600;">Nossos Livros</h4>
                <span class="text-muted ml-2">Temos <%= (estoque != null) ? estoque.getItens().size() : "0" %> livro(s) na estante</span>
            </header>
            <div class="row">
                <% if(estoque != null){ %>
                <% for(ItemEstoque item : estoque.getItens()){ %>
                <div class="col-sm-12 col-md-6 col-lg-3 mb-4">
                    <div class="card-product">
                        <div class="product-tumb">
                            <img class="" src="./img/livros/<%=item.getLivro().getUrlCapa()%>" alt="<%=item.getLivro().getTitulo()%>">
                        </div>
                        <div class="card-content pt-0">
                            <% if(item.getQuantidade() == 0){ %>
                                <h4 class="mb-1 text-muted" style="text-transform: none; font-size: 16px;">Esgotado</h4>
                            <% } %>
                            <h4 class="mb-1" style="text-transform: none; font-size: 15px;"><%= item.getLivro().getTitulo() %></h4>
                            <span class="text-muted" style="font-size: 12px">por <%= Mascara.listaParaString(item.getLivro().getAutores()) %></span>
                            <div class="card-price mt-3">R$ <%=item.getValorVenda()%></div>
                            <div class="text-center">
                                <a href="<%= EstoqueURI.DETALHE_LIVRO_URI %>?l=<%=item.getLivro().getId()%>" class="btn btn-yellow px-5">Detalhe</a>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>
            <% if(estoque == null || estoque.getItens().isEmpty()){ %>
            <div class="text-center my-5">
                <h6 class="text-muted">Nenhum livro nessa estante</h6>
            </div>
            <% } %>
        </div>
    </section>
</main>

<jsp:include page="/components/footer.jsp"/>

<%-- Início do trecho de código JavaScript --%>
<script type="text/javascript">
    $(document).ready(function () {
        let search = getParameterByName('s');

        if (search != null && typeof search === 'string') {
            $('input[name="txtPesquisa"]').val(search);
            $('#limpar').show();
        } else {
            $('#limpar').hide();
        }

        var campos = getParameterByName('campos');

        if (campos) {
            var camposArray = campos.split('-');

            camposArray.forEach(function(campo) {
                $('input[type="checkbox"][name="'+campo+'"]').prop('checked', true);
            });
        }

        $('#search-icon').on('click', function () {
            let s = $('input[name="txtPesquisa"]');
            let campos = $('.campo-pesquisa:checked');
            let camposSelecionados = campos.map(function() {
                return $(this).attr('name');
            }).get();

            if (s.val().trim() === '') {
                alert('Digite a pesquisa');
                return;
            }

            if (camposSelecionados.length === 0) {
                alert('Seleciona um campo para pesquisa');
                return;
            }

            window.location.href = '?s=' + encodeURIComponent(s.val().trim()) + '&campos=' + camposSelecionados.join('-');
        });
    });
</script>