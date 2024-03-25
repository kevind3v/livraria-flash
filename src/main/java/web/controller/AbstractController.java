package web.controller;

import support.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractController extends HttpServlet {

    protected View view;

    public AbstractController() {
        this.view = new View("/_theme");
    }

    protected void forwardToJSP(HttpServletRequest request, HttpServletResponse response, String jspPath) {
        RequestDispatcher rd = request.getRequestDispatcher(jspPath);

        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
