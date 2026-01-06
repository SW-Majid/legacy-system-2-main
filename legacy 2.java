import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LegacyStudentSystem {

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    static ArrayList students = new ArrayList(); // Raw type

    public static void main(String[] args) {

        connectDB();
        fetchStudents("CS101");

        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i));
        }

        closeDB();
    }

    public static void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Deprecated
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/studentdb",
                    "root",
                    "root"); // Hard-coded credentials
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace(); // Bad practice
        }
    }

    public static void fetchStudents(String course) {

        String query = "SELECT * FROM students WHERE course = '" 
                        + course + "'"; // SQL Injection

        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                students.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeDB() {
        try {
            rs.close();   // Possible NPE
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
