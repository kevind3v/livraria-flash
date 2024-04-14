<%@ page import="support.URI.PedidoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<jsp:include page="/components/header-admin.jsp"/>

<style>
    .card-about {
        width: 200px;
    }
</style>

<main class="container" style="margin-top: 10px">

    <h2 class="text-center py-2">Ações disponiveis</h2>

    <hr>

    <div class="row text-center">

        <div class="col">
            <div class="card-about">
                <a href="<%= PedidoURI.LISTA_ADMIN_URI %>">
                    <i class="fa-solid fa-box-open text-warning" style="font-size: 50px"></i>
                </a>
                <h6 class="my-0 fw-normal mt-3">Pedidos</h6>
            </div>
        </div>

        <div class="col mb-5">
            <div class="card-about">
                <a href="/EcomerceLivroLES/adm-grafico">
                    <i class="fa-solid fa-chart-line text-warning" style="font-size: 50px"></i>
                </a>
                <h6 class="my-0 fw-normal mt-3">Gráficos</h6>
            </div>
        </div>

        <div class="col">
            <div class="card-about">
                <a href="/EcomerceLivroLES/adm-grafico">
                    <i class="fa-solid fa-retweet text-warning" style="font-size: 50px"></i>
                </a>
                <h6 class="my-0 fw-normal mt-3">Trocas</h6>
            </div>
        </div>

        <div class="col">
            <div class="card-about">
                <a href="/EcomerceLivroLES/adm-grafico">
                    <i class="fa-solid fa-boxes-stacked text-warning" style="font-size: 50px"></i>
                </a>
                <h6 class="my-0 fw-normal mt-3">Estoque</h6>
            </div>
        </div>

        <div class="col">
            <div class="card-about">
                <a href="/EcomerceLivroLES/adm-grafico">
                    <i class="fa-solid fa-users text-warning" style="font-size: 50px"></i>
                </a>
                <h6 class="my-0 fw-normal mt-3">Clientes</h6>
            </div>
        </div>


    </div>


</main>
