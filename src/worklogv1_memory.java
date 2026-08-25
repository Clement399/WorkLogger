import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class worklogv1_memory {
    /* Task # 1 -- add real sqlite database */
    static ArrayList<Work> WorkList=new ArrayList<>();
    static ArrayList<Work> CompletedList=new ArrayList<>();

    // Now, we need methods to add and remove from list
    public void recordWork(Work work){
        WorkList.add(work);
    }
    public void recordWorkConnected(WorkLoggerDb wdb, Work w){
        wdb.insert(w,"work");
    }

    public void removeWork(Work work){
        WorkList.remove(work);
    }

    public LocalTime getTime(){
        return LocalTime.now();
    }

    public static void printLogin(){
        System.out.println("************************************************");
        System.out.println("*     Welcome To Clement's Work Calculator     *");
        System.out.println("*     Today's Date : "+ LocalDate.now() +"                *");
        System.out.println("*     Time Zone : "+ java.time.ZoneId.systemDefault() +"              *");
        System.out.println("*     Time         : "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +"                  *");

        // Get hours
        Duration duration = Duration.between( LocalTime.now(),LocalTime.MAX).truncatedTo(ChronoUnit.MINUTES);
        long hourDiff = duration.toHours();
        long minuteDiff = duration.toMinutesPart();
        System.out.println("*                                              *");
        System.out.println("*   You have "+ hourDiff +" hours ("+ minuteDiff +"m) left in this day  ! *");
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
            System.out.println("|   Please input work assigned week ( int )    |");
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
            // Task assignment automated to current time
            // System.out.println("|              Please input task assignment date          |");
            String dateFormat = "dd/MM/yyyy";
            LocalDate wAssigned = LocalDate.now();
            System.out.println("|----------------------------------------------|");
            System.out.println("|     Please input task due date (dd/MM/yyyy)  |");
            LocalDate wDue = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern(dateFormat));
            System.out.println("|  Please input task duration ( estimated )     |");
            long wDuration = Long.parseLong(sc.nextLine());
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


    public static void printWorkList(){
        for (int i = 0; i < WorkList.size(); i++) {
            System.out.println("*  "+ (i + 1) +"."+ WorkList.get(i) +"  *");
        }
    }
    public static void printWorkListShort(){
        for (int i = 0; i < WorkList.size(); i++) {
            System.out.println("* |  "+ (i + 1) +". | Name : "+ WorkList.get(i).name +" | "+ WorkList.get(i).dueDate +" | "+ WorkList.get(i).duration +"h | *");
        }
    }
    public static void printCompletedList(){
        for (int i = 0; i < CompletedList.size(); i++) {
            System.out.println("*  "+ (i + 1) +"."+ CompletedList.get(i) +"  *");
        }
    }
    public static void printCompletedListShort(){
        for (int i = 0; i < CompletedList.size(); i++) {
            System.out.println("* |  "+ (i + 1) +". | Name : "+ CompletedList.get(i).name +" | "+ CompletedList.get(i).dueDate +" | "+ CompletedList.get(i).duration +"h | *");
        }
    }

    //Menu operations
    private static void baseFunctionLoop(Scanner sc) throws InterruptedException {
        int choice = 0;
        do {
            printMenu();
            choice = sc.nextInt();
            Thread.sleep(1000);
            switch (choice) {
                case 1:
                    // Show all work
                    System.out.println("Showing list of overdue work ...");
                    printWorkList();
                    sc.nextLine();
                    break;
                case 2:
                    System.out.println("Please follow the incoming framework to insert work ...");
                    addWork(sc);
                    sc.nextLine();
                    break;
                case 3:
                    System.out.println("Congratulations ! \n Please select code");
                    printWorkListShort();
                    int selectedChoice = (sc.nextInt()-1);

                    // Update completion
                    WorkList.get(selectedChoice).completionDate = LocalDate.now();
                    WorkList.get(selectedChoice).completed = true;
                    System.out.println("Completed work : work " + selectedChoice);


                    // Add work to completed list

                    CompletedList.add(WorkList.get(selectedChoice));
                    // Remove work from list
                    WorkList.remove(selectedChoice);
                    System.out.println("Congratulations ! Work " + selectedChoice + " added to completed list !");
                    sc.nextLine();

                    break;
                case 4:
                    sc.nextLine();
                    String view = "";
                    System.out.println("Congratulations ! \n Please select simple (1) or detailed view (2)");
                    try {
                        view = sc.nextLine();

                        System.out.println("Fetching all completed work ...");
                        System.out.println("All completed work :");
                        if (view.equals("1")) {
                            printCompletedList();
                        } else if (view.equals("2")) {
                            printCompletedListShort();
                        }
                    }
                    catch(InputMismatchException e){
                        System.out.println(e.getMessage());
                        System.out.println("Please input again");
                    }
                    sc.nextLine();
                    break;
            }
        }
        while (choice != 5);
    }
    private static void exit(){
        System.out.println("Thank you ! be productive as always");
        System.out.println("***********************************************");
    }
    public static void main(String[] args) throws InterruptedException {
        // Declare used objects
        WorkLoggerDb wdb = new WorkLoggerDb();
        Scanner sc = new Scanner(System.in);

        //Database functions
        wdb.initialize();

        // We want to build a menu
        printLogin();
        sc.nextLine();
        // Menu

        // After this, we can make it so that it opens menu on keyboard interaction
        // we want the menu to keep printing, except when prompted exit
        baseFunctionLoop(sc);

        sc.nextLine();
        exit();
        // Operations
        // 1. Show all work
        // 2. Add work -- addWork(sc);
    }
}
