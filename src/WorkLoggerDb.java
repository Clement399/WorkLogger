import java.sql.*;
import java.time.LocalDate;

public class WorkLoggerDb {

    public WorkLoggerDb() {
    }

    public void initialize(){
            // url = uniform resource locator
            var url = ("jdbc:sqlite:data/worklogger3.db");


            // Basic operations : 1. Get connection
            // 2. Create statement from connection conn.createStatement("SQL QUERY")
            // 3. Insert SQL Query
            // 4. Execute Statement/ ResultSet
            // 5. Try, catch resultset
            try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS work(id INTEGER Primary Key AUTOINCREMENT," +
                        "category VARCHAR(50) NOT NULL," +
                        "name VARCHAR(200) NOT NULL," +
                        "week Int NOT NULL," +
                        "assignedDate Char(10) NOT NULL," +
                        "dueDate Char(10) NOT NULL," +
                        "duration Float NOT NULL," +
                        "complexity Varchar(5) NOT NULL," +
                        "completionDate Date ," +
                        "completed Bool NOT NULL DEFAULT 0 ) ");
                System.out.println("Connection to SQLite has been established.");
            }
            catch(SQLException e){
                System.out.println(e);
                System.out.println(e.getMessage());
            }
    }
    public long insert(Main.Work w) {
        var url = ("jdbc:sqlite:data/worklogger3.db");

        var insertQuery = ("INSERT INTO work(category, name, week, assignedDate, dueDate, duration, complexity) VALUES(?,?,?,?,?,?,?);)");
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(insertQuery);) {
                stmt.setString(1,w.category);
                stmt.setString(2,w.name);
                stmt.setInt(3,w.week);
                stmt.setString(4, (w.assignedDate).toString());
                stmt.setString(5, (w.dueDate).toString());
                stmt.setFloat(6,w.duration);
                stmt.setString(7, (w.complexity));
                stmt.executeUpdate();
                try (
                        Statement idStatement = conn.createStatement();
                        ResultSet rs = idStatement.executeQuery(
                                "SELECT last_insert_rowid()"
                        )
                ) {
                    if (!rs.next()) {
                        throw new SQLException(
                                "Could not retrieve generated ID."
                        );
                    }

                    long id = rs.getLong(1);

                    System.out.println("Inserted work with ID: " + id);
                    w.id = id;

                }
                System.out.println("Inserted records successfully.");
                System.out.println("Id of work: " + w.id);
        }
        catch(SQLException e){
            System.out.println(e);
        }

        return w.id;
    }
    // For testing out
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:data/worklogger3");
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 42")) {
            rs.next();
            System.out.println(rs.getInt(1)); // prints 42
        }
        WorkLoggerDb wdb = new WorkLoggerDb();
        wdb.initialize();
        Main.Work w = new Main.Work("cat","workname",3, LocalDate.now(), LocalDate.of(2026,10,2),5,"S");
        System.out.println(w);
        wdb.insert(w);
    }

}
