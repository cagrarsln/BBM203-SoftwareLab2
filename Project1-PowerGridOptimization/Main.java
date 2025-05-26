import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Integer> demandSchedule = new ArrayList<>();
        ArrayList<Integer> ESVMaintenance = new ArrayList<>();

        String[] preDemandSchedule = FileReader.readFile(args[0]);
        String[] preESVMaintenance = FileReader.readFile(args[1]);


        for (String line : preDemandSchedule) {
            String[] datas = line.split(" ");
            for (String data: datas){
                demandSchedule.add(Integer.parseInt(data));
            }
        }

        for (String line : preESVMaintenance) {
            String[] datas = line.split(" ");
            for (String data: datas){
                ESVMaintenance.add(Integer.parseInt(data));
            }
        }

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call GetOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.
        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(demandSchedule);

        OptimalPowerGridSolution solution = powerGridOptimization.getOptimalPowerGridSolutionDP();

        // Demanded GW
        int demandedGW = 0;
        for (int gw: demandSchedule){
            demandedGW += gw;
        }

        // Satisfied GW
        int satisfiedGW = solution.getmaxNumberOfSatisfiedDemands();

        // Efficiency Hours
        ArrayList<Integer> efficiencyHours = solution.getHoursToDischargeBatteriesForMaxEfficiency();
        String stringHours = "";

        for (int hourIndex = 0; hourIndex < efficiencyHours.size(); hourIndex++){
            if (hourIndex == efficiencyHours.size()-1){
                stringHours += efficiencyHours.get(hourIndex);
            } else {
                stringHours += efficiencyHours.get(hourIndex) + ", ";
            }
        }
        // Unsatisfied GW
        int unsatisfiedGW = demandedGW - solution.getmaxNumberOfSatisfiedDemands();

        // Output Part
        System.out.println("The total number of demanded gigawatts: " + demandedGW);
        System.out.println("Maximum number of satisfied gigawatts: " + satisfiedGW);
        System.out.println("Hours at which the battery bank should be discharged: " + stringHours);
        System.out.println("The number of unsatisfied gigawatts: " + unsatisfiedGW);

        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.
        ArrayList<Integer> newESVMaintenance = new ArrayList<>(ESVMaintenance.subList(2, ESVMaintenance.size()));
        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(newESVMaintenance);

        int esvCount = ESVMaintenance.get(0);
        int capacityOfESV = ESVMaintenance.get(1);
        int optESV = optimalESVDeploymentGP.getMinNumESVsToDeploy(esvCount,capacityOfESV);

        if (optESV != -1){
            System.out.println("The minimum number of ESVs to deploy: " + optESV);
            for (int i = 0; i < optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs().size(); i++) {
                System.out.println("ESV "+( i+1) + " tasks: "+ optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs().get(i));
            }

        }else {
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
}
