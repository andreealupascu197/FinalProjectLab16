package ro.fasttrackit.finalproject.web;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.service.InvalidName;
import ro.fasttrackit.finalproject.service.MedicamentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/treatment")
public class TreatmentServlet extends HttpServlet {

    private MedicamentService medicamentService = new MedicamentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");
        String expiryDate = req.getParameter("expiryDate");
        String type = req.getParameter("type");
        String usage = req.getParameter("usage");
        String frequency = req.getParameter("frequency");
        String timeOfDay = req.getParameter("timeOfDay");
        String beforeEating = req.getParameter("beforeEating");



        try {
            boolean wasAdded = medicamentService.addMedicament(
                    name,
                    quantity,
                    price,
                    expiryDate,
                    usage,
                    type,
                    frequency,
                    timeOfDay,
                    beforeEating);

            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<h2> Succes! </h2>");

            out.println("Medicamentul a fost adaugat." + "</b><br/>");
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
        out.println("<title>Tratament</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table style='border:1px solid black;'>");
        out.println("<tr>");
        out.println("<th>Name</th>");
        out.println("<th>Quantity</th>");
        out.println("<th>Price</th>");
        out.println("<th>Expiry Date</th>");
        out.println("<th>Usage</th>");
        out.println("<th>Type</th>");
        out.println("<th>Frequency</th>");
        out.println("<th>Time Of Day</th>");
        out.println("<th>Before Eating</th>");
        out.println("</tr>");

        for (MedicamentDTO value : medicamentService.listMedications()) {
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
            out.println("<td>");
            out.println(value.expiryDate());
            out.println("</td>");
            out.println("<td>");
            out.println(value.usage());
            out.println("</td>");
            out.println("<td>");
            out.println(value.type());
            out.println("</td>");
            out.println("<td>");
            out.println(value.administrationMethodDTO().frequency());
            out.println("</td>");
            out.println("<td>");
            out.println(value.administrationMethodDTO().timeOfDay());
            out.println("</td>");
            out.println("<td>");
            out.println(value.administrationMethodDTO().beforeEating());
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
