<%@ page import="support.URI.UsuarioURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<header class="header navbar navbar-expand-md navbar-light bg-white fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand brand" href="">
            <i class="fas fa-bolt" style="color: #ffd43b"></i> Flash
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#nav" aria-controls="navMobile" aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="nav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a href="" class="nav-link text-secondary">
                        <i class="fa-solid fa-table-columns"></i> Inicio
                    </a>
                </li>
                <li class="nav-item ml-md-3">
                    <a href="<%= UsuarioURI.LOGIN_URI %>" class="btn btn-yellow">Sair <i class="fa-solid fa-arrow-right-from-bracket"></i></a>
                </li>
            </ul>
        </div>
    </div>
</header>