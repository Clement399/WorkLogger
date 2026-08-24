import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    // A simple script to keep track of overdue tasks/ work and keep track of tasks
    // Helps to manage life when things get messy

    // "Database"
    static ArrayList<Work> WorkList=new ArrayList<>();

    // Now, we need methods to add and remove from list
    public void recordWork(Work work){
        WorkList.add(work);
    }

    public void removeWork(Work work){
        WorkList.remove(work);
    }

    public static void printLogin(){
        System.out.println("************************************************");
        System.out.println("*     Welcome To Clement's Work Calculator     *");
        System.out.println("*     Today's Date : "+ LocalDate.now() +"     *");
        System.out.println("*     Time         : "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +"     *");
        System.out.println("*     You have xx hours left in this day       *");
        System.out.println("*  ------------------------------------------- *");
        System.out.println("*       Work overdue this week :               *");
        if(!WorkList.isEmpty()){
            for (int i = 0; i < WorkList.size(); i++) {
                System.out.println("*  "+ i +"."+ WorkList.get(i) +"  *");
            }
        }
        else{
            System.out.println("*     Good Job !!                              *");
            System.out.println("*     No Work Overdue this week :              *");
        }
        System.out.println("*  ------------------------------------------- *");
        System.out.println("*******      Press any key to continue   *******");
    }

    // Now, we want to build a menu
    public static void printMenu() {
        System.out.println("*  ------------------------------------------- *");
        System.out.println("*                                              *");
        System.out.println("*             Operations List:                 *");
        System.out.println("*                                              *");
        System.out.println("*         1. View all overdue work             *");
        System.out.println("*         2. Add task to list                  *");
        System.out.println("*         3. Remove/Completed work             *");
        System.out.println("*         4. View completed work               *");
        System.out.println("*         5. Log out                           *");
        System.out.println("*                                              *");
        System.out.println("*  ------------------------------------------- *");
    }
    public static int inputWeek(Scanner sc){
        Scanner sc2 = sc;
        int wWeek = 0;
        try{
            System.out.println("Please input work assigned week ( int ): ");
             wWeek = Integer.parseInt(sc.nextLine());
        }
        catch(InputMismatchException e2){
            inputWeek(sc2);
        }
        return wWeek;
    }

    public static void addWork(Scanner sc){
        try {
            System.out.println("|----------------------------------------------|");
            sc.nextLine();
            System.out.println("|           Please input work category         |");
            String wCategory = sc.nextLine();
            System.out.println("|             Please input work name           |");
            String wName = sc.nextLine();
            int wWeek = inputWeek(sc);
            System.out.println("|              Please input work time           |");
            String dateFormat = "dd/MM/yyyy";
            Date wAssigned = new Date();
            System.out.println("|           Please input task due date          |");
            Date wDue = new Date();
            System.out.println("|  Please input task duration ( estimated )     |");
            Double wDuration = Double.parseDouble(sc.nextLine());
            System.out.println("|        Please input task achievability        |");
            System.out.println("|             ( SSS/SS/A/B/C/D )                |");
            String wComplexity = sc.nextLine();
            Work temp = new Work(wCategory, wName, wWeek, wAssigned, wDue, wDuration, wComplexity);
            System.out.println("|----------------------------------------------|");
            System.out.println("|                Work Recorded !               |");
            System.out.println("Work created :  "+ temp);
            WorkList.add(temp);
    }
    catch (Exception e) {
        System.out.println(e.getMessage());
    }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        // We want to build a menu
        printLogin();
        sc.nextLine();
        // Menu

        // After this, we can make it so that it opens menu on keyboard interaction
        // we want the menu to keep printing, except when prompted exit
        int choice = 0;
        do {
            printMenu();
            choice = sc.nextInt();
            Thread.sleep(1000);
            switch (choice) {
                case 1:
                    // Show all work
                    System.out.println("Showing list of overdue work ...");
                    break;
                case 2:
                    System.out.println("Please follow the incoming framework to insert work ...");
                    addWork(sc);
                    break;
                case 3:
                    System.out.println("Congratulations ! \n What is the work name/ code?");
                    break;
                case 4:
                    System.out.println("Fetching all completed work ...");
                    System.out.println("All completed work :");
                    break;
            }
        }
        while (choice != 5);

        sc.nextLine();
        System.out.println("Thank you ! be productive as always");
        System.out.println("***********************************************");

        // Operations
        // 1. Show all work
        // 2. Add work -- addWork(sc);
    }

    // Operation methods
    // 1. Add task
    // demo in app


    // Data Classes
    static class Work{
        // Base class for a task
        String category;
        String name;
        int week;
        Date assignedDate;
        Date dueDate;
        double duration;
        String complexity;
        Date completionDate;
        boolean completed;

        public Work(String cat, String name, int week, Date assignedDate, Date dueDate, double duration, String complexity) {
            this.category = cat;
            this.name = name;
            this.week = week;
            this.assignedDate = assignedDate;
            this.dueDate = dueDate;
            this.duration = duration;
            this.complexity = complexity;
            this.completionDate = new Date(); // set default date
            this.completed = false;
        }

        @Override
        public String toString() {
            /* Now, we have a valid to string */
            return("| "+ name +" | "+ category+ " | "+ week+ " | "+ assignedDate+ " | "+ dueDate+ " | "+ duration+ " | "+ complexity+ " | "+ completionDate+" |");
        }
    }

}