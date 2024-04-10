<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page import="database.dominio.Venda.EnderecoEntrega" %>
<%@ page import="database.dominio.Venda.Carrinho" %>
<%@ page import="database.dominio.Venda.ItemCarrinho" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="support.URI.EnderecoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
    Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
    EnderecoEntrega endEntrega = (EnderecoEntrega) request.getSession().getAttribute("endSelecionado");
//    String mensagem = (String) request.getAttribute("mensagemErro");
%>

<jsp:include page="/components/header.jsp"/>

<style>
    .checkout-summary {
        background-color: #f8f9fa;
        padding: 20px;
        border-radius: 10px;
    }

    .summary-item {
        margin-bottom: 10px;
    }

    .summary-label {
        font-size: 14px;
        font-weight: 500;
    }

    .summary-value {
        float: right;
        font-weight: bold;
    }

    .breadcrumb-timeline-container {
        max-width: 100%;
        width: 100%;
        display: block;
        margin-bottom: 10px;
        padding-left: 0 !important;
        padding-right: 0 !important
    }


    .breadcrumb-timeline {
        list-style-type: none;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 3px 40px;
        margin: 0
    }

    .breadcrumb-timeline .breadcrumb-timeline-item {
        width: 19px;
        border: none;
        text-align: center;
        display: flex;
        flex-direction: column;
        align-items: center;
        background: transparent;
        cursor: pointer;
        color: currentColor;
        fill: currentColor;
        opacity: .6
    }

    .breadcrumb-timeline .breadcrumb-timeline-item.current {
        opacity: 1;
        width: 25px
    }

    .breadcrumb-timeline .breadcrumb-timeline-item.current .breadcrumb-timeline-item-icon {
        width: 35px;
        height: 35px;
        line-height: 20px;
        margin-top: -3px;
        margin-bottom: -3px;
        background: #ffd53b;
        border: none;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .breadcrumb-timeline .breadcrumb-timeline-item.disabled {
        pointer-events: none
    }

    .breadcrumb-timeline .breadcrumb-timeline-item .breadcrumb-timeline-item-icon {
        width: 30px;
        height: 30px;
        border: 1px solid;
        border-radius: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .breadcrumb-timeline .breadcrumb-timeline-item .breadcrumb-timeline-item-text {
        margin: 5px 0 0
    }

    .breadcrumb-timeline .breadcrumb-timeline-line {
        flex-grow: 1;
        margin: 5px;
        height: 1px;
        background: currentColor;
        opacity: .6;
        position: relative;
        width: 100%;
        top: -14px;
    }

    .card-end:hover {
        cursor: pointer;
    }
</style>


<div class="container mt-4">
    <div class="row">
        <div class="col-md-8">
            <ul class="breadcrumb-timeline">
                <li><button class="breadcrumb-timeline-item disabled" aria-disabled="true" tabindex="-1">
                    <div class="breadcrumb-timeline-item-icon"><i class="fas fa-shopping-cart" style="font-size: 10px;"></i></div>
                    <p class="breadcrumb-timeline-item-text">Carrinho</p>
                </button></li>
                <li class="breadcrumb-timeline-line"></li>
                <li><button class="breadcrumb-timeline-item current">
                    <div class="breadcrumb-timeline-item-icon"><i class="fas fa-truck-loading" style="font-size: 14px;"></i></div>
                    <p class="breadcrumb-timeline-item-text">Entrega</p>
                </button></li>
                <li class="breadcrumb-timeline-line"></li>
                <li><button class="breadcrumb-timeline-item disabled" aria-disabled="true" tabindex="-1">
                    <div class="breadcrumb-timeline-item-icon"><i class="fas fa-credit-card" style="font-size: 10px;"></i></div>
                    <p class="breadcrumb-timeline-item-text">Pagamento</p>
                </button></li>
            </ul>

            <div class="d-flex justify-content-between align-items-end mt-3">
                <h6 class="mb-0" style="font-weight: bold;">Endereço de Entrega</h6>
                <button class="btn btn-warning" style="font-weight: 500; font-size: 14px;" data-toggle="modal" data-target="#formEndereco"><i
                        class="fas fa-plus"></i> Adicionar novo endereço</button>
            </div>


            <%if( cliente != null){%>
            <% if (endEntrega != null) { %>
            <% Endereco endE = endEntrega.getEndereco(); %>
            <div class="card my-2">
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div class="custom-control custom-radio d-flex align-items-center">
                        <input type="radio" checked id="end<%=endE.getId()%>" name="endereco" value="<%=endE.getId()%>" class="custom-control-input">
                        <label class="custom-control-label d-flex flex-column card-end" for="end<%=endE.getId()%>">
                            <span style="font-weight: 700;"><%=endE.getIdentificacao()%></span>
                            <span class="text-muted" style="font-size: 13px; font-weight: 500;"><%=endE.getLogradouro()%>, <%=endE.getNumero()%></span>
                            <span class="text-muted" style="font-size: 13px; font-weight: 500;"><%=endE.getBairro()%>, <%=endE.getCidade()%> - <%=endE.getEstado()%></span>
                            <span class="text-muted" style="font-size: 13px; font-weight: 500;">CEP: <%=Mascara.cep(endE.getCep())%></span>
                        </label>
                    </div>
                </div>
            </div>
            <% } %>
            <%for(Endereco end : cliente.getEnderecos()){%>
            <% if (endEntrega == null || endEntrega.getEndereco().getId() != end.getId()) { %>
            <div class="card my-2">
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div class="custom-control custom-radio d-flex align-items-center">
                        <input type="radio" id="end<%=end.getId()%>" name="endereco" value="<%=end.getId()%>" class="custom-control-input">
                        <label class="custom-control-label d-flex flex-column card-end" for="end<%=end.getId()%>">
                            <span style="font-weight: 700;"><%=end.getIdentificacao()%></span>
                            <span class="text-muted" style="font-size: 13px; font-weight: 500;"><%=end.getLogradouro()%>, <%=end.getNumero()%></span>
                            <span class="text-muted" style="font-size: 13px; font-weight: 500;"><%=end.getBairro()%>, <%=end.getCidade()%> - <%=end.getEstado()%></span>
                            <span class="text-muted" style="font-size: 13px; font-weight: 500;">CEP: <%=Mascara.cep(end.getCep())%></span>
                        </label>
                    </div>
                </div>
            </div>
            <% } %>
            <%}%>
            <%}%>

            <h6 class="mb-0 mt-3" style="font-weight: bold;">Métodos de Envio</h6>
            <div class="card p-3 mt-3 mb-5">
                <div class="card-body p-0">
                    <div class="d-flex justify-content-between align-items-center">
                        <% if (endEntrega != null) { %>
                        <span style="font-size: 18px; font-weight: bold" class="transportadora">Sedex</span>
                        <span class="ml-2 text-muted" style="font-size: 14px">
                            Entrega em <span class="tempo-entrega"><%= endEntrega.getFrete().getPrazo() + " dias uteis" %></span>
                        </span>
                        <% } else {%>
                        <span style="font-size: 18px; font-weight: bold" class="transportadora">--</span>
                        <span class="ml-2 text-muted" style="font-size: 14px">
                            Entrega em <span class="tempo-entrega">--</span>
                        </span>
                        <% }%>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">

            <ul class="list-group">
                <%

                    if(carrinho!=null){
                        if(carrinho.getItens() != null){
                            for(ItemCarrinho item : carrinho.getItens()){
                %>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center">
                        <img class="" src="../../img/livros/<%=item.getLivro().getUrlCapa()%>" alt="<%=item.getLivro().getTitulo()%>" height="100">
                        <div class="d-flex flex-column ml-2 justify-content-start">
                            <span style="font-size: 14px;"><%= Mascara.limitarString(item.getLivro().getTitulo(), 25) %></span>
                            <span class="badge badge-warning badge-pill" style="width: 100px;">R$ <%=item.getValorVenda()%></span>
                        </div>
                    </div>
                    <span class="badge badge-warning badge-pill"><%=item.getQuantidade()%></span>
                </li>
                <%}}} %>

            </ul>
            <!-- Resumo da compra -->
            <div class="checkout-summary">
                <h4 class="mb-4" style="font-size: 20px; font-weight: bold;">Resumo da Compra</h4>
                <div class="summary-item">
                    <span class="summary-label">Subtotal:</span>
                    <span class="summary-value">R$ <%= (carrinho!=null) ? carrinho.getValorTotal() : "0.00" %></span>
                </div>
                <div class="summary-item">
                    <span class="summary-label">Frete:</span>
                    <span class="summary-value" id="frete">R$ <%= (endEntrega != null) ? endEntrega.getFrete().getValor() : "0.00" %></span>
                </div>
                <hr>
                <div class="summary-item">
                    <span class="summary-label">Total:</span>
                    <span class="summary-value">R$ <%= (carrinho!=null) ? ((endEntrega != null) ? endEntrega.getFrete().getValor().add(carrinho.getValorTotal()) : carrinho.getValorTotal()) : "0.00" %></span>
                </div>
            </div>
            <a id="bntContinuar" href="c-pagamento.html" class="btn btn-warning btn-block" style="font-weight: 500; font-size: 14px;">Continuar</a>
        </div>
    </div>
</div>

<div class="modal fade" id="formEndereco" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="formEnderecoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <form class="form-container form-ajax p-2 pb-0" action="<%= EnderecoURI.SELECIONAR_URI %>" method="post">
                <div class="modal-header" style="border: none">
                    <h5 class="modal-title" id="formEnderecoLabel" style="font-weight: bold">Novo Endereço de Entrega</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body pt-0">
                    <div class="msg_response"></div>
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

                    <div class="form-group pt-1 mb-0">
                        <div class="form-check form-switch form-check-reverse">
                            <input class="form-check-input" name="swtSalvarEndereco" type="checkbox" id="flexSwitchCheckReverse">
                            <label class="form-check-label" for="flexSwitchCheckReverse">Salvar endereço no perfil</label>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="operacao" id="operacaoEndereco" value="SalvarNovo">
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


<script type="text/javascript">
    $(document).ready(function () {
        setCampoEndereco();
        $('[name="endereco"]').on('change', function() {
            const load = $("#loading");

            load.show();

            $.ajax({
                url: '<%= EnderecoURI.SELECIONAR_URI %>',
                type: "POST",
                dataType: "json",
                data: {
                    operacao: 'Selecionar',
                    txtEnderecoId: $(this).val()
                },
                success: function (data) {
                    load.hide();
                    if (typeof data.error != "undefined" && data.error === true) {
                        Dialog.alert({
                            message: `Não foi possivel selecionar este endereço!`,
                            type: "error"
                        });
                        return;
                    }

                    if (typeof data.redirect !== "undefined") {
                        window.location.href = data.redirect;
                    }

                    $('.transportadora').html('Sedex');
                    $('#frete').html("R$ " + data.frete.valor);
                    $('.tempo-entrega').html(data.frete.prazo + ((data.frete.prazo > 1) ? ' dias uteis' : 'dia util'))
                }
            });
        });
    });
</script>