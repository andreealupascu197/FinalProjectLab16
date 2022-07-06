package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.MedicamentDTO;
import ro.fasttrackit.finalproject.domain.AdministrationMethod;
import ro.fasttrackit.finalproject.domain.Medication;
import ro.fasttrackit.finalproject.domain.Type;
import ro.fasttrackit.finalproject.domain.Usage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
             ResultSet rsA = st.executeQuery("SELECT * FROM administration");

             // 6. iterate the result set and print the values
        ) {
            List<Medication> medicaments = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int quantity = rs.getInt(3);
                double price = rs.getInt(4);
                Date expiryDate = rs.getDate(5);
                String usage = rs.getString("usage");
                String type = rs.getString("type");


                medicaments.add(new Medication(id, name, quantity, price, Date.valueOf(String.valueOf(expiryDate)), Usage.valueOf(usage), Type.valueOf(type)));

                List<AdministrationMethod> medicamentAdministration = new ArrayList<>();
                while ((rsA.next())) {
                    long idUser = rsA.getLong("idUser");
                    int frequency = rsA.getInt(2);

                }
            }
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


            pStMedication.setString(1, medicament.name());
            pStMedication.setInt(2, medicament.quantity());
            pStMedication.setDouble(3, medicament.price());
            pStMedication.setDate(4, Date.valueOf(String.valueOf(medicament.expiryDate())));
            pStMedication.setString(5, String.valueOf(medicament.usage()));
            pStMedication.setString(6, String.valueOf(medicament.type()));



            // 5. execute a prepared statement
            int rowsInserted = pStMedication.executeUpdate();
            System.out.println("Inserted " + rowsInserted);

            // 6. close the objects will happen due to the try-with-resources + AutoClosable interface
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }


    }

}
