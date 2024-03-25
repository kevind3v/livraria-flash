package support;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class View {

    protected String ext;
    protected String theme;


    public View(String theme, String ext) {
        this.ext = ext;
        this.theme = theme + "." + ext;
    }

    public View(String theme) {
        this(theme, "jsp");
    }

    public void forwardToJSP(HttpServletRequest request, HttpServletResponse response, String jspPath) {
        forwardToJSP(request, response, jspPath, null);
    }

    public void forwardToJSP(HttpServletRequest request, HttpServletResponse response, String jspPath, Map<String, Object> parametros) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(this.theme);

        try {
            request.setAttribute("content", jspPath + "." + this.ext);
            if (parametros != null) {
                request.setAttribute("parametros", parametros);
            }

            // Adiciona o título ao objeto request, se existir; caso contrário, usa o título padrão
            String titulo = (parametros != null && parametros.containsKey("titulo")) ? (String) parametros.get("titulo") : "Flash";
            request.setAttribute("titulo", titulo);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
