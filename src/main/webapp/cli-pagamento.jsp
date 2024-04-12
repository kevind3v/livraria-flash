<%@ page import="java.util.List" %>
<%@ page import="database.dominio.Venda.*" %>
<%@ page import="support.Mascara" %>
<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.URI.PagamentoURI" %>
<%@ page import="java.util.UUID" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    Pagamento pagamento = (Pagamento) request.getSession().getAttribute("pagamento");
    Carrinho carrinho = pagamento.getCarrinho();
    List<CartaoCompra> cartoes = pagamento.getCartoes();
    List<Cupom> cupons = pagamento.getCupons();
    Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

//    String mensagem = (String) request.getAttribute("mensagemErro");
%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js"></script>

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
</style>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-8">
            <ul class="breadcrumb-timeline mb-4">
                <li><button class="breadcrumb-timeline-item disabled" aria-disabled="true" tabindex="-1">
                    <div class="breadcrumb-timeline-item-icon"><i class="fas fa-shopping-cart"
                                                                  style="font-size: 10px;"></i></div>
                    <p class="breadcrumb-timeline-item-text">Carrinho</p>
                </button></li>
                <li class="breadcrumb-timeline-line"></li>
                <li><button class="breadcrumb-timeline-item disabled">
                    <div class="breadcrumb-timeline-item-icon"><i class="fas fa-truck-loading"
                                                                  style="font-size: 10px;"></i></div>
                    <p class="breadcrumb-timeline-item-text">Entrega</p>
                </button></li>
                <li class="breadcrumb-timeline-line"></li>
                <li><button class="breadcrumb-timeline-item current" aria-disabled="true" tabindex="-1">
                    <div class="breadcrumb-timeline-item-icon"><i class="fas fa-credit-card"
                                                                  style="font-size: 14px;"></i></div>
                    <p class="breadcrumb-timeline-item-text">Pagamento</p>
                </button></li>
            </ul>

            <div class="d-flex justify-content-end">
                <button class="btn btn-warning" style="font-weight: 500; font-size: 14px;" data-toggle="modal" data-target="#formCartao"><i
                        class="fas fa-plus"></i> Adicionar um cartão de crédito</button>
            </div>
            <style>

                .card-cartao:hover {
                    cursor: pointer;
                }
            </style>

            <h5 style="font-weight: bold">Cartões Cadastrados</h5>
            <%if( cliente != null && !cliente.getCartoes().isEmpty()){%>
            <%for(CartaoCredito card : cliente.getCartoes()){%>
            <div class="card my-3 card-cartao-cadastrado" data-id="<%=card.getId()%>">
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div class="custom-control d-flex align-items-center">
                        <input type="checkbox" id="card<%=card.getId()%>" name="cartaoCredito<%=card.getId()%>" value="<%=card.getId()%>" class="cartao-credito custom-control-input">
                        <label class="custom-control-label d-flex flex-column card-cartao" for="card<%=card.getId()%>">
                            <span>Cartão <b><%= card.getNomeIdentificacao() %></b></span>
                            <span class="text-muted" style="font-size: 14px; font-weight: 500;">Terminado em <%= Mascara.extrairUltimosQuatroDigitos(card.getNumero()) %> - Validade <%= card.getDataValidade() %></span>
                        </label>
                    </div>
                    <div>
                        <img src="../img/<%= (card.getBandeira() == Bandeira.VISA) ? "visa-b" : "mastercard-b" %>.png" width="40" alt="">
                    </div>
                </div>
                <div class="card-body py-0 content-valor-cartao" style="display: none;" id="cardValor<%=card.getId()%>">
                    <div class="d-flex justify-content-end">
                        <div class="input-group d-flex align-items-center mb-1 mt-2"  style="width: 500px;">
                            <label for="valor<%=card.getId()%>" class="mr-2 mb-0" style="font-size: 16px; font-weight: 600;">Quanto vai pagar nesse cartão</label>
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">R$</span>
                            </div>
                            <input type="text" id="valor<%=card.getId()%>" class="form-control money" value="10.00">
                            <button class="btn btn-warning salvar-valor ml-2">Salvar</button>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end mb-2">
                        <span class="text-danger" style="font-size: 14px"><b>OBS:</b> O valor minimo para cada cartão é R$ 10.00</span>
                    </div>
                </div>
            </div>
            <%}%>
            <%} else {%>
                <div class="alert alert-warning">
                    Não existe cartões associados a esse perfil!
                </div>
            <%}%>

            <style>
                .dois-cartoes.disabled {
                    pointer-events: none;
                    opacity: 0.5;
                    cursor: not-allowed;
                }
            </style>

            <%if( pagamento.getCartoes() != null && !pagamento.getCartoes().isEmpty()){%>
            <h5 style="font-weight: bold">Cartões Selecionados</h5>
            <%for(CartaoCompra card : pagamento.getCartoes()){%>
            <div class="card my-3 card-cartao-selecionado" data-id="<%=card.getId()%>" data-cartao="<%=card.getCartao().getId()%>">
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center">
                        <a href="#" class="excluir-valor mr-3"><i class="fas fa-trash-alt text-danger" style="font-size: 20px;"></i></a>
                        <label class="d-flex flex-column card-cartao">
                            <span>Cartão <b><%= card.getCartao().getNomeIdentificacao() %></b></span>
                            <span class="text-muted" style="font-size: 14px; font-weight: 500;">Terminado em <%= Mascara.extrairUltimosQuatroDigitos(card.getCartao().getNumero()) %> - Validade <%= card.getCartao().getDataValidade() %></span>
                        </label>
                    </div>
                    <div>
                        <img src="../img/<%= (card.getCartao().getBandeira() == Bandeira.VISA) ? "visa-b" : "mastercard-b" %>.png" width="40" alt="">
                    </div>
                </div>
                <div class="card-body py-0 content-valor-cartao-selecionado" id="cardValorSelect<%=card.getId()%>">
                    <div class="d-flex justify-content-end">
                        <div class="input-group d-flex align-items-center mb-1 mt-2"  style="width: 500px;">
                            <label for="valorSelecionado<%=card.getId()%>" class="mr-2 mb-0" style="font-size: 16px; font-weight: 600;">Quanto vai pagar nesse cartão</label>
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon">R$</span>
                            </div>
                            <input type="text" id="valorSelecionado<%=card.getId()%>" class="form-control money" value="<%= card.getValor() %>">
                            <button class="btn btn-warning alterar-valor ml-2">Salvar</button>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end mb-2">
                        <span class="text-danger" style="font-size: 14px"><b>OBS:</b> O valor minimo para cada cartão é R$ 10.00</span>
                    </div>
                </div>
            </div>
            <%}%>
            <%}%>
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

            <div class="d-flex justify-content-end" style="width: 100%">
                <div class="input-group mb-3 mt-2" style="width: 100%">
                    <label for="cupom" class="mr-2 mb-0" style="font-size: 16px; font-weight: 600;">Cupom</label>
                    <div class="d-flex align-items-center"  style="width: 100%">
                        <input oninput="this.value = this.value.toUpperCase()" type="text" class="form-control" placeholder="Ex: FLASH10" id="cupom">
                        <button class="btn btn-warning ml-2" style="font-weight: 500;" type="button" id="aplicarCupom">Aplicar</button>
                    </div>
                </div>
            </div>

            <div class="cupons-selecionados">
                <%
                    BigDecimal valorDesconto = new BigDecimal("0.00");
                    if(!pagamento.getCupons().isEmpty())
                        for(Cupom c : pagamento.getCupons()){
                            valorDesconto = valorDesconto.add(c.getValor());
                %>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center">
                        <button data-id="<%=c.getId()%>" class="btn remover-cupom"><i class="fas fa-times text-danger"></i></button>
                        <span class="text-info"><%=c.getCodigo()%></span>
                    </div>
                    <span>- R$<%=c.getValor()%></span>
                </div>
                <%} %>
            </div>

            <!-- Resumo da compra -->
            <div class="checkout-summary">
                <h4 class="mb-4" style="font-size: 20px; font-weight: bold;">Resumo da Compra</h4>
                <div class="summary-item">
                    <span class="summary-label">Subtotal:</span>
                    <span class="summary-value">R$ <%=pagamento.getCarrinho().getValorTotal()%></span>
                </div>
                <div class="summary-item">
                    <span class="summary-label">Frete:</span>
                    <span class="summary-value">R$ <%=pagamento.getEndereco().getFrete().getValor()%></span>
                </div>
                <div class="summary-item">
                    <span class="summary-label">Desconto:</span>
                    <span class="summary-value">- R$ <%= valorDesconto %></span>
                </div>
                <hr>
                <div class="summary-item">
                    <span class="summary-label">Total:</span>
                    <span class="summary-value">R$ <%= pagamento.getTotalCompra().subtract(valorDesconto) %></span>
                </div>
                <%if(pagamento.getTotalCompra().subtract(pagamento.getTotalAlocado()).doubleValue() > 0){ %>
                <div class="alert alert-info mt-3" role="alert">
                    Falta R$ <%=pagamento.getTotalCompra().subtract(pagamento.getTotalAlocado())%> para ser alocado nos cartões!
                </div>
                <%} %>
            </div>
            <a id="bntContinuar" href="c-detalhe-pedido.html" class="btn btn-warning btn-block" style="font-weight: 500; font-size: 14px;">Finalizar</a>
        </div>
    </div>
