package business;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Endereco;

public class ValidarDadosEndereco extends AbstractValidador{

    @Override
    public String processar(EntidadeDominio entidade) {
        Endereco endereco = null;
        endereco = (Endereco) entidade;

        StringBuilder msg = new StringBuilder();

        if(isNull(endereco.getIdentificacao())) {
            msg.append("Identificação Endereco obrigatorio; \n");
        }

        if(isNull(endereco.getCep())) {
            msg.append("CEP obrigatorio; \n");
        }

        if(isNull(endereco.getLogradouro())) {
            msg.append("Logradouro obrigatorio; \n");
        }

        if(isNull(endereco.getNumero())) {
            msg.append("Numero residencia obrigatorio; \n");
        }

        if(isNull(endereco.getBairro())) {
            msg.append("Bairro obrigatorio; \n");
        }

        if(endereco.getCep().length() != 8) {
            msg.append("CEP invalido; \n");
        }

        if(isNull(endereco.getCidade())) {
            msg.append("Cidade obrigatorio;  \n");
        }

        if(isNull(endereco.getEstado())) {
            msg.append("Estado obrigatorio; \n");
        }

        if(msg.length()>0) {
            return msg.toString();
        }

        return null;
    }

}
