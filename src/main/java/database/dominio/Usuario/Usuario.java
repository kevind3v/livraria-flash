package database.dominio.Usuario;

import database.dominio.EntidadeDominio;

public class Usuario extends EntidadeDominio {
    private boolean isAdmin;
    private String email;
    private String senha;

    private Cliente cliente;

    private String novaSenha;
    private String confirmarSenha;

    public Usuario() {}

    public Usuario(String email, String senha) {
        super();
        this.email = email;
        this.senha = senha;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String senha) {
        this.confirmarSenha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
