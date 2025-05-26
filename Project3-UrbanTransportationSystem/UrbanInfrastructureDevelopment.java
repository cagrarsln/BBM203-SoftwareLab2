import java.io.Serializable;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project : projectList) {
            

            int[] schedule = project.getEarliestSchedule();
            project.printSchedule(schedule);

        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));

            Element root = document.getDocumentElement();

            NodeList projectNodes = root.getElementsByTagName("Project");

            for (int i = 0; i < projectNodes.getLength(); i++) {
                Element projectElement = (Element) projectNodes.item(i);

                String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();

                List<Task> tasks = new ArrayList<>();
                NodeList taskNodes = projectElement.getElementsByTagName("Task");

                for (int j = 0; j < taskNodes.getLength(); j++) {
                    Element taskElement = (Element) taskNodes.item(j);

                    int taskID = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());

                    String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();

                    int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());

                    List<Integer> dependencies = new ArrayList<>();
                    NodeList dependenciesNodeList = taskElement.getElementsByTagName("DependsOnTaskID");
                    for (int k = 0; k < dependenciesNodeList.getLength(); k++) {
                        int dependsOnTaskID = Integer.parseInt(dependenciesNodeList.item(k).getTextContent());
                        dependencies.add(dependsOnTaskID);
                    }

                    Task task = new Task(taskID, description, duration, dependencies);
                    tasks.add(task);
                }

                Project project = new Project(projectName, tasks);
                projectList.add(project);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectList;
    }
}
