<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<% Cliente cliente = (Cliente) request.getSession().getAttribute("cliente"); %>

<jsp:include page="/components/header.jsp"/>

<style>
    .span-required {
        color: #dc3545;
        margin-left: 2px;
    }
    select, input {
        font-size: 14px!important;
        font-weight: 400;
    }
</style>

<main class="container" style="margin-top: 40px">
    <h3 class="text-left py-2">Sua Conta</h3>

    <hr>

    <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
            <form action="<%= ClienteURI.PERFIL_URI %>" method="post">
                <button type="submit" class="nav-link" name="operacao" value="ConsultarPorId">Dados Pessoais</button>
            </form>
            <form action="<%= ClienteURI.PERFIL_ENDERECO_URI %>" method="post">
                <button type="submit" class="nav-link active" name="operacao" value="Consultar">Seus Endereços</button>
            </form>
        </div>
    </nav>
    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
            <div class="row mx-2 my-4">
                <%for(Endereco endereco : cliente.getEnderecos()){%>
                <div class="col-6 mb-3">
                    <div class="card" style="width: 100%; height: 150px">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <h6 class="m-0" style="font-weight: 800; font-size: 18px"><%=endereco.getIdentificacao()%></h6>
                                <div>
                                    <button class="btn py-0 px-1" style="font-weight: bold; color: #007bff"><i class="fa-solid fa-pen-to-square"></i> Alterar</button>
                                    <%if(cliente.getEnderecos().size() > 1){%>
                                    <button class="btn py-0 px-1" style="font-weight: bold; color: #dc3545"><i class="fa-regular fa-trash-can"></i> Excluir</button>
                                    <%} %>
                                </div>
                            </div>
                            <p class="text-muted m-0">CEP: <%=Mascara.cep(endereco.getCep())%></p>
                            <p class="text-muted mb-0"><%=endereco.getLogradouro()%>, <%=endereco.getNumero()%></p>
                            <p class="text-muted m-0"><%=endereco.getBairro()%> - <%=endereco.getCidade()%> / <%=endereco.getEstado()%></p>
                        </div>
                    </div>
                </div>
                <%}%>

                <div class="col-6">
                    <a href="#" style=" text-decoration: none;">
                        <div class="card" style="width: 100%; height: 150px">
                            <div class="card-body d-flex align-items-center justify-content-center">
                                <h5 style="font-weight: bold; color: #ffd43b"><i class="fa-solid fa-plus mr-2"></i> Novo Endereço</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript">
    $(document).ready(function() {
        $("#txtCPF").mask("000.000.000-00");
        $("#txtDDD").mask("00");
        $("#txtTelefone").mask("90000-0000", { reverse: true });
        $("#txtCep").mask("00000-000");
        $("#txtDtNasc").mask("00/00/0000");
        $('#txtDtNasc').datepicker({
            dateFormat: 'dd/mm/yy', // Definir o formato desejado
            changeMonth: true,
            changeYear: true,
            yearRange: '1900:+0' // Definir o intervalo de anos desejado
        });
    });
</script>