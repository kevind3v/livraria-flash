<%@ page import="support.URI.UsuarioURI" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% String mensagem = (String) request.getSession().getAttribute("mensagem");
    request.getSession().invalidate();
%>
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

<jsp:include page="/components/banner-cupom.jsp"/>
<br/>
<br/>

<header class="header navbar navbar-expand-md navbar-light bg-white border-0 mt-2">
    <div class="container-fluid">
        <a class="navbar-brand brand" style="font-size: 2rem" href="<%= UsuarioURI.CLIENTE_HOME_URI %>">
            <i class="fas fa-bolt" style="color: #ffd43b;"></i> Flash
        </a>
    </div>
</header>

<main class="container">
    <h2 class="text-center" style="font-weight: bold">Crie sua conta</h2>
    <p class="text-muted text-center">Crie sua conta e comece a leitura</p>
    <div class="card" style="border-radius: 20px">
        <form class="form-container form-ajax p-4 pb-0" action="<%= ClienteURI.CADASTRAR_URI %>" method="post">
            <div class="msg_response"></div>
            <div class="row">
                <div class="col-12 col-lg-6">
                    <div class="form-group">
                        <label for="txtEmail" style="font-size: 13px; margin-bottom: 2px">E-mail<span class="span-required">*</span></label>
                        <input type="email" class="form-control" id="txtEmail" name="txtEmail" required placeholder="Ex: cliente@flash.com">
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="txtSenha" style="font-size: 13px; margin-bottom: 2px">Senha<span class="span-required">*</span></label>
                                <input type="password" class="form-control" required name="txtSenha" id="txtSenha" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label for="confirm" style="font-size: 13px; margin-bottom: 2px">Confirmar Senha<span class="span-required">*</span></label>
                                <input type="password" class="form-control" required name="txtConfirmaSenha" id="confirm" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
                            </div>
                        </div>
                    </div>

                    <h6 class="my-3">Dados cadastrais</h6>

                    <div class="form-group">
                        <label for="txtNome" style="font-size: 13px; margin-bottom: 2px">Nome Completo<span class="span-required">*</span></label>
                        <input type="text" class="form-control" required name="txtNome" id="txtNome">
                    </div>
                    <div class="form-group">
                        <label for="cbbGenero" style="font-size: 13px; margin-bottom: 2px">Genero<span class="span-required">*</span></label>
                        <select id="cbbGenero" class="form-control" required name="cbbGenero">
                            <option value="" selected>Selecione...</option>
                            <option value="1">Masculino</option>
                            <option value="2">Feminino</option>
                            <option value="3">Prefiro não dizer</option>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-group">
                                <label for="txtCPF" style="font-size: 13px; margin-bottom: 2px">CPF<span class="span-required">*</span></label>
                                <input type="text" class="form-control" required name="txtCPF" id="txtCPF" placeholder="Ex: 123.456.789-10">
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label for="txtDtNasc" style="font-size: 13px; margin-bottom: 2px">Data de Nascimento<span class="span-required">*</span></label>
                                <input type="text" class="form-control" required name="txtDtNasc" id="txtDtNasc" placeholder="Ex: 24/03/2024">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cbbTpTelefone" style="font-size: 13px; margin-bottom: 2px">Tipo Telefone<span class="span-required">*</span></label>
                        <select id="cbbTpTelefone" class="form-control" required name="cbbTpTelefone">
                            <option value="" selected>Selecione...</option>
                            <option value="1">Fixo</option>
                            <option value="2">Celular</option>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-3 col-sm-2">
                            <div class="form-group">
                                <label for="txtDDD" style="font-size: 13px; margin-bottom: 2px">DDD<span class="span-required">*</span></label>
                                <input type="text" class="form-control" required name="txtDDD" id="txtDDD" placeholder="(11)">
                            </div>
                        </div>
                        <div class="col-9 col-sm-10">
                            <div class="form-group">
                                <label for="txtTelefone" style="font-size: 13px; margin-bottom: 2px">Numero<span class="span-required">*</span></label>
                                <input type="text" class="form-control" required name="txtTelefone" id="txtTelefone" placeholder="Ex: 91234-5678">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-lg-6">
                    <h6 class="my-3">Endereço</h6>

                    <div class="row">
                        <div class="form-group col-md-6">
                            <label for="txtCep" style="font-size: 13px; margin-bottom: 2px">Identificação <span class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtIdentificacao" id="txtIdentificacao" placeholder="Ex: Minha Casa">
                        </div>

                        <div class="form-group col-md-6">
                            <label for="txtCep" style="font-size: 13px; margin-bottom: 2px">CEP<span class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtCep" id="txtCep" placeholder="Ex: 08493-293">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="txtLogradouro" style="font-size: 13px; margin-bottom: 2px">Logradouro<span class="span-required">*</span></label>
                        <input type="text" class="form-control" readonly required name="txtLogradouro" id="txtLogradouro">
                    </div>

                    <div class="form-group">
                        <label for="txtBairro" style="font-size: 13px; margin-bottom: 2px">Bairro<span class="span-required">*</span></label>
                        <input type="text" class="form-control" readonly required name="txtBairro" id="txtBairro">
                    </div>

                    <div class="row">
                        <div class="col-md-9">
                            <div class="form-group">
                                <label for="txtCidade" style="font-size: 13px; margin-bottom: 2px">Cidade<span class="span-required">*</span></label>
                                <input type="text" class="form-control" readonly required name="txtCidade" id="txtCidade">
                            </div>
                        </div>
                        <div class="col-3">
                            <div class="form-group">
                                <label for="txtEstado" style="font-size: 13px; margin-bottom: 2px">Estado<span class="span-required">*</span></label>
                                <input type="text" class="form-control" readonly required name="txtEstado" id="txtEstado">
                            </div>
                        </div>
                    </div>

                    <div class="form-group col-md-3 px-0">
                        <label for="txtNumero" style="font-size: 13px; margin-bottom: 2px">Número<span class="span-required">*</span></label>
                        <input type="text" class="form-control" required name="txtNumero" id="txtNumero" placeholder="Ex: 82">
                    </div>

                    <div class="form-group">
                        <label for="txtComplemento" style="font-size: 13px; margin-bottom: 2px">Complemento</label>
                        <input type="text" class="form-control" name="txtComplemento" id="txtComplemento" placeholder="Ex: Apt 320, Bloco A">
                    </div>

                    <div class="row py-1">
                        <div class="col-sm-6">
                            <div class="custom-control">
                                <input class="custom-control-input" checked type="checkbox" role="switch" id="entrega" name="swtIsEntrega">
                                <label class="custom-control-label" for="entrega">Endereco de entrega.</label>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="custom-control">
                                <input class="custom-control-input" type="checkbox" role="switch" id="cobranca" name="swtIsCobranca">
                                <label class="custom-control-label" for="cobranca">Endereco de cobranca.</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" name="operacao" value="Salvar">
            <div class="text-right">
                <button type="submit" style="font-size: 16px; font-weight: bold;" class="btn btn-lg btn-warning text-end" id="BtnSalvar">
                    Finalizar Cadastro
                </button>
            </div>
        </form>
    </div>

    <div class="d-flex justify-content-center mt-3">
        <p class="text-center text-muted copyright d-flex align-items-center" style="font-size: 15px">Ja possui cadastro? <a class="nav-link text-warning p-0 pl-2" style="font-weight: bold" href="<%= UsuarioURI.LOGIN_URI %>">Acessar Conta</a></p>
    </div>
    <div class="mt-4">
        <p class="text-center text-muted copyright">&copy 2024 Flash Inc.</p>
    </div>

</main>

<script type="text/javascript">
    $(document).ready(function() {
        $("#txtCPF").mask("000.000.000-00");
        $("#txtDDD").mask("00");
        $("#txtTelefone").mask("90000-0000", { reverse: true });
        $("#txtDtNasc").mask("00/00/0000");

        $('#txtDtNasc').datepicker({
            dateFormat: 'dd/mm/yy', // Definir o formato desejado
            changeMonth: true,
            changeYear: true,
            yearRange: '1900:+0' // Definir o intervalo de anos desejado
        });

        setCampoEndereco();
    });
</script>