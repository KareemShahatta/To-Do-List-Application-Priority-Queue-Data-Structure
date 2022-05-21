import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.PriorityQueue;
import java.util.Scanner;

import static java.time.LocalDateTime.now;

public class Main
{
    PriorityQueue<Task> usersTasks = new PriorityQueue<>();
    PriorityQueue<Task> usersTasksPlaceHolder = new PriorityQueue<>();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    void start() {
        System.out.println("OPENING YOUR TODO-List");
        System.out.println("======================");
        menu();
    }

    void menu() {
        System.out.print("What do you want to perform? (A:Add, E:Edit, N:Next, L:List, V:View, R:Remove, Q:Quit) : ");
        try
        {
            switch (scanner.nextLine().toLowerCase().charAt(0))
            {
                case 'a' : add(); break;
                case 'e' : edit(); break;
                case 'n' : next(); break;
                case 'l' : list(); break;
                case 'v' : view(); break;
                case 'r' : remove(); break;
                case 'q' : quit(); break;
                default:
                    printErrorMessage("Not a valid value");
                    menu();
            }
        }
        catch(StringIndexOutOfBoundsException error)
        {
            printErrorMessage("Please enter an value");
            menu();
        }
    }

    void add() {
        usersTasks.add(new Task(Task.initializeNewId(), getTaskNameInput() , getTaskPriorityInput() , getTaskStatusInput() ,getTaskStartDateInput() , getTaskEndDateInput()));
        System.out.println("Task successfully added!");
        menu();
    }
    void edit() {
        int selectedTaskId = getIdFromUser();
        for(Task task : usersTasks)
        {
            if(task.getTaskId() == selectedTaskId)
            {
                System.out.print("What do you want to edit? (N:Name, P:Priority, S:Status, D:EndDate) : ");
                try
                {
                    switch (scanner.nextLine().toLowerCase().charAt(0))
                    {
                        case 'n' : editTaskName(task); break;
                        case 'p' : editTaskPriority(task); break;
                        case 's' : editTaskStatus(task); break;
                        case 'd' : editTaskEndDate(task); break;
                        default:
                            printErrorMessage("Not a valid value");
                            edit();
                    }

                    System.out.println("Task successfully edited!");
                    menu();
                }
                catch(StringIndexOutOfBoundsException error)
                {
                    printErrorMessage("Please enter an value");
                    edit();
                }
            }
        }
    }
    void next() {
        if(!usersTasks.isEmpty())
        {
            System.out.println(usersTasks.peek().toString());
        }
        else
        {
            printErrorMessage("There are no tasks in your list");
        }

        menu();
    }
    void list() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if(!usersTasks.isEmpty())
        {
            while(!usersTasks.isEmpty())
            {
                Task task = usersTasks.poll();
                System.out.println(task);
                usersTasksPlaceHolder.add(task);
            }

            while(!usersTasksPlaceHolder.isEmpty())
            {
                Task task = usersTasksPlaceHolder.poll();
                usersTasks.add(task);
            }
        }
        else
        {
            System.out.println(" ");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        menu();
    }
    void view() {
        int id = getIdFromUser();
        for(Task task : usersTasks)
        {
            if(task.getTaskId() == id)
            {
                System.out.println(task.toString());
                menu();
                return;
            }
        }

        printErrorMessage("Task doesn't exist");
        menu();
    }
    void remove() {
        int id = getIdFromUser();
        for(Task task : usersTasks)
        {
            if(task.getTaskId() == id)
            {
                usersTasks.remove(task);
                System.out.println("Task successfully removed!");
                menu();
                return;
            }
        }

        printErrorMessage("Task doesn't exist");

        menu();
    }
    void quit() {
        System.out.println("======================");
        System.out.println("CLOSING YOUR TODO-List");
        System.exit(0);
    }

    String getTaskNameInput() {
        System.out.print("Please enter task name : ");
        String input = scanner.nextLine();
        if(!input.replace(" " , "").isEmpty())
        {
            return input;
        }
        else
        {
            printErrorMessage("Task name can't be left empty");
            return getTaskNameInput();
        }
    }
    Priority getTaskPriorityInput() {
        System.out.print("Please enter task priority (N:Normal, L:Low, H:High, U:Urgent) : ");
        try
        {
            switch (scanner.nextLine().toLowerCase().charAt(0))
            {
                case 'n' : return Priority.NORMAL;
                case 'l' : return Priority.LOW;
                case 'h' : return Priority.HIGH;
                case 'u' : return Priority.URGENT;
                default:
                    printErrorMessage("Not a valid value");
                    return getTaskPriorityInput();
            }
        }
        catch(StringIndexOutOfBoundsException error)
        {
            printErrorMessage("Please enter an value");
            return getTaskPriorityInput();
        }
    }
    Status getTaskStatusInput() {
        System.out.print("Please enter task status (N:Not_started, I:In_progress, W:Waiting, D:Deferred) : ");
        try
        {
            switch (scanner.nextLine().toLowerCase().charAt(0))
            {
                case 'n' : return Status.NOT_STARTED;
                case 'i' : return Status.IN_PROGRESS;
                case 'w' : return Status.WAITING;
                case 'd' : return Status.DEFERRED;
                default:
                    printErrorMessage("Not a valid value");
                    return getTaskStatusInput();
            }
        }
        catch(StringIndexOutOfBoundsException error)
        {
            printErrorMessage("Please enter an value");
            return getTaskStatusInput();
        }
    }
    LocalDateTime getTaskStartDateInput() {
        return now();
    }
    LocalDateTime getTaskEndDateInput() {
        System.out.print("Please enter task end date (YYYY-MM-DD) : ");
        String input = scanner.nextLine();
        if(!input.replace(" " , "").isEmpty())
        {
            try
            {
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                return formatter.parse(input).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); //Had to use this line to convert from Date to LocalDateTime

            }
            catch (ParseException error)
            {
                printErrorMessage("Please enter date in the valid format (YYYY-MM-DD)");
                return getTaskEndDateInput();
            }

        }
        else
        {
            printErrorMessage("Task due date can't be left empty");
            return getTaskEndDateInput();
        }
    }

    void editTaskName(Task task) {
       task.setSubject(getTaskNameInput());
    }
    void editTaskPriority(Task task) {
        task.setPriority(getTaskPriorityInput());
    }
    void editTaskStatus(Task task) {
        task.setStatus(getTaskStatusInput());
    }
    void editTaskEndDate(Task task) {
        task.setDueDate(getTaskEndDateInput());
    }

    void printErrorMessage(String message) {
        System.out.println("[ERROR] >> " + message + "\n");
    }
    int getIdFromUser() {
        System.out.print("Please enter task id: ");
        if(scanner.hasNextInt())
        {
            int id = scanner.nextInt();
            scanner.nextLine(); //It does not do anything but prevent a scanner bug while using nextInt() & nextLine()
            return id;
        }
        else
        {
            printErrorMessage("Task id must be a number");
            return getIdFromUser();
        }
    }
}
