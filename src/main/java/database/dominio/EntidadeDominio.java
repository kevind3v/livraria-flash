package database.dominio;

import java.time.LocalDate;

public class EntidadeDominio {
    protected int id;
    private LocalDate dtCadastro;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getDtCadastro() {
        return dtCadastro;
    }
    public void setDtCadastro(LocalDate dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

}
