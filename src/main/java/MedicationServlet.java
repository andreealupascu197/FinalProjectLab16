import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/medication")
public class MedicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");

        int quantity = 0;
        int price = 0;

        try {
            quantity = Integer.parseInt(req.getParameter("quantity"));
            price = Integer.parseInt(req.getParameter("price"));
        } catch (NumberFormatException e) {
            resp.setStatus(422);
            try (PrintWriter writer = resp.getWriter()) {
                writer.println("Parametrii trebuie sa fie numere");
            }
        }
        try {
            Medication medication = new Medication(name, quantity, price);
            DemoCRUDOperations.demoCreate(medication);
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<h2> Succes! </h2>");

            out.println("Medicament: <b>" + medication.getName() + " a fost adaugat."+ "</b><br/>");
            out.println("<a href='/FinalProjectLab16_war_exploded'>Go Back</a>");

            // finished writing, send to browser
            out.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
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

        try {
            List<Medication> databaseManager = DemoCRUDOperations.demoRead();
            for (int i = 0; i < databaseManager.size(); i++) {

                out.println("<tr>");
                out.println("<td>");
                out.println(databaseManager.get(i).getName());
                out.println("</td>");
                out.println("<td>");
                out.println(databaseManager.get(i).getQuantity());
                out.println("</td>");
                out.println("<td>");
                out.println(databaseManager.get(i).getPrice());
                out.println("</td>");
                out.println("</tr>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
