package ro.fasttrackit.finalproject.web;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.service.InvalidName;
import ro.fasttrackit.finalproject.service.TreatmentService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/medication")
public class MedicationServlet extends HttpServlet {

    private TreatmentService tratamentService = new TreatmentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String quantity = req.getParameter("quantity");
        String price =req.getParameter("price");

        try {
            boolean wasAdded = tratamentService.addTreatment(name,quantity,price);
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<h2> Succes! </h2>");

            out.println("Medicamentul a fost adaugat."+ "</b><br/>");
            out.println("<a href='/FinalProjectLab16_war_exploded'>Go Back</a>");
            out.close();

        } catch (NumberFormatException e) {
           endRequestWithError(resp, "Invalid numbers");
        } catch (InvalidName e) {
            endRequestWithError(resp, "Number are not accepted as names");
        }
    }
    private void endRequestWithError(HttpServletResponse resp, String message) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.write(message);
        resp.setStatus(422);
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<head>");
        out.println("<title>Tratament </title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table style='border:1px solid black;'>");
        out.println("<tr>");
        out.println("<th>");
        out.println("Name");
        out.println("</th>");
        out.println("<th>");
        out.println("Quantity");
        out.println("</th>");
        out.println("<th>");
        out.println("Price");
        out.println("</th>");
        out.println("</tr>");

        for (MedicamentDTO value : tratamentService.listMedications()) {
                out.println("<tr>");
                out.println("<td>");
                out.println(value.name());
                out.println("</td>");
                out.println("<td>");
                out.println(value.quantity());
                out.println("</td>");
                out.println("<td>");
                out.println(value.price());
                out.println("</td>");
                out.println("</tr>");
            }

        out.println("</table>");
        out.println("</body>");
        out.close();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
