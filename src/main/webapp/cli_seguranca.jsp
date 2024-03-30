<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page import="support.URI.UsuarioURI" %>
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

    <jsp:include page="/components/nav-perfil.jsp"/>

    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
            <form class="form-container form-ajax p-4 pb-0" action="<%= UsuarioURI.ALTERAR_LOGIN_URI %>" method="post">
                <div class="msg_response"></div>
                <div class="row">
                    <div class="col-12 col-lg-2"></div>
                    <div class="col-12 col-lg-8">
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtEmail" style="font-size: 13px; margin-bottom: 2px">E-mail</label>
                                    <input type="email" class="form-control" readonly value="<%=cliente.getUsuario().getEmail() %>" required name="txtEmail" id="txtEmail">
                                </div>
                            </div>
                            <div class="col"></div>
                        </div>
                        <h6 class="mb-3 mt-1">Alterar Senha</h6>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtSenhaAtual" style="font-size: 13px; margin-bottom: 2px">Senha Atual<span class="span-required">*</span></label>
                                    <input type="password" class="form-control" placeholder="" required name="txtSenhaAtual" id="txtSenhaAtual">
                                </div>
                            </div>
                            <div class="col"></div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtNovaSenha" style="font-size: 13px; margin-bottom: 2px">Nova Senha<span class="span-required">*</span></label>
                                    <input type="password" class="form-control" placeholder="" required name="txtNovaSenha" id="txtNovaSenha">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtConfirmarSenha" style="font-size: 13px; margin-bottom: 2px">Confirmar Senha<span class="span-required">*</span></label>
                                    <input type="password" class="form-control" placeholder="" required name="txtConfirmarSenha" id="txtConfirmarSenha">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-lg-2"></div>
                </div>
                <input type="hidden" name="operacao" value="Alterar">
                <div class="text-center">
                    <button type="submit" style="width: 400px; font-size: 16px; font-weight: bold;" class="btn btn-lg btn-warning text-end" id="BtnSalvar">
                        Salvar
                    </button>
                </div>
                <div class="text-center mt-3">
                    <a href="#" id="BtnEncerrarConta" class="text-danger" style="font-weight: bold">Encerrar minha conta</a>
                </div>
            </form>
        </div>
    </div>
</main>

<script type="text/javascript">
    $(document).ready(function() {
        $("#BtnEncerrarConta").on('click', function () {
            Dialog.confirm({
                type: "warning",
                message: "Deseja encerrar a conta?"
            })
        })
    });
</script>