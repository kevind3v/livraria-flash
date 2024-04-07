<%@ page import="support.URI.ClienteURI" %>
<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<nav>
    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <form action="<%= ClienteURI.PERFIL_URI %>" method="post">
            <button type="submit" class="nav-link ${parametros.nav == "cli_perfil" ? 'active' : ''}" name="operacao" value="ConsultarPorId">Dados Pessoais</button>
        </form>
        <form action="<%= ClienteURI.PERFIL_ENDERECO_URI %>" method="post">
            <button type="submit" class="nav-link ${parametros.nav == "cli_enderecos" ? 'active' : ''}" name="operacao" value="Consultar">Seus Endereços</button>
        </form>
        <form action="<%= ClienteURI.PERFIL_CARTOES_URI %>" method="post">
            <button type="submit" class="nav-link ${parametros.nav == "cli_cartoes" ? 'active' : ''}" name="operacao" value="Consultar">Cartões</button>
        </form>
        <form action="<%= ClienteURI.PERFIL_SEGURANCA_URI %>" method="post">
            <button type="submit" class="nav-link ${parametros.nav == "cli_seguranca" ? 'active' : ''}" name="operacao" value="Consultar">Dados Login</button>
        </form>
        <form action="<%= ClienteURI.PERFIL_CUPONS_URI %>" method="post">
            <button type="submit" disabled class="nav-link ${parametros.nav == "cli_cupons" ? 'active' : ''}" name="operacao" value="Consultar">Cupons</button>
        </form>
    </div>
</nav>