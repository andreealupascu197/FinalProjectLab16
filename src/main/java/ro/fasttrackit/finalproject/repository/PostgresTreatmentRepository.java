package ro.fasttrackit.finalproject.repository;

import ro.fasttrackit.finalproject.dataTransferObject.TreatmentDTO;
import ro.fasttrackit.finalproject.domain.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresTreatmentRepository implements TreatmentRepository {
    static final String URL = "jdbc:postgresql://localhost:5432/temaproiect";

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
    public List<Treatment> findAll() {

        try (// 3. obtain a connection
             Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             // 4. create a query statement
             Statement st = conn.createStatement();

             // 5. execute a query
             ResultSet rs = st.executeQuery("SELECT * FROM medication");

             // 6. iterate the result set and print the values
        ) {
            List<Treatment> operations = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int quatity = rs.getInt(3);
                int price = rs.getInt(4);
                operations.add(new Treatment(id, name, quatity, price));
            }
            return operations;
        } catch (SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public void save(TreatmentDTO medicament) {
        // 1. load driver, no longer needed in new versions of JDBC
        try (// 3. obtain a connection
             Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             // 4. create a query statement
             PreparedStatement pSt = conn.prepareStatement(
                     "INSERT INTO medication (name, quantity, price) VALUES (?,?,?)"
             )
        ){
//            Class.forName("org.postgresql.Driver");
//            // 2. define connection params to db



            pSt.setString(1, medicament.name());
            pSt.setInt(2, medicament.quantity());
            pSt.setInt(3, medicament.price());

            // 5. execute a prepared statement
            int rowsInserted = pSt.executeUpdate();
            System.out.println("Inserted " + rowsInserted);

            // 6. close the objects will happen due to the try-with-resources + AutoClosable interface
        } catch ( SQLException e) {
            throw new RepositoryAccessException(e);
        }


    }

}
