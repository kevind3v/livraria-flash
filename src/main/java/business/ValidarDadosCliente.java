package business;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.Cliente;
import database.dominio.Usuario.Endereco;

public class ValidarDadosCliente extends AbstractValidador {
    @Override
    public String processar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente) entidade;

        if(isNull(cliente.getNome())) {
            sb.append("Nome Completo obrigatorio; ");
        }

        if(isNull(cliente.getCpf())) {
            sb.append("CPF obrigatorio; ");
        }else if(cliente.getCpf().length() != 11 ) {
            sb.append("CPF invalido; ");
        }

        if(isNull(cliente.getDtNascimento())) {
            sb.append("Data de Nascimento obrigatorio; ");
        }

        if(isNull(cliente.getGenero())) {
            sb.append("Genero obrigatorio; ");
        }

        ValidarDadosTelefone vTelefone = new ValidarDadosTelefone();
        String msgTelefone = vTelefone.processar(cliente.getTelefone());

        if( msgTelefone != null ){
            sb.append(msgTelefone);
        }

        if(cliente.getId() == 0) {
            ValidarDadosEndereco vEndereco = new ValidarDadosEndereco();

            String msgEndereco = null;

            for(Endereco endereco : cliente.getEnderecos()) {
                msgEndereco = vEndereco.processar(endereco);
                if( msgEndereco != null ){
                    sb.append(msgEndereco);
                }
            }

            ValidarDadosUsuario vUsuario = new ValidarDadosUsuario();
            String msgUsuario = vUsuario.processar(cliente.getUsuario());

            if( msgUsuario != null ){
                sb.append(msgUsuario);
            }
        }

        ValidarUnicidadeCliente vUnicidade = new ValidarUnicidadeCliente();

        String msgUnicidade = null;
        msgUnicidade = vUnicidade.processar(cliente);

        if( msgUnicidade != null ){
            sb.append(msgUnicidade);
        }

        if(sb.length()>0) {
            return sb.toString();
        }

        return null;
    }
}
