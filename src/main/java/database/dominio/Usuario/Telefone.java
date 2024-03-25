package database.dominio.Usuario;

import database.dominio.EntidadeDominio;

public class Telefone extends EntidadeDominio {
    private TipoTelefone tpTelefone;
    private String ddd;
    private String numero;

    public Telefone() {}

    public Telefone(TipoTelefone tpTelefone, String ddd, String numero) {
        super();
        this.tpTelefone = tpTelefone;
        this.ddd = ddd;
        this.numero = numero;
    }
    public TipoTelefone getTpTelefone() {
        return tpTelefone;
    }
    public void setTpTelefone(TipoTelefone tpTelefone) {
        this.tpTelefone = tpTelefone;
    }
    public String getDdd() {
        return ddd;
    }
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }


}