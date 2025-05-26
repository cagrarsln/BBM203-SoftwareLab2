# Urban Transportation System – BBM204 Project 3

This Java project models a smart urban transportation system involving stations, train lines, and route planning. It supports advanced pathfinding using Dijkstra's algorithm.

## 📚 Course Info
- Course: BBM204 – Software Laboratory II
- Semester: Spring 2024

## 🧠 Project Description

The simulation handles the following:

- Defining city infrastructure including **stations**, **routes**, and **hyperloop networks**
- Finding shortest paths between stations using **Dijkstra's algorithm**
- Modeling tasks such as infrastructure planning and train route assignments

## 🧩 Main Components

| File | Description |
|------|-------------|
| `Main.java` | Entry point of the simulation |
| `UrbanTransportationApp.java` | User interface for executing simulation scenarios |
| `Station.java`, `TrainLine.java` | Core entities for the transportation system |
| `Edge.java`, `EdgeWeightedGraph.java` | Graph structure used for route optimization |
| `DijkstraSP.java` | Dijkstra's shortest path implementation |
| `Project.java`, `Task.java` | Task and planning abstractions |
| `HyperloopTrainNetwork.java` | Optional high-speed route modeling |
| `UrbanInfrastructureDevelopment.java` | Tools for simulating infrastructure planning |

## 🚀 How to Run

```bash
javac *.java
java Main