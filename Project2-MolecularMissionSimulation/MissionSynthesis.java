import java.util.*;

public class MissionSynthesis {

    private final List<MolecularStructure> humanStructures;
    private final List<MolecularStructure> vitalesStructures;

    public MissionSynthesis(List<MolecularStructure> humanStructures, List<MolecularStructure> vitalesStructures) {
        this.humanStructures = humanStructures;
        this.vitalesStructures = vitalesStructures;
    }

    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Molecule> selectedMolecules = new ArrayList<>();

        for (MolecularStructure structure : humanStructures) {
            Molecule weakestMolecule = structure.getMoleculeWithWeakestBondStrength();
            selectedMolecules.add(weakestMolecule);
        }

        for (MolecularStructure structure : vitalesStructures) {
            Molecule weakestMolecule = structure.getMoleculeWithWeakestBondStrength();
            selectedMolecules.add(weakestMolecule);
        }

        List<Bond> potentialBonds = new ArrayList<>();

        Molecule centerM = selectedMolecules.get(0);

        for (Molecule target: selectedMolecules){
            if (target.getBondStrength() < centerM.getBondStrength()){
                centerM = target;
            }
        }

        for (int i = 0; i < selectedMolecules.size(); i++) {
            if (!centerM.equals(selectedMolecules.get(i))){
                Molecule addedM = selectedMolecules.get(i);
                double bondStrength = (double) (centerM.getBondStrength() + addedM.getBondStrength()) / 2;
                Bond bond = new Bond(centerM, addedM, bondStrength);
                potentialBonds.add(bond);
            }
        }

        potentialBonds.sort(Comparator.comparingDouble(Bond::getWeight));

        Set<MolecularStructure> connectedStructures = new HashSet<>();
        for (Bond bond : potentialBonds) {
            Molecule m1 = bond.getFrom();
            Molecule m2 = bond.getTo();
            MolecularStructure structure1 = findStructureContainingMolecule(m1);
            MolecularStructure structure2 = findStructureContainingMolecule(m2);

            if (structure1 != null && structure2 != null ) {
                serum.add(bond);
                connectedStructures.add(structure1);
                connectedStructures.add(structure2);


                if (connectedStructures.size() == humanStructures.size() + vitalesStructures.size()) {
                    break;
                }
            }
        }

        return serum;

    }

    public void printSynthesis(List<Bond> serum) {

        List<String> humanMolecules = new ArrayList<>();
        List<String> vitalesMolecules = new ArrayList<>();

        for (MolecularStructure structure : humanStructures) {
            Molecule weakestMolecule = structure.getMoleculeWithWeakestBondStrength();

            humanMolecules.add(weakestMolecule.getId());
        }


        for (MolecularStructure structure : vitalesStructures) {
            Molecule weakestMolecule = structure.getMoleculeWithWeakestBondStrength();
            vitalesMolecules.add(weakestMolecule.getId());
        }


        Collections.reverse(humanMolecules);
        Collections.reverse(vitalesMolecules);

        System.out.println("Typical human molecules selected for synthesis: " + humanMolecules);
        System.out.println("Vitales molecules selected for synthesis: " + vitalesMolecules);
        System.out.println("Synthesizing the serum...");

        double totalBondStrength = 0.0;

        for (Bond bond : serum) {
            Molecule from = bond.getFrom();
            Molecule to = bond.getTo();
            double strength = bond.getWeight();

            String smallValue = from.getId(), bigValue= to.getId();

            if (from.compareTo(to) > 0){
                smallValue = to.getId();
                bigValue = from.getId();
            }

            System.out.printf("Forming a bond between %s - %s with strength %.2f%n", smallValue, bigValue, strength);
            totalBondStrength += strength;
        }
        System.out.printf("The total serum bond strength is %.2f%n", totalBondStrength);

    }

    private MolecularStructure findStructureContainingMolecule(Molecule molecule) {
        for (MolecularStructure structure : humanStructures) {
            if (structure.hasMolecule(molecule.getId())) {
                return structure;
            }
        }
        for (MolecularStructure structure : vitalesStructures) {
            if (structure.hasMolecule(molecule.getId())) {
                return structure;
            }
        }
        return null;
    }
}
