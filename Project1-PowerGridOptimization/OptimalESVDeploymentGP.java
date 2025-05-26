import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP
{
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity)
    {
        // TODO: Your code goes here

        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());

        ArrayList<Integer> leftEnergy = new ArrayList<>();
        for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
            leftEnergy.add(maxESVCapacity);
        }

        for (int asked : maintenanceTaskEnergyDemands) {
            boolean done = false;

            for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
                if (asked <= leftEnergy.get(i)) {
                    if (maintenanceTasksAssignedToESVs.size() <= i) {
                        maintenanceTasksAssignedToESVs.add(new ArrayList<>());
                    }
                    maintenanceTasksAssignedToESVs.get(i).add(asked);

                    leftEnergy.set(i, leftEnergy.get(i) - asked);

                    done = true;
                    break;
                }
            }
            if (!done) {
                return -1;
            }
        }
        return maintenanceTasksAssignedToESVs.size();
    }

}
