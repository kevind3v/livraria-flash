<%@ page import="support.URI.PedidoURI" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="support.Mascara" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<jsp:include page="/components/header-admin.jsp"/>

<% List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes"); %>

<main class="container" style="margin-top: 10px">
    <h2 class="mt-4" style="font-weight: bold;">Lista de Clientes</h2>
    <div class="msg_response"></div>
    <table class="table table-bordered mt-4">
        <thead>
        <tr>
            <th scope="col" class="text-center">ID</th>
            <th scope="col" class="text-center">Nome</th>
            <th scope="col" class="text-center">Gênero</th>
            <th scope="col" class="text-center">CPF</th>
            <th scope="col" class="text-center">Data de Nascimento</th>
            <th scope="col" class="text-center">Email</th>
            <th scope="col" class="text-center">Telefone</th>
            <th scope="col" style="width: 80px;"></th> <!-- Cabeçalho para o botão de remoção -->
        </tr>
        </thead>
        <tbody>
        <% if(clientes != null)
            for(Cliente cliente: clientes){%>

        <tr>
            <td class="text-center align-middle" id="id">
                #<%=cliente.getId()%>
            </td>

            <td class="align-middle">
                <%=cliente.getNome()%>
            </td>

            <td class="text-center align-middle">
                <%=cliente.getGenero().getDescricao()%>
            </td>

            <td class="text-center align-middle cpf">
                <%=cliente.getCpf()%>
            </td>

            <td class="text-center align-middle">
                <%=Mascara.dataExtensa(cliente.getDtNascimento())%>
            </td>

            <td class="align-middle">
                <%=cliente.getUsuario().getEmail()%>
            </td>

            <td class="align-middle">
                <%=cliente.getTelefone().getDdd() %>

                <span class="telefone"><%=cliente.getTelefone().getNumero()%></span>
            </td>
            <td>
                <div class="d-flex justify-content-center align-items-center">
                    <a href="<%= ClienteURI.DETALHE_ADMIN_URI %>?c=<%= cliente.getId() %>" title="Detalhe do Pedido">
                        <i class="fas fa-eye text-secondary" style="font-size: 20px;"></i>
                    </a>
                </div>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
</main>

<script type="text/javascript">
    $(document).ready(function() {
        $('.cpf').mask("000.000.000-00");
        $(".telefone").mask("90000-0000", { reverse: true });
    });

</script>