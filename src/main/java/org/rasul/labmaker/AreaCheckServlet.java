package org.rasul.labmaker;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.rasul.labmaker.table.ResultBean;
import org.rasul.labmaker.table.TableRow;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@WebServlet(name="AreaCheckServlet", urlPatterns="/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Inject
    private ResultBean resultBean;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getDispatcherType().name().equals("FORWARD")) {
            resp.sendError(403, "You don't have access rights to this resource");
            return;
        }
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkPoint(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkPoint(request, response);
    }

    private void checkPoint(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        long currentTime = System.currentTimeMillis();

        try {
            float x = Float.parseFloat(request.getParameter("x"));
            float y = Float.parseFloat(request.getParameter("y"));
            float r = Float.parseFloat(request.getParameter("r"));
            long offset = -Long.parseLong(request.getParameter("offset"));

            Instant clientTime = Instant.now().truncatedTo(ChronoUnit.MILLIS).plus(offset, ChronoUnit.MINUTES);
            boolean result = checkArea(x,y,r);

            double scriptWorkingTime = System.currentTimeMillis() - currentTime;

            TableRow newRow = new TableRow(x, y, r, result, clientTime.toString(), scriptWorkingTime);
            resultBean.addRow(newRow);

            PrintWriter out = response.getWriter();
            out.print(new JSONObject(newRow));
            out.close();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.sendError(400, "Transmitted values are not numeric");
        } catch (IOException e) {
            response.sendError(418, "Unidentified error");
        }
    }

    private boolean checkArea(float x, float y, float r){
        if (x<=0 && y>=0){
            return x >= -r && y <= r;
        }
        else if (x>=0 && y>=0){
            return y < (-x / 2 + 0.5 * r);
        }
        else if (x>=0 && y<=0){
            return (x * x + y * y) < r * r;
        }
        return false;
    }
}