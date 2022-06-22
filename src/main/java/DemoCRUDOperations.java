import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemoCRUDOperations {static final String URL = "jdbc:postgresql://localhost:5432/temaproiect";
    static final String USERNAME = "finalprojectapp";
    static final String PASSWORD = "abc123";


    public static void main(String[] args) {
        try {
            demoCreate(new Medication("lala",20, 30));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void demoCreate(Medication medication) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);


        PreparedStatement pStMedication = conn.prepareStatement("INSERT INTO MEDICATION (NAME, QUANTITY,PRICE) VALUES (?,?,?)");
        pStMedication.setString(1, medication.getName());
        pStMedication.setInt(2, medication.getQuantity());
        pStMedication.setInt(3, medication.getPrice());


        int rowsInserted = pStMedication.executeUpdate();

        pStMedication.close();
        conn.close();
    }

    public static List<Medication> demoRead() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement st = conn.createStatement();
        ResultSet rsMedication = st.executeQuery("SELECT name, quantity,price FROM medication");
        List<Medication> medications = new ArrayList<>();
        while (rsMedication.next()) {
            Medication medication = new Medication(rsMedication.getString("name"), rsMedication.getInt("quantity"), rsMedication.getInt("price"));
            medications.add(medication);

            System.out.print(rsMedication.getString("name"));
            System.out.print("---");
            System.out.println(rsMedication.getInt("quantity"));
            System.out.print("---");
            System.out.println(rsMedication.getInt("price"));

        }

        rsMedication.close();
        st.close();
        conn.close();
        return medications;

    }

    private static void demoUpdate() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pStMedication = conn.prepareStatement("UPDATE MEDICATION SET NAME=?, QUANTITY=?, PRICE=? WHERE ID=?");


        int rowsUpdatedM = pStMedication.executeUpdate();
        System.out.println("updated rows: " + rowsUpdatedM);

        pStMedication.close();
        conn.close();
    }


    private static void demoDelete() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement pStMedication = conn.prepareStatement("DELETE FROM MEDICATION WHERE ID=?");
        pStMedication.setLong(1, 1);

        int rowsDeletedM = pStMedication.executeUpdate();
        System.out.println(rowsDeletedM + " rows were deleted.");

        pStMedication.close();
        conn.close();
    }


}
