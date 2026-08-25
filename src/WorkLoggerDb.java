import java.sql.*;
import java.time.LocalDate;

public class WorkLoggerDb {
    public final String url = "jdbc:sqlite:data/worklogger3.db";
    public WorkLoggerDb() {
    }

    public void initialize(){
            // url = uniform resource locator

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
            createCompletedTable();
            System.out.println("Both tables located.");
    }

    public void createCompletedTable(){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS completed(id INTEGER Primary Key AUTOINCREMENT," +
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
    public long insert(Main.Work w, String dbName) {
        var url = ("jdbc:sqlite:data/worklogger3.db");

        var insertQuery = ("INSERT INTO "+ dbName +"(category, name, week, assignedDate, dueDate, duration, complexity, completionDate, completed) VALUES(?,?,?,?,?,?,?,?,?);)");
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(insertQuery);) {
                stmt.setString(1,w.category);
                stmt.setString(2,w.name);
                stmt.setInt(3,w.week);
                stmt.setString(4, (w.assignedDate).toString());
                stmt.setString(5, (w.dueDate).toString());
                stmt.setFloat(6,w.duration);
                stmt.setString(7, (w.complexity));
                if(w.completionDate != null){
                    stmt.setString(8, (w.completionDate).toString());
                }
                stmt.setBoolean(9,w.completed);
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
                System.out.println("Inserted work in : " + dbName +", "+ w);
        }
        catch(SQLException e){
            System.out.println(e);
        }

        return w.id;
    }
    //Select
    public Main.Work selectOne(long id, String dbName){
        String selectQuery = "SELECT * FROM "+ dbName +" WHERE id = " + id;
        try(Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectQuery);){
            while (rs.next()) {
                System.out.printf("| %-5d| %-25s| %-25s| %-3d| %-12s | %-12s |%3dh | %-5s | %-12s |%-10s |%n",
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getInt("week"),
                        rs.getString("assignedDate"),
                        rs.getString("dueDate"),
                        rs.getInt("duration"),
                        rs.getString("complexity"),
                        rs.getString("completionDate"),
                        rs.getInt("completed"));
                long sId = rs.getLong("id");
                String sCategory = rs.getString("category");
                String sName = rs.getString("name");
                int sWeek = rs.getInt("week");
                LocalDate sAssignedDate = LocalDate.parse(rs.getString("assignedDate"));
                LocalDate sDueDate = LocalDate.parse(rs.getString("dueDate"));
                long sDuration = rs.getLong("duration");
                String sComplexity = rs.getString("complexity");
                LocalDate sCompletionDate = null;
                if((rs.getString("completionDate") != null)){
                    sCompletionDate = LocalDate.parse(rs.getString("completionDate"));
                }
                boolean sCompleted = rs.getBoolean("completed");
                Main.Work selectedWork = new Main.Work(sCategory,sName,sWeek,sAssignedDate,sDueDate,sDuration,sComplexity);
                selectedWork.id = sId;
                selectedWork.completionDate = sCompletionDate;
                selectedWork.completed = sCompleted;
                return selectedWork;
            }
        }catch( SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public void selectAll(String dbName){
        String selectQuery = "SELECT * FROM "+ dbName;
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);){
            while (rs.next()) {
                System.out.printf("| %-5d| %-25s| %-25s| %-3d| %-12s | %-12s |%3dh | %-5s | %-12s |%-10s |%n",
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getInt("week"),
                        rs.getString("assignedDate"),
                        rs.getString("dueDate"),
                        rs.getInt("duration"),
                        rs.getString("complexity"),
                        rs.getString("completionDate"),
                        rs.getInt("completed"));
            }
        }catch( SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    //Update
    public void update(long id,Main.Work w){
        String updateQuery = """
                UPDATE work SET category = ?
                , name = ?
                , week = ?
                , assignedDate = ?
                , dueDate = ?
                , duration = ?
                , complexity = ?
                , completionDate = ?
                , completed = ?
                 WHERE id = ?;
                """;
        try(Connection conn = DriverManager.getConnection(url);
        PreparedStatement stmt = conn.prepareStatement(updateQuery);){
            stmt.setString(1,w.category);
            stmt.setString(2,w.name);
            stmt.setInt(3,w.week);
            stmt.setString(4,(w.assignedDate).toString());
            stmt.setString(5,(w.dueDate).toString());
            stmt.setFloat(6,w.duration);
            stmt.setString(7,(w.complexity));
            stmt.setString(8,(w.completionDate).toString());
            stmt.setBoolean(9,(w.completed));
            stmt.setLong(10,id);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public void updateCompleted(long id){
        String updateQuery = """
                UPDATE work SET
                 completionDate = ?
                 , completed = 1
                 WHERE id = ?;
                """;
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(updateQuery);){
            stmt.setString(1, LocalDate.now().toString());
            stmt.setLong(2,id);
            stmt.executeUpdate();
            System.out.println("Update -- completed work with ID: " + id);
            selectOne(id,"work");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Delete
    public void delete(long id){
        String deleteQuery = """
                Delete from work WHERE id = ?;
                """;
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(deleteQuery);){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
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
        Main.Work w = new Main.Work("newlongcat","workname",3, LocalDate.now(), LocalDate.of(2026,10,2),5,"S");
        System.out.println(w);
        wdb.insert(w, "work");
        wdb.selectOne(2,"work");
        System.out.println("All records");
        wdb.selectAll("work");
        Main.Work change = new Main.Work("newcategory","newworkname",300, LocalDate.now(), LocalDate.of(2026,12,22),3,"S");
        // can update together, or leave for update when completed -- for future configurations
        change.completionDate = LocalDate.now();
        change.completed = true;
        wdb.update(3,change);
        wdb.selectOne(3,"work");
        wdb.updateCompleted(7);
        wdb.delete(37);
        wdb.selectAll("work");
        wdb.completedWork(71);
        System.out.println("All records in completed array");
        wdb.selectAll("completed");
    }

    // Completed :
    // When completed,
    /*
    * 1. work(i).completeTime = time.now
    * 2. completed.add work(i)
    * 3. work.delete(i)
    * 4. (optional) : print completed ((completed.last-5) ... completed.last)
    *
    * */
    public void completedWork(long id){
        if(selectOne(id,"work") != null){
            updateCompleted(id);
            Main.Work selected = selectOne(id,"work");
            insert(selected,"completed");
            delete(id);
        }
        else{
            System.out.println("No work #"+ id +" in completedWork");
        }
    }

}
