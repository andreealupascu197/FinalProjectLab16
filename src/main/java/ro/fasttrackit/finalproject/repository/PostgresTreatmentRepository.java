package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostgresTreatmentRepository implements TreatmentRepository {
    static final String URL = "jdbc:postgresql://localhost:5432/finalproject";

    static final String USERNAME = "finalprojectapp";
    static final String PASSWORD = "abc123";

    public PostgresTreatmentRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public List<Medication> findAll() {

        try (// 3. obtain a connection
             Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement st = conn.createStatement();


             ResultSet rs = st.executeQuery("SELECT * FROM medication");


             // 6. iterate the result set and print the values
        ) {
            List<Medication> medicaments = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getInt("price");
                Date expiryDate = rs.getDate("expiryDate");
                String usage = rs.getString("usage").strip();
                String type = rs.getString("type").strip();
                Statement stA = conn.createStatement();
//                ResultSet rsA = stA.executeQuery("SELECT * FROM administration WHERE idUser = " + id);
                ResultSet rsA = stA.executeQuery("SELECT * FROM administration WHERE " + id + " = idUser");
                while (rsA.next()) {
                    long idUser = rsA.getLong("idUser");
                    int frequency = rsA.getInt("frequency");
                    String timeOfDay = rsA.getString("timeOfDay").strip();
                    Boolean beforeEating = rsA.getBoolean("beforeEating");

                    //  rsA.close();
                    medicaments.add(new Medication(id, name, quantity, price, Date.valueOf(String.valueOf(expiryDate)), Usage.valueOf(usage.toUpperCase()), Type.valueOf(type.toUpperCase()),
                            new AdministrationMethod(idUser, frequency, TimeOfDay.valueOf(timeOfDay.toUpperCase()), beforeEating)));

                }
            }
            //rs.close();
            return medicaments;
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public void save(MedicamentDTO medicament) {

        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement pStMedication = conn.prepareStatement(
                        "INSERT INTO medication (name, quantity, price,expiryDate, usage, type ) VALUES (?,?,?,?,?,?)"
                )

        ) {
//            Class.forName("org.postgresql.Driver");
//            // 2. define connection params to db
            java.util.Calendar cal = Calendar.getInstance();
            cal.setTime(medicament.expiryDate());
            java.sql.Date date = new Date(cal.getTime().getTime());


            pStMedication.setString(1, medicament.name());
            pStMedication.setInt(2, medicament.quantity());
            pStMedication.setDouble(3, medicament.price());
            pStMedication.setDate(4, date);
            pStMedication.setString(5, String.valueOf(medicament.usage()));
            pStMedication.setString(6, String.valueOf(medicament.type()));

            PreparedStatement pstAdministrationMethod = conn.prepareStatement("INSERT INTO administration(frequency,timeOfDay,beforeEating) VALUES (?,?,?)");
            pstAdministrationMethod.setInt(1, Integer.valueOf(medicament.administrationMethodDTO().frequency()));
            pstAdministrationMethod.setString(2, String.valueOf(medicament.administrationMethodDTO().timeOfDay()));
            pstAdministrationMethod.setBoolean(3, medicament.administrationMethodDTO().beforeEating());


            // 5. execute a prepared statement
            int rowsInserted = pStMedication.executeUpdate();
            int rowsInsertedA = pstAdministrationMethod.executeUpdate();
            System.out.println("Inserted " + rowsInserted + rowsInsertedA);

            // 6. close the objects will happen due to the try-with-resources + AutoClosable interface
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }


    }

//    private static void demoDelete() throws ClassNotFoundException, SQLException {
//        Class.forName("org.postgresql.Driver");
//        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//
//        PreparedStatement pStMedication = conn.prepareStatement("DELETE FROM MEDICATION WHERE ID=?");
//        pStMedication.setLong(1, 1); // once the record with iduser 1 is deleted from Medication, the record with id 1 from Administration is deleted as well
//
//
//        int rowsDeletedM = pStMedication.executeUpdate();
//        System.out.println(rowsDeletedM + " rows were deleted.");
//
//        pStMedication.close();
//        conn.close();
//    }

}
