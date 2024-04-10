package database.dominio.Venda;


import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Endereco;

public class EnderecoEntrega extends EntidadeDominio {
    private boolean isSalvar;

    private Endereco endereco;
    private Frete frete;

    public EnderecoEntrega() {}

    public EnderecoEntrega(Endereco endereco, Frete frete) {
        super();
        this.frete = frete;
        this.endereco = endereco;
        this.setSalvar(false);
    }

    public EnderecoEntrega(Endereco endereco) {
        super();
        this.endereco = endereco;
        this.setSalvar(false);
    }

    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isSalvar() {
        return isSalvar;
    }

    public void setSalvar(boolean isSalvar) {
        this.isSalvar = isSalvar;
    }

    public void setFrete(Frete frete) {
        this.frete = frete;
    }

    public Frete getFrete() {
        return frete;
    }

}
