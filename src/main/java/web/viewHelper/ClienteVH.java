package web.viewHelper;

import database.dominio.EntidadeDominio;
import database.dominio.Usuario.*;
import database.dominio.Venda.Cupom;
import database.dominio.Venda.TipoCupom;
import support.Mascara;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteVH implements IViewHelper  {
    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Cliente cliente = null;

        String nmOperacao = request.getParameter("operacao");

        String idCliente = request.getParameter("c");

        if(nmOperacao != null) {
            if(nmOperacao.equals("Salvar")) {
                UsuarioVH usrVH = new UsuarioVH();
                Usuario usuario = (Usuario) usrVH.getEntidade(request);

                List<Endereco> enderecos = new ArrayList<>();

                EnderecoVH endVH = new EnderecoVH();
                Endereco endereco = (Endereco) endVH.getEntidade(request);

                enderecos.add(endereco);

                int nmTpTelefone = Integer.valueOf(request.getParameter("cbbTpTelefone"));
                String nmDdd = Mascara.removerMascara(request.getParameter("txtDDD"));
                String nmNumTelefone = Mascara.removerMascara(request.getParameter("txtTelefone"));
                TipoTelefone tpTelefone = null;
                if(nmTpTelefone == 1) {
                    tpTelefone = TipoTelefone.FIXO;
                }else if(nmTpTelefone == 2) {
                    tpTelefone = TipoTelefone.CELULAR;
                }

                Telefone telefone = new Telefone(tpTelefone, nmDdd, nmNumTelefone);

                String nmNome = request.getParameter("txtNome");
                String nmDtNascimento = request.getParameter("txtDtNasc");
                String nmCpf = Mascara.removerMascara(request.getParameter("txtCPF"));

                int nmGenero = Integer.parseInt(request.getParameter("cbbGenero"));
                Genero genero = null;
                if(nmGenero == 1) {
                    genero = Genero.MASCULINO;
                }else if(nmGenero == 2) {
                    genero = Genero.FEMININO;
                }else if(nmGenero == 3) {
                    genero = Genero.NAOBINARIO;
                }

                cliente = new Cliente(nmNome, nmDtNascimento, nmCpf, genero);

                cliente.setTelefone(telefone);
                cliente.setEnderecos(enderecos);
                cliente.setUsuario(usuario);

                Cupom cupom = new Cupom("FLASH10",
                        TipoCupom.PROMOCIONAL,
                        new BigDecimal("10.00"),
                        LocalDate.now().plusMonths(1)
                );

                List<Cupom> cupons = new ArrayList<>();
                cupom.setCliente(cliente);
                cupons.add(cupom);

                cliente.setCupons(cupons);

                return cliente;
            } else if(nmOperacao.equals("ConsultarPorId")) {
                cliente = new Cliente();

                Usuario usr = (Usuario) request.getSession().getAttribute("usuario");

                if (usr == null) {
                    return null;
                }

                if(!usr.isAdmin()) {
                    cliente.setId(usr.getCliente().getId());
                    cliente.setUsuario(usr);
                    return cliente;
                }
            } else if(nmOperacao.equals("Alterar")){

                cliente = (Cliente) request.getSession().getAttribute("cliente");

                Usuario usr = (Usuario) request.getSession().getAttribute("usuario");

                if(!usr.isAdmin()) {

                    int nmTpTelefone = Integer.valueOf(request.getParameter("cbbTpTelefone"));
                    String nmDdd = Mascara.removerMascara(request.getParameter("txtDDD"));
                    String nmNumTelefone = Mascara.removerMascara(request.getParameter("txtTelefone"));
                    TipoTelefone tpTelefone = null;
                    if(nmTpTelefone == 1) {
                        tpTelefone = TipoTelefone.FIXO;
                    }else if(nmTpTelefone == 2) {
                        tpTelefone = TipoTelefone.CELULAR;
                    }

                    Telefone telefone = new Telefone(tpTelefone, nmDdd, nmNumTelefone);

                    String nmNome = request.getParameter("txtNome");
                    String nmDtNascimento = request.getParameter("txtDtNasc");
                    String nmCpf = Mascara.removerMascara(request.getParameter("txtCPF"));

                    int nmGenero = Integer.parseInt(request.getParameter("cbbGenero"));
                    Genero genero = null;
                    if(nmGenero == 1) {
                        genero = Genero.MASCULINO;
                    }else if(nmGenero == 2) {
                        genero = Genero.FEMININO;
                    }else if(nmGenero == 3) {
                        genero = Genero.NAOBINARIO;
                    }

                    cliente.setNome(nmNome);
                    cliente.setCpf(nmCpf);
                    cliente.setDtNascimento(nmDtNascimento);
                    cliente.setGenero(genero);
                    cliente.setTelefone(telefone);
                    cliente.setUsuario(usr);

                }
                return cliente;
            }
        } else if(idCliente != null) {
            cliente = new Cliente();

//            Usuario usr = (Usuario) request.getSession().getAttribute("usuario");
//
//            if(!usr.isAdmin()) {
//                cliente.setId(usr.getCliente().getId());
//                cliente.setUsuario(usr);
//
//                return cliente;
//            }

            cliente.setId(Integer.valueOf(idCliente));
        } else {
            cliente = new Cliente();
        }

        return cliente;
    }

    @Override
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado) {
        request.getSession().setAttribute("cliente", (Cliente) resultado);
    }
}
