<%@ page import="database.dominio.Venda.Estoque" %>
<%@ page import="database.dominio.Venda.ItemEstoque" %>
<%@ page import="support.URI.EstoqueURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<% Estoque estoque = (Estoque) request.getSession().getAttribute("estoque"); %>

<jsp:include page="/components/header.jsp"/>

<section class="intro">
    <div class="bg"></div>
    <div class="container py-3">
        <div class="row align-items-center">
            <div class="col-12 my-3 col-md-7 text-center">
                <img class="img-fluid" style="width: 60%;" src="./img/image.svg" alt="">
            </div>
            <div class="col-12 col-md-4 my-3 text-center">
                <span class="brand" style="font-size: 30px;"><i class="fas fa-bolt"></i> Flash</span>
                <span class="text-warning" style="font-size: 13px; font-weight: bold;">Leitura Veloz</span>
                <p style="font-weight: 500;">Seja um leitor relâmpago</p>
            </div>
        </div>
    </div>
</section>

<div class="d-flex justify-content-center">
    <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="./img/banner3.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="./img/banner1.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="./img/banner2.jpg" class="d-block w-100" alt="...">
            </div>
        </div>
        <button class="carousel-control-prev" style="background: transparent; border: none;" type="button" data-target="#carouselExampleControls" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </button>
        <button class="carousel-control-next" style="background: transparent; border: none;" type="button" data-target="#carouselExampleControls" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </button>
    </div>
</div>

<main class="container-fluid">

    <section class="products jumbotron bg-white pt-4 pb-0">
        <div class="container">
            <header class="d-flex align-items-center">
                <h4 style="font-weight: 600;">Destaque</h4>
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
                            <h4 style="text-transform: none; font-size: 14px;"><%= item.getLivro().getTitulo() %></h4>
                            <div class="card-price">R$ <%=item.getValorVenda()%></div>
                            <div class="text-center">
                                <a href="<%= EstoqueURI.DETALHE_LIVRO_URI %>?l=<%=item.getId()%>" class="btn btn-yellow px-5">Detalhe</a>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
                <% } %>
            </div>
        </div>
    </section>
</main>

<jsp:include page="/components/footer.jsp"/>
