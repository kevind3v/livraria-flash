<%@ page import="database.dominio.Usuario.Usuario" %>
<%@ page import="support.URI.UsuarioURI" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .menu-icon {
        font-size: 22px!important;
        transition: color 0.3s;
    }
    .menu-icon:hover{
        color: #ffd43b;
        transition: color 0.3s;
    }

    .search-container {
        width: 300px;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 20px !important;
        border: 1.5px solid #ffd43b;
    }
    .search-input {
        border-radius: 20px !important;
        font-size: 16px;
        width: 300px;
        outline: none!important;
        box-shadow: none!important;
        padding-left: 0;
    }
</style>

<% Usuario usr = (Usuario) request.getSession().getAttribute("usuario"); %>

<header class="header navbar navbar-expand-md navbar-light bg-white">
    <div class="container">
        <a class="navbar-brand brand" href="<%= UsuarioURI.CLIENTE_HOME_URI %>">
            <i class="fas fa-bolt"></i> Flash
        </a>
        <ul class="navbar-nav mr-auto icons-nav">
            <li class="nav-item">
                <a href="<%= UsuarioURI.CLIENTE_HOME_URI %>" class="nav-link">Estante de Livros</a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">Meus Pedidos</a>
            </li>
        </ul>

        <div class="" id="nav">
            <ul class="navbar-nav ml-auto d-flex align-items-center icons-nav">
                <% if(usr != null){%>
                    <% if(usr.isAdmin()){%>
                        <li class="nav-item">
                            <a href="<%= UsuarioURI.ADMIN_INDEX_URI %>" class="btn btn-yellow" type="submit">Dashboard</a>
                        </li>
                    <%} else {%>
                        <li class="nav-item">
                            <a class="nav-link" href="#"><i class="fas fa-shopping-cart menu-icon"></i></a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link" href="#" role="button" data-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user menu-icon"></i>
                            </a>
                            <div class="dropdown-menu">
                                <form action="<%= ClienteURI.PERFIL_URI %>" method="post">
                                    <button type="submit" class="dropdown-item" name="operacao" value="ConsultarPorId">Sua Conta</button>
                                </form>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="<%= UsuarioURI.LOGIN_URI %>">Sair</a>
                            </div>
                        </li>
                    <%}%>
                <%} else {%>
                    <li class="nav-item">
                        <a href="<%= UsuarioURI.LOGIN_URI %>" class="btn btn-yellow" type="submit">Faça Login</a>
                    </li>
                <%}%>
            </ul>
        </div>
    </div>
</header>