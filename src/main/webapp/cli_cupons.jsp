<%@ page import="database.dominio.Usuario.Cliente" %>
<%@ page import="support.Mascara" %>
<%@ page import="support.URI.ClienteURI" %>
<%@ page import="database.dominio.Usuario.Endereco" %>
<%@ page import="database.dominio.Venda.Cupom" %>
<%@ page import="java.time.LocalDate" %>
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

    .cupom {
        filter: drop-shadow(0 3px 5px rgba(0,0,0,0.3));
        border-radius: 15px;
        padding-left: 20px;
        padding-right: 20px;
    }
    .cupom::before, .cupom::after {
        content: '';
        position: absolute;
        top: 0;
        width: 50%;
        height: 100%;
        z-index: -2;
    }

    .cupom::before {
        left: 0;
        border-radius: 10px 0 0 10px;
        background-image: radial-gradient(circle at 0% 50%, transparent 25px, #fff 26px);
    }

    .cupom::after {
        right: 0;
        border-radius: 0 10px 10px 0;
        background-image: radial-gradient(circle at 100% 50%, transparent 25px, #fff 26px);
    }

    .status-cupom.expirado::before,
    .status-cupom.expirado::after{
        content: '';
        position: absolute;
        top: 0;
        width: 50%;
        height: 100%;
        z-index: 1;
    }

    .status-cupom.expirado::before {
        left: 0;
        border-radius: 10px 0 0 10px;
        background-image: radial-gradient(circle at 0% 50%, transparent 25px, rgba(0,0,0,0.5) 26px);
    }

    .status-cupom.expirado::after {
        right: 0;
        border-radius: 0 10px 10px 0;
        background-image: radial-gradient(circle at 100% 50%, transparent 25px, rgba(0,0,0,0.5) 26px);
    }

    .cupom-indisponivel {
        width: 100%;
        height: 100%;
        color: #fff;
        left: 0;
        position: absolute;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        z-index: 2;
    }

    .cupom.disponivel:hover {
        cursor: pointer;
    }

    .cupom.disponivel:hover::before {
        background-image: radial-gradient(circle at 0% 50%, transparent 25px, #e0e0e0 26px);
    }

    .cupom.disponivel:hover::after {
        background-image: radial-gradient(circle at 100% 50%, transparent 25px, #e0e0e0 26px);
    }

    .btn-cupom {
        background: #fbb72c!important;
        font-weight: bold;
    }
</style>

<main class="container" style="margin-top: 40px">
    <h3 class="text-left py-2">Sua Conta</h3>

    <hr>

    <jsp:include page="/components/nav-perfil.jsp"/>

    <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
            <div class="row mx-2 my-4">
                <%for(Cupom cupom : cliente.getCupons()){%>
                <% boolean expirado = !cupom.getValidade().isAfter(LocalDate.now()); %>
                <div class="col-4 mb-3">
                    <div class="cupom <%= expirado ? "" : "disponivel" %>" <%= !expirado ? "onclick=\"copiarCupom('"+cupom.getCodigo()+"', '"+cupom.getTpCupom()+"')\"" : "" %>>
                        <div class="cupom-indisponivel <%= expirado ? "" : "d-none" %>">
                            <span class="px-5 py-1" style="font-weight: bold; font-size: 20px"><%= cupom.getCodigo() %></span>
                            <span class="px-5 py-1" style="border-radius: 5px; font-weight: 500; background: rgb(251,183,44)">Expirado</span>
                        </div>
                        <div class="status-cupom <%= expirado ? "expirado" : "" %> d-flex justify-content-center align-items-center">
                            <div class="cupom-img d-flex align-items-center  my-3 ml-3" style="border-right: 2px #3f4d67 dashed; width: 70px; height: 70px">
                                <i class="fas fa-bolt" style="color: #ffd43b; font-size: 50px"></i>
                            </div>
                            <div class="cupom-content m-3 d-flex flex-column">
                                <span class="text-muted" style="font-size: 16px; font-weight: 500"><%= cupom.getTpCupom() %></span>
                                <span class="text-muted" style="font-weight: 500">R$ <span style="font-size: 26px; font-weight: 900"><%= cupom.getValor() %></span> Desc.</span>
                                <span class="text-muted" style="opacity: 0.7; font-size: 14px">Expira em <%= Mascara.dataBR(cupom.getValidade().toString()) %></span>
                            </div>
                        </div>
                    </div>
                </div>
                <%}%>
<%--                <div class="col-4 mb-3">--%>
<%--                    <div class="cupom disponivel" onclick="copiarCupom('FLASH39')">--%>
<%--                        <div class="cupom-indisponivel d-none">--%>
<%--                            <span class="px-5 py-1" style="font-weight: bold; font-size: 20px">FLASH10</span>--%>
<%--                            <span class="px-5 py-1" style="border-radius: 5px; font-weight: 500; background: rgb(251,183,44)">Expirado</span>--%>
<%--                        </div>--%>
<%--                        <div class="status-cupom d-flex justify-content-center align-items-center">--%>
<%--                            <div class="cupom-img d-flex align-items-center  my-3 ml-3" style="border-right: 2px #3f4d67 dashed; width: 70px; height: 70px">--%>
<%--                                <i class="fas fa-bolt" style="color: #ffd43b; font-size: 50px"></i>--%>
<%--                            </div>--%>
<%--                            <div class="cupom-content m-3 d-flex flex-column">--%>
<%--                                <span class="text-muted" style="font-size: 16px; font-weight: 500">TROCA</span>--%>
<%--                                <span class="text-muted" style="font-weight: 500">R$ <span style="font-size: 26px; font-weight: 900">143.80</span> Desc.</span>--%>
<%--                                <span class="text-muted" style="opacity: 0.7; font-size: 14px">Expira em 20/04/2024</span>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="col-4 mb-3">--%>
<%--                    <div class="cupom">--%>
<%--                        <div class="cupom-indisponivel">--%>
<%--                            <span class="px-5 py-1" style="font-weight: bold; font-size: 20px">FLASH5</span>--%>
<%--                            <span class="px-5 py-1" style="border-radius: 5px; font-weight: 500; background: rgb(251,183,44)">Usado</span>--%>
<%--                        </div>--%>
<%--                        <div class="status-cupom expirado d-flex justify-content-center align-items-center">--%>
<%--                            <div class="cupom-img d-flex align-items-center  my-3 ml-3" style="border-right: 2px #3f4d67 dashed; width: 70px; height: 70px">--%>
<%--                                <i class="fas fa-bolt" style="color: #ffd43b; font-size: 50px"></i>--%>
<%--                            </div>--%>
<%--                            <div class="cupom-content m-3 d-flex flex-column">--%>
<%--                                <span class="text-muted" style="font-size: 16px; font-weight: 500">TROCA</span>--%>
<%--                                <span class="text-muted" style="font-weight: 500">R$ <span style="font-size: 26px; font-weight: 900">143.80</span> Desc.</span>--%>
<%--                                <span class="text-muted" style="opacity: 0.7; font-size: 14px">Expira em 20/04/2024</span>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript">
    function copiarCupom(cupom, tipo) {
        if (typeof tipo == "undefined")
            tipo = "";
        Swal.fire({
            title: "<span>"+tipo+"</span><br/><span style='font-weight: 800'>Seu Cupom</span>",
            icon: "info",
            html: `
                <div class="d-flex justify-content-center">
                    <span class="form-control" style="width: 200px; font-size: 24px!important; font-weight: 700; height: 50px">`+cupom+`</span>
                </div>
            `,
            showCloseButton: true,
            showCancelButton: false,
            focusConfirm: false,
            customClass: {
                confirmButton: "btn-cupom",
            },
            confirmButtonText: `
                <i class="fa fa-copy"></i> Copiar
            `,
        }).then((result) => {
            if (result.isConfirmed) {
                navigator.clipboard.writeText(cupom)
                    .then(() => {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: "top-end",
                            showConfirmButton: false,
                            timer: 3000,
                            timerProgressBar: true,
                            didOpen: (toast) => {
                                toast.onmouseenter = Swal.stopTimer;
                                toast.onmouseleave = Swal.resumeTimer;
                            }
                        });
                        Toast.fire({
                            icon: "success",
                            title: "Cupom copiado!!"
                        });
                    })
                    .catch((error) => {
                        Swal.fire('Erro!', 'Erro ao copiar cupom', 'error');
                        console.error('Erro ao copiar:', error);
                    });
            }
        });
    }
    $(document).ready(function() {

    });
</script>