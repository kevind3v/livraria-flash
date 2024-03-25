package web.viewHelper;

import database.dominio.EntidadeDominio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IViewHelper {
    public EntidadeDominio getEntidade(HttpServletRequest request);
    public void setEntidade(HttpServletResponse response, HttpServletRequest request, Object resultado);
}
