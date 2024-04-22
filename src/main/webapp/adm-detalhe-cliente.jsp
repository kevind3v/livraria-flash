<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Venda.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="database.dominio.Venda.Cupom" %>
<%@ page import="support.URI.PedidoURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:include page="/components/header-admin.jsp"/>

<% Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");%>

<style>
    select, input {
        font-size: 14px !important;
        font-weight: 400;
    }
</style>

<main class="container" style="margin-top: 40px">
    <h3 class="text-left py-2">Perfil do(a) <%= cliente.getNome() %>
    </h3>

    <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="dadosPessoais-tab" data-toggle="tab" href="#dadosPessoais" role="tab"
               aria-controls="dadosPessoais" aria-selected="true">Dados Cliente</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="pedidos-tab" data-toggle="tab" href="#pedidos" role="tab" aria-controls="pedidos"
               aria-selected="false">Pedidos</a>
        </li>
    </ul>
    <div class="tab-content" id="myTabContent">
        <div class="tab-pane fade show active" id="dadosPessoais" role="tabpanel" aria-labelledby="dadosPessoais-tab">
            <div class="card mt-3">
                <div class="card-body pt-0">
                    <h5 class="my-3">Informações Pessoais</h5>
                    <div class="col-12">
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtNome" style="font-size: 13px; margin-bottom: 2px">Nome Completo<span
                                            class="span-required">*</span></label>
                                    <input type="text" readonly class="form-control" required name="txtNome"
                                           value="<%=cliente.getNome()%>" id="txtNome">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtEmail" style="font-size: 13px; margin-bottom: 2px">E-mail</label>
                                    <input type="email" class="form-control" readonly
                                           value="<%=cliente.getUsuario().getEmail() %>" required name="txtEmail"
                                           id="txtEmail">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtCPF" style="font-size: 13px; margin-bottom: 2px">CPF<span
                                            class="span-required">*</span></label>
                                    <input type="text" class="form-control" required name="txtCPF" readonly
                                           value="<%=cliente.getCpf()%>" id="txtCPF" placeholder="Ex: 123.456.789-10">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="txtDtNasc" style="font-size: 13px; margin-bottom: 2px">Data de
                                        Nascimento<span class="span-required">*</span></label>
                                    <input type="text" class="form-control" required name="txtDtNasc" readonly
                                           id="txtDtNasc"
                                           value="<%= Mascara.converterData(cliente.getDtNascimento(), "yyyy-MM-dd", "dd/MM/yyyy") %>"
                                           placeholder="Ex: 24/03/2024">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="cbbGenero" style="font-size: 13px; margin-bottom: 2px">Genero<span
                                            class="span-required">*</span></label>
                                    <select id="cbbGenero" readonly class="form-control" required name="cbbGenero">
                                        <option value="1" <%if(cliente.getGenero().getValor() == 1){%>selected<%}%>>
                                            Masculino
                                        </option>
                                        <option value="2" <%if(cliente.getGenero().getValor() == 2){%>selected<%}%>>
                                            Feminino
                                        </option>
                                        <option value="3" <%if(cliente.getGenero().getValor() == 3){%>selected<%}%>>
                                            Prefiro não dizer
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <h6 class="mb-3 mt-1">Contato</h6>

                        <div class="row">
                            <div class="col-4">
                                <div class="form-group">
                                    <label for="cbbTpTelefone" style="font-size: 13px; margin-bottom: 2px">Tipo Telefone<span
                                            class="span-required">*</span></label>
                                    <select id="cbbTpTelefone" readonly class="form-control" required
                                            name="cbbTpTelefone">
                                        <option value="1"
                                                <%if(cliente.getTelefone().getTpTelefone().getValor() == 1){%>selected<%}%>>
                                            Fixo
                                        </option>
                                        <option value="2"
                                                <%if(cliente.getTelefone().getTpTelefone().getValor() == 2){%>selected<%}%>>
                                            Celular
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="form-group">
                                    <label for="txtDDD" style="font-size: 13px; margin-bottom: 2px">DDD<span
                                            class="span-required">*</span></label>
                                    <input type="text" readonly
                                           value=<%=cliente.getTelefone().getDdd() %>   class="form-control" required
                                           name="txtDDD" id="txtDDD" placeholder="(11)">
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <label for="txtTelefone" style="font-size: 13px; margin-bottom: 2px">Numero<span
                                            class="span-required">*</span></label>
                                    <input type="text" readonly
                                           value=<%=cliente.getTelefone().getNumero()%> class="form-control" required
                                           name="txtTelefone" id="txtTelefone" placeholder="Ex: 91234-5678">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <div class="card mt-3">
                    <div class="card-body pt-0">
                        <h5 class="my-3">Endereços Cadastrados</h5>
                            <% if(!cliente.getEnderecos().isEmpty()) {%>
                        <div class="row mx-2 my-4">
                            <%for (Endereco endereco : cliente.getEnderecos()) {%>
                            <div class="col-6 mb-3">
                                <div class="card card-endereco" data-cardId="<%=endereco.getId()%>" style="width: 100%; height: 150px">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                            <h6 class="m-0"
                                                style="font-weight: 800; font-size: 18px"><%=endereco.getIdentificacao()%></h6>
                                        </div>
                                        <p class="text-muted m-0">CEP: <%=Mascara.cep(endereco.getCep())%>
                                        </p>
                                        <p class="text-muted mb-0"><%=endereco.getLogradouro()%>, <%=endereco.getNumero()%>
                                        </p>
                                        <p class="text-muted m-0"><%=endereco.getBairro()%> - <%=endereco.getCidade()%>
                                            / <%=endereco.getEstado()%>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <%}%>
                        </div>
                            <%} else { %>
                            <div class="text-center mb-5 mt-5">
                                <span class="text-muted">Nenhum endereço cadastrado</span>
                            </div>
                            <% } %>
                    </div>
                </div>
        </div>
        <div class="tab-pane fade" id="pedidos" role="tabpanel" aria-labelledby="pedidos-tab">
            <table class="table table-bordered mt-4">
                <thead>
                <tr>
                    <th scope="col">N° Pedido</th>
                    <th scope="col">Status</th>
                    <th scope="col">Data Pedido</th>
                    <th scope="col">Valor da Compra</th>
                    <th scope="col" style="width: 80px;"></th> <!-- Cabeçalho para o botão de remoção -->
                </tr>
                </thead>
                <tbody>
                <% if(pedidos != null && !pedidos.isEmpty()){
                    BigDecimal desconto = null;
                    for(Pedido pedido: pedidos){%>
                <tr>
                    <td><%=Mascara.doisDigitoAno(pedido.getDtCadastro().toString())%>-<%= Mascara.formatarIdPedido(pedido.getId()) %></td>
                    <td><%= pedido.getStatus().getDescricao() %></td>
                    <td>
                        <%= Mascara.dataExtensa(pedido.getDtCadastro().toString()) %>
                    </td>
                    <%
                        desconto = new BigDecimal("0.00");
                        if(pedido.getCupons() != null) {
                            for(Cupom cupom : pedido.getCupons()) {
                                desconto = desconto.add(cupom.getValor());
                            }
                        }
                    %>
                    <td>R$ <%=pedido.getValorTotal().add(pedido.getEndereco().getFrete().getValor()).subtract(desconto)%></td>
                    <td>
                        <div class="d-flex justify-content-center align-items-center">
                            <a href="<%= PedidoURI.DETALHE_ADMIN_URI %>?p=<%= pedido.getId() %>" title="Detalhe do Pedido">
                                <i class="fas fa-eye text-secondary" style="font-size: 20px;"></i>
                            </a>
                        </div>
                    </td>
                </tr>
                <%}
                } else {%>
                <tr class="text-center">
                    <td colspan="5" class="text-muted">Nenhum pedido</td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
</main>

<script type="text/javascript">
    $(document).ready(function () {
        $("#txtCPF").mask("000.000.000-00");
        $("#txtDDD").mask("00");
        $("#txtTelefone").mask("90000-0000", {reverse: true});
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