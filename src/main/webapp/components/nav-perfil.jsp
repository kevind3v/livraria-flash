<%@ page import="support.URI.ClienteURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<nav>
    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <form action="<%= ClienteURI.PERFIL_URI %>" method="post">
            <button type="submit" class="nav-link active" name="operacao" value="ConsultarPorId">Dados Pessoais</button>
        </form>
        <form action="<%= ClienteURI.PERFIL_ENDERECO_URI %>" method="post">
            <button type="submit" class="nav-link" name="operacao" value="Consultar">Seus Endereços</button>
        </form>
        <form action="" method="post">
            <button type="submit" class="nav-link disabled" name="operacao" value="Consultar">Forma de Pagamento</button>
        </form>
        <form action="" method="post">
            <button type="submit" class="nav-link disabled" name="operacao" value="Consultar">Segurança</button>
        </form>
        <form action="" method="post">
            <button type="submit" class="nav-link disabled" name="operacao" value="Consultar">Cupons</button>
        </form>
    </div>
</nav>