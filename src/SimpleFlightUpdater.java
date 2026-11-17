import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SimpleFlightUpdater {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/FlightDB?allowLoadLocalInfile=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jerusalem&useSSL=false";
        String user = "root";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MySQL root password: ");
        String password = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();

            System.out.print("Enter a new delay status for flight 'LX354': ");
            String userInput = scanner.nextLine().trim();

            String sql = "UPDATE Arrivals SET delay_status = '" + userInput + "' WHERE flight_number = 'LX354'";

            int affectedRows = statement.executeUpdate(sql);
            System.out.println("Number of affected rows: " + affectedRows);
        } catch (SQLException e) {
            System.err.println("Error occurred while updating the flight status: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
