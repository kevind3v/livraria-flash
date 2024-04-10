<%@ page import="support.URI.UsuarioURI" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="d-flex flex-column justify-content-center align-items-center h-100">
    <main class="container d-flex flex-column align-items-center">

        <div class="card" style="width: 400px">
            <form class="form-container form-ajax d-flex flex-column  align-items-center justify-content-center" id="formLogin" action="<%= UsuarioURI.AUTENTICAR_URI %>" method="post">
                <a class="navbar-brand brand py-3" href="<%= UsuarioURI.LOGIN_URI %>" style="font-size: 30px;">
                    <i class="fas fa-bolt" style="color: #ffd43b"></i> <span style="font-weight: bold">Flash</span>
                </a>
                <div class="msg_response" style="width: 350px;"></div>
                <div class="form-group" style="width: 350px">
                    <label for="exampleInputEmail1" style="font-size: 13px; margin-bottom: 2px">E-mail</label>
                    <input type="email" class="form-control" id="exampleInputEmail1" name="txtEmail" required>
                </div>
                <div class="form-group" style="width: 350px">
                    <label for="exampleInputPassword1" style="font-size: 13px; margin-bottom: 2px">Senha</label>
                    <input type="password" class="form-control" required name="txtSenha" id="exampleInputPassword1">
                </div>
                <input name="operacao" value="Consultar" type="hidden"/>
                <button type="submit" id="BtnLogar" style="width: 350px; font-size: 14px; font-weight: bold;" class="btn btn-lg btn-warning mb-4">
                    Entrar
                </button>
                <p class="text-center text-muted copyright" style="font-size: 14px">Ainda não possui conta? <a class="nav-link text-warning" id="linkCadastro" style="font-weight: bold" href="<%= UsuarioURI.CADASTRO_URI %>">Faça o cadastro!</a></p>
            </form>
        </div>

        <div class="mt-4">
            <p class="text-center text-muted copyright">&copy 2024 Flash Inc.</p>
        </div>
    </main>
</div>

