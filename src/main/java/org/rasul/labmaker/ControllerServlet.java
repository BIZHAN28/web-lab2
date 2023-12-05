package org.rasul.labmaker;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.rasul.labmaker.table.ResultBean;
import org.rasul.labmaker.table.TableRow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ControllerServlet", urlPatterns="/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    @Inject
    private ResultBean resultBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        log(request.toString());
        PrintWriter out = response.getWriter();
        if (checkGetTableRequest(request)) {
            List<TableRow> rows = resultBean.getRows();
            out.print(new JSONArray(rows));
            out.close();
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(400, "Bad Request (get only for get Table from session)");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkArgumentExists(request)) {
            getServletContext().getRequestDispatcher("/AreaCheckServlet").forward(request, response);
        } else {
            response.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkCleanRequest(request)){
            PrintWriter out = response.getWriter();
            request.getSession().invalidate();
            out.print("Success");
            out.close();
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(400, "Bad Request");
        }

    }


    private boolean checkCleanRequest(HttpServletRequest req) {
        return "true".equals(req.getParameter("clean"));
    }

    private void cleanTable(HttpServletRequest request) {
        resultBean.setRows(new ArrayList<>());
    }

    private boolean checkArgumentExists(HttpServletRequest request) {
        return request.getParameter("x") != null &&
                request.getParameter("y") != null &&
                request.getParameter("r") != null &&
                request.getParameter("offset") != null;
    }

    private boolean checkGetTableRequest(HttpServletRequest request) {
        return "true".equals(request.getParameter("getTable"));
    }
}