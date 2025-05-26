import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        // Calculate the project duration as the end time of the last task
        int[] schedule = getEarliestSchedule();
        int lastTaskID = tasks.size() - 1;
        Task lastTask = tasks.get(lastTaskID);
        int projectDuration = schedule[lastTaskID] + lastTask.getDuration();

        return projectDuration;
    }

    public String getName() {
        return name;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        // Get the total number of tasks
        int numTasks = tasks.size();

        // Create an array to store the earliest start times for each task
        int[] earliestStartTimes = new int[numTasks];

        // Create an array to store the number of dependencies for each task
        int[] inDegree = new int[numTasks];

        // Create a list of task dependencies
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numTasks; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        // Fill the inDegree array and adjacency list
        for (int i = 0; i < numTasks; i++) {
            Task task = tasks.get(i);
            List<Integer> dependencies = task.getDependencies();

            for (int dependency : dependencies) {
                adjacencyList.get(dependency).add(i);
                inDegree[i]++;
            }
        }

        // Create a queue to process tasks with no dependencies (inDegree = 0)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numTasks; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Process tasks in topological order
        while (!queue.isEmpty()) {
            int currentTask = queue.poll();
            Task task = tasks.get(currentTask);

            // Calculate the earliest start time for the current task
            for (int neighbor : adjacencyList.get(currentTask)) {
                // Calculate the earliest start time for the neighbor task
                earliestStartTimes[neighbor] = Math.max(
                        earliestStartTimes[neighbor],
                        earliestStartTimes[currentTask] + task.getDuration()
                );

                // Decrease the inDegree of the neighbor task
                inDegree[neighbor]--;

                // If the neighbor task has no more dependencies, add it to the queue
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return earliestStartTimes;
    }


    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
