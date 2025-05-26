# Algorithm Performance Analysis – BBM204 Project 4

This project evaluates the practical performance of various sorting and searching algorithms by comparing their runtime behaviors with their theoretical complexities.

## 📚 Course Info
- Course: BBM204 – Software Laboratory II
- Semester: Spring 2024

## 🧠 Project Description

The following algorithms were implemented and tested:

### Sorting Algorithms:
- Insertion Sort
- Merge Sort
- Counting Sort

### Searching Algorithms:
- Linear Search
- Binary Search

The algorithms were executed on multiple datasets:
- Randomly ordered data
- Already sorted data
- Reversely sorted data

Performance measurements were recorded and plotted using XChart. The project report (`report.pdf`) includes runtime tables and performance graphs, which support the theoretical analysis of each algorithm.

## 🧩 Main Components

| File | Description |
|------|-------------|
| `Main.java` | Entry point for running experiments |
| `Sorts.java` | Contains implementation of sorting algorithms |
| `Searches.java` | Contains linear and binary search implementations |
| `FileFunctions.java` | File parsing and data preparation |
| `report.pdf` | Full report including analysis, tables, graphs, and complexity tables |

## 🚀 How to Run

```bash
javac *.java
java Main