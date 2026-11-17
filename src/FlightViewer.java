import java.sql.*;
import java.util.Scanner;

public class FlightViewer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for database password and terminal number
        System.out.print("Enter database password (root): ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter target Terminal number (2, 3, 4, 5): ");
        String terminal = scanner.nextLine().trim();

        // JDBC URL for connecting to MySQL FlightDB
        String url = "jdbc:mysql://localhost:3306/FlightDB?allowLoadLocalInfile=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jerusalem&useSSL=false";
        String user = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("SELECT Arrivals.id, Arrivals.flight_number, FlightRoutes.from_city, Arrivals.scheduled, Arrivals.delay_status, Arrivals.terminal FROM Arrivals INNER JOIN FlightRoutes ON Arrivals.flight_number = FlightRoutes.flight_number WHERE Arrivals.terminal = ? ORDER BY Arrivals.scheduled ASC")) {

            // Bind the parameter
            pstmt.setString(1, terminal);

            // Execute query and print results in a clean table format
            boolean found = false;
            String format = "%-5s | %-10s | %-18s | %-10s | %-18s | %-8s%n";
            String divider = "-------------------------------------------------------------------------------------";

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No flights were found for Terminal " + terminal);
                    return;
                }

                // Print table header and top divider
                System.out.printf(divider + "%n");
                System.out.printf("%-5s | %-10s | %-18s | %-10s | %-18s | %-8s%n", "ID", "Flight", "From City", "Scheduled", "Delay Status", "Terminal");
                System.out.printf(divider + "%n");

                do {
                    // Print each row
                    System.out.printf(format, rs.getInt("id"), rs.getString("flight_number"), rs.getString("from_city"), rs.getTime("scheduled").toString(), rs.getString("delay_status"), rs.getString("terminal"));
                } while (rs.next());

                // Print bottom divider
                System.out.printf(divider + "%n");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
