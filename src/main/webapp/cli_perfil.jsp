<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
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
            <form class="form-container form-ajax p-4 pb-0" action="<%= ClienteURI.PERFIL_ALTERAR_URI %>" method="post">
                <div class="msg_response"></div>
                <div class="row">
                    <div class="col-12 col-lg-2"></div>
                    <div class="col-12 col-lg-8">
                        <div class="form-group">
                            <label for="txtNome" style="font-size: 13px; margin-bottom: 2px">Nome Completo<span class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtNome" value="<%=cliente.getNome()%>" id="txtNome">
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtCPF" style="font-size: 13px; margin-bottom: 2px">CPF<span class="span-required">*</span></label>
                                    <input type="text" class="form-control" required name="txtCPF" readonly value="<%=cliente.getCpf()%>" id="txtCPF" placeholder="Ex: 123.456.789-10">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtDtNasc" style="font-size: 13px; margin-bottom: 2px">Data de Nascimento<span class="span-required">*</span></label>
                                    <input type="text" class="form-control" required name="txtDtNasc" readonly id="txtDtNasc" value="<%= Mascara.converterData(cliente.getDtNascimento(), "yyyy-MM-dd", "dd/MM/yyyy") %>" placeholder="Ex: 24/03/2024">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="cbbGenero" style="font-size: 13px; margin-bottom: 2px">Genero<span class="span-required">*</span></label>
                                    <select id="cbbGenero" class="form-control" required name="cbbGenero">
                                        <option value="1" <%if(cliente.getGenero().getValor() == 1){%>selected<%}%>>Masculino</option>
                                        <option value="2" <%if(cliente.getGenero().getValor() == 2){%>selected<%}%>>Feminino</option>
                                        <option value="3" <%if(cliente.getGenero().getValor() == 3){%>selected<%}%>>Prefiro não dizer</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <h6 class="mb-3 mt-1">Contato</h6>

                        <div class="row">
                            <div class="col-4">
                                <div class="form-group">
                                    <label for="cbbTpTelefone" style="font-size: 13px; margin-bottom: 2px">Tipo Telefone<span class="span-required">*</span></label>
                                    <select id="cbbTpTelefone" class="form-control" required name="cbbTpTelefone">
                                        <option value="1" <%if(cliente.getTelefone().getTpTelefone().getValor() == 1){%>selected<%}%>>Fixo</option>
                                        <option value="2" <%if(cliente.getTelefone().getTpTelefone().getValor() == 2){%>selected<%}%>>Celular</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="form-group">
                                    <label for="txtDDD" style="font-size: 13px; margin-bottom: 2px">DDD<span class="span-required">*</span></label>
                                    <input type="text" value=<%=cliente.getTelefone().getDdd() %>   class="form-control" required name="txtDDD" id="txtDDD" placeholder="(11)">
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <label for="txtTelefone" style="font-size: 13px; margin-bottom: 2px">Numero<span class="span-required">*</span></label>
                                    <input type="text" value=<%=cliente.getTelefone().getNumero()%> class="form-control" required name="txtTelefone" id="txtTelefone" placeholder="Ex: 91234-5678">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-lg-2"></div>
                </div>
                <input type="hidden" name="operacao" value="Alterar">
                <div class="text-center">
                    <button type="submit" style="font-size: 16px; font-weight: bold; width: 400px;" class="btn btn-lg btn-warning text-end" id="BtnSalvar">
                        Salvar
                    </button>
                </div>
            </form>
        </div>
        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">...</div>
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