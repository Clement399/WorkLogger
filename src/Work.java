import java.time.LocalDate;
// Data Class for work, instead of monolithic system
public class Work {

        long id;
        // Base class for a task
        String category;
        String name;
        int week;
        LocalDate assignedDate;
        LocalDate dueDate;
        long duration;
        String complexity;
        LocalDate completionDate;
        boolean completed;

        public Work(String cat, String name, int week, LocalDate assignedDate, LocalDate dueDate, long duration, String complexity) {
            this.category = cat;
            this.name = name;
            this.week = week;
            this.assignedDate = assignedDate; // Should be localDate
            this.dueDate = dueDate; // Should be localDate
            this.duration = duration;
            this.complexity = complexity;
            this.completionDate = null; // set default date
            this.completed = false;
        }

        @Override
        public String toString() {
            /* Now, we have a valid to string */
            return("| "+ name +" | "+ category+ " | "+ week+ " | Assigned : "+ assignedDate+ " | DUE : "+ dueDate+ " | "+ duration+ " | "+ complexity+ " | "+ completionDate+" |");
        }

}
