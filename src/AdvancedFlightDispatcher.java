import java.sql.*;
import java.util.*;

public class AdvancedFlightDispatcher {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the database password:");
        String password = scanner.nextLine();
        scanner.close();

        String url = "jdbc:mysql://localhost:3306/FlightDB?allowLoadLocalInfile=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jerusalem&useSSL=false";
        try (Connection conn = DriverManager.getConnection(url, "root", password)) {
            List<Integer> arrivalIds = Arrays.asList(1, 3, 5);
            updateMultipleDelays(conn, arrivalIds, "Landed - Final");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database or updating flight statuses: " + e.getMessage());
        }
    }

    public static void updateMultipleDelays(Connection conn, List<Integer> arrivalIds, String newStatus) throws SQLException {
        String sql = "UPDATE Arrivals SET delay_status = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            for (Integer id : arrivalIds) {
                pstmt.setInt(2, id);
                pstmt.addBatch();
            }
            int[] results = pstmt.executeBatch();
            int totalAffectedRows = 0;
            for (int result : results) {
                totalAffectedRows += result;
            }
            System.out.println("Total rows affected: " + totalAffectedRows);
        }
    }
}
