import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    public List<MolecularStructure> identifyMolecularStructures() {
        Map<String, MolecularStructure> structuresMap = new HashMap<>();

        for (Molecule molecule : molecules) {
            MolecularStructure structure = findOrCreateStructure(molecule, structuresMap);
            findMolecularStructure(molecule, structure, structuresMap);
        }

        List<MolecularStructure> structuresList = new ArrayList<>(structuresMap.values());
        structuresList.sort((s1, s2) -> Integer.compare(s2.getMolecules().size(), s1.getMolecules().size()));

        List<MolecularStructure> finalStructures = new ArrayList<>();

        for (MolecularStructure currentStructure : structuresList) {
            boolean keepCurrent = true;

            for (MolecularStructure otherStructure : finalStructures) {
                if (haveCommonMolecules(currentStructure, otherStructure)) {
                    if (currentStructure.getMolecules().size() > otherStructure.getMolecules().size()) {
                        finalStructures.remove(otherStructure);
                        break;
                    } else {
                        keepCurrent = false;
                        break;
                    }
                }
            }

            if (keepCurrent) {
                finalStructures.add(currentStructure);
            }
        }

        return finalStructures;
    }


    private boolean haveCommonMolecules(MolecularStructure s1, MolecularStructure s2) {
        Set<Molecule> s1Molecules = new HashSet<>(s1.getMolecules());
        Set<Molecule> s2Molecules = new HashSet<>(s2.getMolecules());

        s1Molecules.retainAll(s2Molecules);

        return !s1Molecules.isEmpty();
    }




    private MolecularStructure findOrCreateStructure(Molecule molecule, Map<String, MolecularStructure> structuresMap) {
        MolecularStructure structure;
        if (structuresMap.containsKey(molecule.getId())) {
            structure = structuresMap.get(molecule.getId());
        } else {
            structure = new MolecularStructure();
            structuresMap.put(molecule.getId(), structure);
        }
        return structure;
    }


    private void findMolecularStructure(Molecule molecule, MolecularStructure structure, Map<String, MolecularStructure> structuresMap) {
        structure.addMolecule(molecule);

        for (String bond : molecule.getBonds()) {
            if (!structure.hasMolecule(bond)) {
                Molecule bondedMolecule = findMoleculeById(bond);
                if (bondedMolecule != null) {
                    findOrCreateStructure(bondedMolecule, structuresMap);
                    findMolecularStructure(bondedMolecule, structure, structuresMap);
                }
            }
        }
    }

    private Molecule findMoleculeById(String moleculeID) {
        return molecules.stream()
                .filter(molecule -> molecule.getId().equals(moleculeID))
                .findFirst()
                .orElse(null);
    }



    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {


        int structureNumber = 1;

        System.out.printf("%d molecular structures have been discovered in %s individuals.%n", molecularStructures.size(), species);

        for (MolecularStructure structure: molecularStructures) {
            System.out.printf("Molecules in Molecular Structure %d: %s%n", structureNumber, structure);
            structureNumber++;
        }


    }

    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        for (MolecularStructure targetStructure : targetStructures) {
            if (!sourceStructures.contains(targetStructure)) {
                anomalyList.add(targetStructure);
            }
        }

        return anomalyList;
    }

    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");

        for (MolecularStructure structure: molecularStructures) {
            System.out.println(structure);

        }


    }
}
