import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class demonstrates how to update a flight status securely in a MySQL database using JDBC.
 */
public class SimpleFlightUpdater {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/FlightDB?allowLoadLocalInfile=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jerusalem&useSSL=false";
        String user = "root";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MySQL root password: ");
        String password = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Refactored to use PreparedStatement for better security
            String sql = "UPDATE Arrivals SET delay_status = ? WHERE flight_number = 'LX354'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                System.out.print("Enter a new delay status for flight 'LX354': ");
                String userInput = scanner.nextLine().trim();

                // Using PreparedStatement to prevent SQL Injection
                statement.setString(1, userInput);
                int affectedRows = statement.executeUpdate();
            System.out.println("Number of affected rows: " + affectedRows);
        }
        } catch (SQLException e) {
            System.err.println("Error occurred while updating the flight status: " + e.getMessage());
        } finally {
            scanner.close();
    }
}

    /**
     * The original code used a standard Statement which concatenated user input into SQL queries.
     * This approach is vulnerable to SQL Injection because it allows an attacker to inject malicious SQL
     * code by manipulating the input. Using PreparedStatement with parameterized queries (using ?)
     * prevents this vulnerability because the SQL query and parameters are separated, making it impossible
     * for an attacker to alter the query structure.
     */
}