</div>


<div class="modal fade" id="formCartao" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="formCartaoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <form class="form-container form-ajax p-2 pb-0" action="<%= PagamentoURI.SELECIONAR_URI %>" method="post">
                <div class="modal-header" style="border: none">
                    <h5 class="modal-title" id="formCartaoLabel" style="font-weight: bold">Novo Cartão</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body pt-0">
                    <div class="msg_response"></div>
                    <h6 class="text-muted">Bandeiras Aceitas:</h6>
                    <div class="d-flex mb-3">
                        <img src="../img/visa-b.png" style="max-height: 30px;">
                        <img class="ml-2" src="../img/mastercard-b.png" style="max-height: 30px;">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="nomeIdentificacao" style="font-size: 13px; margin-bottom: 2px">Nome de Identificação<span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtNomeIdentificacao" id="nomeIdentificacao" placeholder="Ex: Cartão Pai">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="numeroCartao" style="font-size: 13px; margin-bottom: 2px">Número do Cartão<span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtNumero" id="numeroCartao" placeholder="Ex: 9999 9999 9999 9999">
                        </div>
                        <div class="form-group col-md-6 d-flex align-items-end justify-content-left">
                            <img id="bandeiraImagem" src="" style="max-height: 30px; margin-bottom: 3.2px">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="nomeCartao" style="font-size: 13px; margin-bottom: 2px">Nome no Cartão<span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtTitular" id="nomeCartao" placeholder="Ex: Kevin S Siqueira">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="dataValidade" style="font-size: 13px; margin-bottom: 2px">Data de Validade<span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtValidade" id="dataValidade" placeholder="Ex: 12/31">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="ccv" style="font-size: 13px; margin-bottom: 2px">CVV<span
                                    class="span-required">*</span></label>
                            <input type="text" class="form-control" required name="txtCVV" id="ccv" placeholder="Ex: 999">
                        </div>
                    </div>
                    <div class="card-body p-0 mb-2">
                            <label for="valorSelecionado" class="mr-2 mb-0" style="font-size: 16px; font-weight: 600;">Quanto vai pagar nesse cartão</label>
                            <div class="input-group d-flex align-items-center mb-1 mt-2" style="width: 200px;">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic">R$</span>
                                </div>
                                <input type="text" id="valorSelecionado" required class="form-control money" name="txtValor" value="10.00">
                            </div>
                            <span class="text-danger" style="font-size: 14px"><b>OBS:</b> O valor minimo para cada cartão é R$ 10.00</span>
                    </div>
                    <div class="form-group pt-1 mb-0">
                        <div class="custom-control">
                            <input class="custom-control-input" name="swtSalvarCartao" type="checkbox" id="flexSwitchCheckReverse">
                            <label class="custom-control-label" for="flexSwitchCheckReverse">Salvar cartão no perfil</label>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="txtBandeira" id="bandeiraID" value="">
                <input type="hidden" name="operacao" id="operacaoCartao" value="SalvarNovo">
                <div class="modal-footer" style="border: none">
                    <button type="button" style="font-size: 16px; font-weight: bold;"
                            class="btn btn-lg btn-warning text-end" id="BtnSalvar">
                        Salvar
                    </button>
                    <button type="submit" class="d-none" id="BtnSubmit"></button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $('.money').maskMoney({
            thousands: '',
            decimal: '.',
            precision: 2
        });

        $('#BtnSalvar').on('click', function () {
            const money = parseFloat($('#valorSelecionado').val());

            if (isNaN(money) || money < 10) {
                $('#valorSelecionado').val("10.00");
                Dialog.alert({
                    type: "warning",
                    message: "O valor minimo é de R$ 10.00"
                })
                return;
            }

            $('#BtnSubmit').click();
        });

        $('#numeroCartao').mask("0000 0000 0000 0000");
        $('#ccv').mask("000");
        $("#dataValidade").mask("00/00");

        $('#numeroCartao').on('blur', function() {
            $('#bandeiraImagem').attr('src', '');
            $('#bandeiraID').val('');
            if ($(this).val().trim() != '') {
                var cardNumber = $(this).val().replace(/\s+/g, ''); // Remove espaços em branco
                var firstDigit = cardNumber.substring(0, 1);

                if (firstDigit == '4') {
                    $('#bandeiraID').val('1');
                    $('#bandeiraImagem').attr('src', '../img/visa.png');
                } else if (firstDigit == '5') {
                    $('#bandeiraID').val('2');
                    $('#bandeiraImagem').attr('src', '../img/mastercard.png');
                } else {
                    Dialog.alert({
                        message: `Bandeiras não permitida!`,
                        type: "error"
                    });
                }
            }
        });

        $('#aplicarCupom').on('click', function(){
            const cupom = $('#cupom').val();

            if (cupom.trim() == '') {
                Dialog.alert({
                    type: 'warning',
                    message: 'Digite o cupom para aplica-lo'
                });
                return;
            }

            const load = $("#loading");
            load.show();

            $.ajax({
                url: '<%= PagamentoURI.SELECIONAR_URI %>',
                type: "POST",
                dataType: "json",
                data: {
                    operacao: 'AdicionarCupom',
                    txtCodigo: cupom.trim()
                },
                success: function (data) {
                    load.hide();
                    if (typeof data.error != "undefined" && data.error === true) {
                        if (typeof data.message != "undefined") {
                            Dialog.alert({
                                message: data.message,
                                type: "error"
                            });
                        } else {
                            Dialog.alert({
                                message: `Não foi possivel aplicar cupom!`,
                                type: "error"
                            });
                        }
                        return;
                    }

                    if (typeof data.redirect !== "undefined") {
                        window.location.href = data.redirect;
                    }
                }
            });
        });

        $('.remover-cupom').on('click', function() {
            const id = ($(this).data()).id;

            const load = $("#loading");
            load.show();

            $.ajax({
                url: '<%= PagamentoURI.SELECIONAR_URI %>',
                type: "POST",
                dataType: "json",
                data: {
                    operacao: 'RemoverCupom',
                    idCupom: id
                },
                success: function (data) {
                    load.hide();
                    if (typeof data.error != "undefined" && data.error === true) {
                        if (typeof data.message != "undefined") {
                            Dialog.alert({
                                message: data.message,
                                type: "error"
                            });
                        } else {
                            Dialog.alert({
                                message: `Não foi possivel remover cupom!`,
                                type: "error"
                            });
                        }
                        return;
                    }

                    if (typeof data.redirect !== "undefined") {
                        window.location.href = data.redirect;
                    }
                }
            });
        });

        $('.salvar-valor').on('click', function(){
            const card = $(this).closest('[data-id]');
            const id = (card.data()).id;

            const money = parseFloat(card.find('#valor' + id).val());

            if (isNaN(money) || money < 10) {
                card.find('#valor' + id).val("10.00");
                Dialog.alert({
                    type: "warning",
                    message: "O valor minimo é de R$ 10.00"
                })
                return;
            }

            const load = $("#loading");
            load.show();

            $.ajax({
                url: '<%= PagamentoURI.SELECIONAR_URI %>',
                type: "POST",
                dataType: "json",
                data: {
                    operacao: 'SelecionarCartao',
                    txtIdCartao: id,
                    txtValor: card.find('#valor' + id).val()
                },
                success: function (data) {
                    load.hide();
                    if (typeof data.error != "undefined" && data.error === true) {
                        if (typeof data.message != "undefined") {
                            Dialog.alert({
                                message: data.message,
                                type: "error"
                            });
                        } else {
                            Dialog.alert({
                                message: `Não foi possivel selecionar este cartão!`,
                                type: "error"
                            });
                        }
                        return;
                    }

                    if (typeof data.redirect !== "undefined") {
                        window.location.href = data.redirect;
                    }
                }
            });

        });

        $('.alterar-valor').on('click', function(){
            const card = $(this).closest('[data-id]');
            const id = (card.data()).id;

            const money = parseFloat(card.find('#valorSelecionado' + id).val());

            if (isNaN(money) || money < 10) {
                card.find('#valorSelecionado' + id).val("10.00");
                Dialog.alert({
                    type: "warning",
                    message: "O valor minimo é de R$ 10.00"
                })
                return;
            }

            const load = $("#loading");
            load.show();

            $.ajax({
                url: '<%= PagamentoURI.SELECIONAR_URI %>',
                type: "POST",
                dataType: "json",
                data: {
                    operacao: 'AlterarCartao',
                    idCartao: id,
                    txtValor: card.find('#valorSelecionado' + id).val()
                },
                success: function (data) {
                    load.hide();
                    if (typeof data.error != "undefined" && data.error === true) {
                        if (typeof data.message != "undefined") {
                            Dialog.alert({
                                message: data.message,
                                type: "error"
                            });
                        } else {
                            Dialog.alert({
                                message: `Não foi possivel alterar o valor deste cartão!`,
                                type: "error"
                            });
                        }
                        return;
                    }

                    if (typeof data.redirect !== "undefined") {
                        window.location.href = data.redirect;
                    }
                }
            });

        });

        $('.excluir-valor').on('click', function(){
            const card = $(this).closest('[data-id]');
            const id = (card.data()).id;

            const load = $("#loading");
            load.show();

            $.ajax({
                url: '<%= PagamentoURI.SELECIONAR_URI %>',
                type: "POST",
                dataType: "json",
                data: {
                    operacao: 'ExcluirCartao',
                    idCartao: id
                },
                success: function (data) {
                    load.hide();
                    if (typeof data.error != "undefined" && data.error === true) {
                        if (typeof data.message != "undefined") {
                            Dialog.alert({
                                message: data.message,
                                type: "error"
                            });
                        } else {
                            Dialog.alert({
                                message: `Não foi possivel excluir o valor deste cartão!`,
                                type: "error"
                            });
                        }
                        return;
                    }

                    if (typeof data.redirect !== "undefined") {
                        window.location.href = data.redirect;
                    }
                }
            });

        });

        $('.cartao-credito').on('change', function() {
            const id = $(this).val();

            const checked = $(this).is(":checked");

            const campoVl = '#cardValor' + id;

            const outros = $('.card-cartao-cadastrado:not([data-id="'+id+'"])');

            outros.find('.money').val("10.00");
            outros.find('[type="checkbox"]').prop('checked', false);
            outros.find('.content-valor-cartao').hide();

            $(campoVl).find('.money').val("10.00");
            if (checked) {
                $(campoVl).show();
            } else {
                $(campoVl).hide();
            }
        });

        $('.card-cartao-cadastrado').each(function() {
            const id = ($(this).data()).id;
            if ($('.card-cartao-selecionado[data-cartao="'+id+'"]').length > 0) {
               $(this).find('[type="checkbox"]').prop('checked', true);
               $(this).find('[type="checkbox"]').prop('disabled', true);
               $(this).css('opacity', "0.7");
            }
        });
    });
</script>