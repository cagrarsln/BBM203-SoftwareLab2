# Molecular Mission Simulation – BBM204 Project 2

This Java project simulates the synthesis and management of molecular structures for specific missions.

## 📚 Course Info
- Course: BBM204 – Software Laboratory II
- Semester: Spring 2024

## 🧠 Project Description

The system manages molecule definitions, bonds, and simulates two types of missions:

- **Mission Genesis**: Generates valid molecular structures from predefined components.
- **Mission Synthesis**: Combines or evaluates molecules for complex formation.

## 🧩 Main Components

| File | Description |
|------|-------------|
| `Main.java` | Launches the simulation and handles mission selection |
| `Bond.java` | Represents bonds between atoms in a molecule |
| `MolecularData.java` | Parses and stores molecular input data |
| `MolecularStructure.java` | Models atomic and molecular configurations |
| `MissionGenesis.java` | Handles structure generation logic |
| `MissionSynthesis.java` | Manages molecule combination/synthesis logic |
| `Molecule.java` | Core entity representing a molecule |

## 🚀 How to Run

```bash
javac *.java
java Main