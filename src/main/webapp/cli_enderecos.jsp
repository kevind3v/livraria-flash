<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page import="support.URI.EnderecoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<% Cliente cliente = (Cliente) request.getSession().getAttribute("cliente"); %>

<jsp:include page="/components/header.jsp"/>

<style>
    .span-required {
        color: #dc3545;
        margin-left: 2px;
    }

    select, input {
        font-size: 14px !important;
        font-weight: 400;
    }
</style>

<main class="container" style="margin-top: 40px">
    <h3 class="text-left py-2">Sua Conta</h3>

    <hr>

    <jsp:include page="/components/nav-perfil.jsp"/>

    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
            <div class="row mx-2 my-4">
                <%for (Endereco endereco : cliente.getEnderecos()) {%>
                <div class="col-6 mb-3">
                    <div class="card card-endereco" data-cardId="<%=endereco.getId()%>" style="width: 100%; height: 150px">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <h6 class="m-0"
                                    style="font-weight: 800; font-size: 18px"><%=endereco.getIdentificacao()%>
                                </h6>
                                <div>
                                    <button class="btn py-0 px-1" style="font-weight: bold; color: #007bff" onclick="setModalEndereco(<%=endereco.getId()%>)"><i
                                            class="fa-solid fa-pen-to-square"></i> Alterar
                                    </button>
                                    <%if (cliente.getEnderecos().size() > 1) {%>
                                    <button class="btn py-0 px-1" style="font-weight: bold; color: #dc3545"><i
                                            class="fa-regular fa-trash-can"></i> Excluir
                                    </button>
                                    <%} %>
                                </div>
                            </div>
                            <p class="text-muted m-0">CEP: <%=Mascara.cep(endereco.getCep())%>
                            </p>
                            <p class="text-muted mb-0"><%=endereco.getLogradouro()%>, <%=endereco.getNumero()%>
                            </p>
                            <p class="text-muted m-0"><%=endereco.getBairro()%> - <%=endereco.getCidade()%>
                                / <%=endereco.getEstado()%>
                            </p>
                            <input type="hidden" class="campos"
                                   data-indentificador="<%=endereco.getIdentificacao()%>"
                                   data-cep="<%=endereco.getCep()%>"
                                   data-logradouro="<%=endereco.getLogradouro()%>"
                                   data-bairro="<%=endereco.getBairro()%>"
                                   data-cidade="<%=endereco.getCidade()%>"
                                   data-estado="<%=endereco.getEstado()%>"
                                   data-numero="<%=endereco.getNumero()%>"
                                   data-complemento="<%=endereco.getComplemento()%>"
                            >
                        </div>
                    </div>
                </div>
                <%}%>

                <div class="col-6">
                    <a href="#" style=" text-decoration: none;" data-toggle="modal" data-target="#formEndereco">
                        <div class="card" style="width: 100%; height: 150px">
                            <div class="card-body d-flex align-items-center justify-content-center">
                                <h5 style="font-weight: bold; color: #ffd43b"><i class="fa-solid fa-plus mr-2"></i> Novo
                                    Endereço</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal fade" id="formEndereco" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="formEnderecoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <form class="form-container form-ajax p-2 pb-0" action="<%= EnderecoURI.ADICIONAR_URI %>" method="post">
                <div class="modal-header" style="border: none">
                    <h5 class="modal-title" id="formEnderecoLabel" style="font-weight: bold">Cadastro Endereço</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body pt-0">
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label for="txtCep" style="font-size: 13px; margin-bottom: 2px">Identificação <span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtIdentificacao" id="txtIdentificacao"
                                   placeholder="Ex: Minha Casa">
                        </div>

                        <div class="form-group col-md-6">
                            <label for="txtCep" style="font-size: 13px; margin-bottom: 2px">CEP<span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtCep" id="txtCep"
                                   placeholder="Ex: 08493-293">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="txtLogradouro" style="font-size: 13px; margin-bottom: 2px">Logradouro<span
                                class="span-required">*</span></label>
                        <input type="text" class="form-control" readonly required name="txtLogradouro" id="txtLogradouro">
                    </div>

                    <div class="form-group">
                        <label for="txtBairro" style="font-size: 13px; margin-bottom: 2px">Bairro<span
                                class="span-required">*</span></label>
                        <input type="text" class="form-control" readonly required name="txtBairro" id="txtBairro">
                    </div>

                    <div class="row">
                        <div class="col-md-9">
                            <div class="form-group">
                                <label for="txtCidade" style="font-size: 13px; margin-bottom: 2px">Cidade<span
                                        class="span-required">*</span></label>
                                <input type="text" class="form-control" readonly required name="txtCidade" id="txtCidade">
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-group">
                                <label for="txtEstado" style="font-size: 13px; margin-bottom: 2px">Estado<span
                                        class="span-required">*</span></label>
                                <input type="text" class="form-control" readonly required name="txtEstado" id="txtEstado">
                            </div>
                        </div>
                    </div>

                    <div class="form-group col-md-3 px-0">
                        <label for="txtNumero" style="font-size: 13px; margin-bottom: 2px">Número<span
                                class="span-required">*</span></label>
                        <input type="text" class="form-control" required name="txtNumero" id="txtNumero"
                               placeholder="Ex: 82">
                    </div>

                    <div class="form-group">
                        <label for="txtComplemento" style="font-size: 13px; margin-bottom: 2px">Complemento</label>
                        <input type="text" class="form-control" name="txtComplemento" id="txtComplemento"
                               placeholder="Ex: Apt 320, Bloco A">
                    </div>
                </div>
                <input type="hidden" name="txtEnderecoId" id="txtEnderecoId">
                <input type="hidden" name="operacao" id="operacaoEndereco" value="Salvar">
                <div class="modal-footer" style="border: none">
                    <button type="submit" style="font-size: 16px; font-weight: bold;"
                            class="btn btn-lg btn-warning text-end" id="BtnSalvar">
                        Salvar
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- Início do trecho de código JavaScript --%>
<script type="text/javascript">
    $(document).ready(function () {
        setCampoEndereco();

        $('#formEndereco').on('hidden.bs.modal', function (event) {
            $('#operacaoEndereco').val("Salvar");
            $('#formEndereco .modal-title').text('Cadastro Endereço');
            $("#txtIdentificacao").val("");
            $("#txtCep").val("");
            $("#txtEnderecoId").val("");
            $("#txtLogradouro").val("");
            $("#txtBairro").val("");
            $("#txtCidade").val("");
            $("#txtEstado").val("");
            $("#txtNumero").val("");
            $("#txtComplemento").val("");
        })
    });

    function setModalEndereco(id) {
        const card = $('.card-endereco[data-cardId=\'' + id + '\']');

        if (card.length > 0) {
            const data = (card.find(".campos")).data();
            $("#txtEnderecoId").val(id);
            $("#txtIdentificacao").val(data.indentificador);
            $("#txtCep").val(data.cep);
            $("#txtLogradouro").val(data.logradouro);
            $("#txtBairro").val(data.bairro);
            $("#txtCidade").val(data.cidade);
            $("#txtEstado").val(data.estado);
            $("#txtNumero").val(data.numero);
            $("#txtComplemento").val(data.complemento);
            $('#operacaoEndereco').val("Alterar");

            $('#formEndereco .modal-title').text('Editar Endereço');

            $('#formEndereco').modal({
                keyboard: false
            })
        }
    }
</script>
<%-- Fim do trecho de código JavaScript --%>