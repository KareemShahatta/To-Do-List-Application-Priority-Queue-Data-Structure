import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Comparable<Task>
{
    static int tasksId = 1;

    public Task(int taskId, String subject, Priority priority, Status status, LocalDateTime startDate, LocalDateTime dueDate) {
        this.taskId = taskId;
        this.subject = subject;
        this.priority = priority;
        this.status = status;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    int taskId;
    String subject;
    Priority priority;
    Status status;
    LocalDateTime startDate;
    LocalDateTime dueDate;


    @Override
    public int compareTo(Task o)
    {
        return this.getPriority().compareTo(o.getPriority());
    }

    @Override
    public String toString() {
        return "Id:" + taskId + "; Subject: " + subject + "; Status: " + status + "; Priority: " + priority + "; StartDate: " + dateFormatter(startDate) + "; Due date: " + dateFormatter(dueDate);
    }

    private String dateFormatter(LocalDateTime date) {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public int getTaskId() {
        return taskId;
    }
    public Priority getPriority()
    {
        return priority;
    }


    public static int initializeNewId() {
        return tasksId++;
    } //Static to make it easier to increment the id without too much code.
}