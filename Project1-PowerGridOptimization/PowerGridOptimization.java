import java.util.ArrayList;
import java.util.List;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {


    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        // TODO: YOUR CODE HERE
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        int[] SOL = new int[N + 1];
        ArrayList<Integer>[] HOURS = new ArrayList[N + 1];

        SOL[0] = 0;
        HOURS[0] = new ArrayList<>();

        for (int j = 1; j <= N; j++) {
            SOL[j] = 0;
            HOURS[j] = new ArrayList<>();
            for (int i = 0; i < j; i++) {
                int energy = (j-i) * (j-i);
                int minEnergy = Math.min(amountOfEnergyDemandsArrivingPerHour.get(j - 1), energy);
                int newEnergy = SOL[i] + minEnergy;
                if (newEnergy > SOL[j]) {
                    SOL[j] = newEnergy;
                    HOURS[j] = new ArrayList<>(HOURS[i]);
                    HOURS[j].add(j);
                }
            }
        }

        return new OptimalPowerGridSolution(SOL[N], HOURS[N]);
    }
}
