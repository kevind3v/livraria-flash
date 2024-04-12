<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page import="support.URI.PagamentoURI" %>
<%@ page import="database.dominio.Venda.Bandeira" %>
<%@ page import="database.dominio.Venda.CartaoCredito" %>
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
    .cartao-credito {
        width: 100%;
        max-width: 350px;
        height: 210px;
        border-radius: 10px;
        position: relative;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 20px;
        box-sizing: border-box;
    }

    .visa {
        background: linear-gradient(135deg, #e7f0fd 0%, #d9e7f4 100%);
    }

    .mastercard {
        background: linear-gradient(to bottom right, #fdf2e7, #f8e4ce);
    }

    .credit-chip {
        width: 50px;
        height: 30px;
        background: url('../img/chip.png') no-repeat center;
        background-size: contain;
        position: absolute;
        border-radius: 10px;
        top: 75px;
        left: 20px;
    }

    .credit-number {
        color: #3f4d67;
        font-size: 18px;
        letter-spacing: 2px;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .credit-name {
        color: #3f4d67;
        font-size: 16px;
        font-weight: bold;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .credit-title {
        font-size: 30px;
        text-align: end;
        font-weight: 800;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .credit-title i {
        color: #ffd43b;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
    }


    .credit-flag {
        width: 60px;
        position: absolute;
        bottom: 20px;
        right: 20px;
    }

    .credit-flag img {
        max-width: 100%;
        height: auto;
    }
</style>

<main class="container" style="margin-top: 40px">
    <h3 class="text-left py-2">Sua Conta</h3>

    <hr>

    <jsp:include page="/components/nav-perfil.jsp"/>

    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
            <div class="row mx-2 my-4">
                <%for (CartaoCredito card : cliente.getCartoes()) {%>
                <div class="col-12 col-lg-6 mb-3 px-5">
                    <div class="card card-cartao" data-cardId="<%=card.getId()%>" style="width: 100%; height: 350px">
                        <div class="card-body d-flex flex-column align-items-center">
                        <%if (card.getBandeira() == Bandeira.VISA) {%>
                            <div class="cartao-credito visa">
                                <div class="credit-chip"></div>
                                <div class="credit-title d-flex justify-content-between">
                                    <span style="font-size: 16px; font-weight: 500;">Validade <%= card.getDataValidade() %></span>
                                    <i class="fas fa-bolt"></i>
                                </div>
                                <div class="d-flex flex-column justify-content-end">
                                    <div class="credit-name"><%= card.getTitular() %></div>
                                    <div class="credit-number"><sub style="font-size: 20px">**** **** ****</sub> <span><%= Mascara.extrairUltimosQuatroDigitos(card.getNumero()) %></span></div>
                                </div>
                                <div class="credit-flag"><img src="../img/visa-b.png" alt="Bandeira Visa"></div>
                            </div>
                        <%} else { %>
                            <div class="cartao-credito mastercard">
                                <div class="credit-chip"></div>
                                <div class="credit-title d-flex justify-content-between">
                                    <span style="font-size: 16px; font-weight: 500;">Validade <%= card.getDataValidade() %></span>
                                    <i class="fas fa-bolt"></i>
                                </div>
                                <div class="d-flex flex-column justify-content-end">
                                    <div class="credit-name"><%= card.getTitular() %></div>
                                    <div class="credit-number"><sub style="font-size: 20px">**** **** ****</sub> <span><%= Mascara.extrairUltimosQuatroDigitos(card.getNumero()) %></span></div>
                                </div>
                                <div class="credit-flag"><img src="../img/mastercard-b.png" alt="Bandeira Visa"></div>
                            </div>
                        <%} %>
                            <h5 class="mt-4" style="font-weight: bold"><%= card.getNomeIdentificacao() %></h5>
                            <button class="btn py-0 px-1" style="font-weight: bold; color: #dc3545" onclick="setExcluirCartao(<%=card.getId()%>)"><i
                                    class="fa-regular fa-trash-can"></i> Excluir
                            </button>
                        </div>
                    </div>
                </div>
                <%}%>

                <div class="col-12 col-lg-6 px-5">
                    <a href="#" style=" text-decoration: none;" data-toggle="modal" data-target="#formCartao">
                        <div class="card" style="width: 100%; height: 350px">
                            <div class="card-body d-flex align-items-center justify-content-center">
                                <h5 style="font-weight: bold; color: #ffd43b"><i class="fa-solid fa-plus mr-2"></i> Novo
                                    Cartão</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>

<div class="modal fade" id="formCartao" data-backdrop="static" data-keyboard="false" tabindex="-1"
     aria-labelledby="formCartaoLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <form class="form-container form-ajax p-2 pb-0" action="<%= PagamentoURI.ADICIONAR_CARTAO_URI %>" method="post">
                <div class="modal-header" style="border: none">
                    <h5 class="modal-title" id="formCartaoLabel" style="font-weight: bold">Cadastro Cartão</h5>
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
                </div>
                <input type="hidden" name="txtBandeira" id="bandeiraID" value="">
                <input type="hidden" name="txtCartaoId" id="txtCartaoId">
                <input type="hidden" name="operacao" id="operacaoCartao" value="Salvar">
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
    $(document).ready(function() {
        $('#numeroCartao').mask("0000 0000 0000 0000");
        $('#ccv').mask("000");
        $("#dataValidade").mask("00/00");

        $('#numeroCartao').on('blur', function() {
            $('#bandeiraImagem').attr('src', '');
            $('#bandeiraID').val('');
            if ($(this).val().trim() != '') {
                var cardNumber = $(this).val().replace(/\s+/g, ''); // Remove espaços em branco
                var firstDigit = cardNumber.substring(0, 1);
                var regex = /^(431274|438935|451416|457393|457631|40117[8-9]|45763[1-2]|504175|627780|636297|636368|438935|504175|5067|509[0-9]|6500|6501)\d*$/;

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
    });

    function setExcluirCartao(id) {
        const load = $("#loading");

        Dialog.confirm({
            type: "warning",
            message: "Deseja excluir o cartão?",
            callback: () => {
                load.show();
                $.ajax({
                    url: "<%= PagamentoURI.EXCLUIR_CARTAO_URI %>",
                    type: "POST",
                    dataType: "json",
                    data: {
                        operacao: "Excluir",
                        txtIdCartao: id
                    },
                    success: function(data) {
                        load.hide();
                        if (typeof data.error != "undefined" && data.error === true) {
                            Dialog.alert({
                                message: `Não foi possivel excluir cartão!`,
                                type: "error"
                            });
                            return;
                        }

                        if (typeof data.alert !== "undefined") {
                            Dialog.alert(data.alert);
                        }

                        if (typeof data.redirect !== "undefined") {
                            window.location.href = data.redirect;
                        }
                    },
                    error: function() {
                        Dialog.alert({
                            message: `Não foi possivel excluir cartão`,
                            type: "error"
                        });
                    }
                });
            }
        })
    }
</script>