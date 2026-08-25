import java.sql.*;

public class workloggerdb {

    public void initialize(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:duckdb:data/worklogger.db");
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("CREATE TABLE IF NOT EXISTS Worklogger(id Int Primary Key," +
                        "category VARCHAR(50)," +
                        "name VARCHAR(200)," +
                        "week Int," +
                        "assignedDate Date," +
                        "dueDate Date," +
                        "duration Float," +
                        "complexity Varchar(5)," +
                        "completionDate Date," +
                        "completed Bool ) ");
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:duckdb:data/worklogger.db");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 42")) {
            rs.next();
            System.out.println(rs.getInt(1)); // prints 42
        }
        workloggerdb wdb = new workloggerdb();
        wdb.initialize();
    }
}
