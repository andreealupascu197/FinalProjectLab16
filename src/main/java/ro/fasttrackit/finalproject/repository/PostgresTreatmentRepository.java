package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostgresTreatmentRepository implements TreatmentRepository {
    static final String URL = "jdbc:postgresql://localhost:5432/proiectFinalTest";

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
                ResultSet rsA = stA.executeQuery("SELECT * FROM administration WHERE " + id + " = idUser");
               while (rsA.next()) {
                    long idUser = rsA.getLong("idUser");
                    int frequency = rsA.getInt("frequency");
                    String timeOfDay = rsA.getString("timeOfDay").strip();
                    Boolean beforeEating = rsA.getBoolean("beforeEating");


                    medicaments.add(new Medication(id, name, quantity, price, Date.valueOf(String.valueOf(expiryDate)), Usage.valueOf(usage), Type.valueOf(type.toUpperCase()),
                            new AdministrationMethod(idUser, frequency, TimeOfDay.valueOf(timeOfDay.toUpperCase()), beforeEating)));
                }
            }
            return medicaments;
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public void save(MedicamentDTO medicamentDTO) {

        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement pStMedication = conn.prepareStatement(
                        "INSERT INTO medication (name, quantity, price,expiryDate, usage, type ) VALUES (?,?,?,?,?,?)"
                );

        ) {
//            Class.forName("org.postgresql.Driver");
//            // 2. define connection params to db
            java.util.Calendar cal = Calendar.getInstance();
            cal.setTime(medicamentDTO.expiryDate());
            java.sql.Date date = new Date(cal.getTime().getTime());

            pStMedication.setString(1, medicamentDTO.name());
            pStMedication.setInt(2, medicamentDTO.quantity());
            pStMedication.setDouble(3, medicamentDTO.price());
            pStMedication.setDate(4, date);
            pStMedication.setString(5, String.valueOf(medicamentDTO.usage()));
            pStMedication.setString(6, String.valueOf(medicamentDTO.type()));

            PreparedStatement pstAdministrationMethod = conn.prepareStatement("INSERT INTO administration(frequency,timeOfDay,beforeEating) VALUES (?,?,?) ");
            pstAdministrationMethod.setInt(1, Integer.valueOf(medicamentDTO.administrationMethodDTO().frequency()));
            pstAdministrationMethod.setString(2, String.valueOf(medicamentDTO.administrationMethodDTO().timeOfDay()));
            pstAdministrationMethod.setBoolean(3, medicamentDTO.administrationMethodDTO().beforeEating());


            // 5. execute a prepared statement
            int rowsInserted = pStMedication.executeUpdate();
            int rowsInsertedA = pstAdministrationMethod.executeUpdate();
            System.out.println("Inserted " + rowsInserted + rowsInsertedA);

            // 6. close the objects will happen due to the try-with-resources + AutoClosable interface
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }


    }
}
