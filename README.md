# Project #: Experiments with Hashing

* Author: Anup Bhattarai
* Class: CS321 
* Semester: Spring 2025

## Overview

This program explores different hashing techniques by implementing a hash table and analyzing its performance under various conditions. It tests linear probing and double hashing using different data sources and load factors, measuring the average number of probes required for insertion.

## Reflection

I found this project to be a great learning experience. Unlike previous projects that focused on a single concept or provided pre-written code, this one required starting from scratch, which made it both enjoyable and educational. Working with AWS and running the project via SSH was particularly interesting, as it was my first time doing so. This experience will be valuable for future projects beyond the classroom.

One challenge I faced was obtaining the correct output. I tried to match the expected results, but the average number of probes didn’t seem to be accurate. Aside from that, everything else worked well.

## Compiling and Using

Compile the code using the following commands:

$ javac HashtableExperiment.java

After compiling, run the program using the following format:

java HashtableExperiment.java <dataSource> <loadFactor> <debugLevel>

where:
    <dataSource>: 1 ==> random numbers
                     2 ==> date value as a long
                     3 ==> word list
       <loadFactor>: The ratio of objects to table size,
                       denoted by alpha = n/m
       <debugLevel>: 0 ==> print summary of experiment
                     1 ==> save the two hash tables to a file at the end
                     2 ==> print debugging output for each insert


## Results 

### Data Source 1

| Load Factor | Hashing Type | Average Number of Probes |
|-------------|--------------|--------------------------|
| 0.5         | Linear       | 1.50                     |
| 0.5         | Double       | 1.38                     |
| 0.6         | Linear       | 1.72                     |
| 0.6         | Double       | 1.52                     |
| 0.7         | Linear       | 2.15                     |
| 0.7         | Double       | 1.71                     |

### Data Source 2

| Load Factor | Hashing Type | Average Number of Probes |
|-------------|--------------|--------------------------|
| 0.5         | Linear       | 1.28                     |
| 0.5         | Double       | 1.38                     |
| 0.6         | Linear       | 1.44                     |
| 0.6         | Double       | 1.66                     |
| 0.7         | Linear       | 1.60                     |
| 0.7         | Double       | 1.98                     |

### Data Source 3

| Load Factor | Hashing Type | Average Number of Probes |
|-------------|--------------|--------------------------|
| 0.5         | Linear       | 1.07                     |
| 0.5         | Double       | 1.06                     |
| 0.6         | Linear       | 1.08                     |
| 0.6         | Double       | 1.07                     |
| 0.7         | Linear       | 1.09                     |
| 0.7         | Double       | 1.08                     |
| 0.8         | Linear       | 1.09                     |
| 0.8         | Double       | 1.08                     |
| 0.9         | Linear       | 1.10                     |
| 0.9         | Double       | 1.09                     |

## Screenshot of HashtableExperiment Running in the Cloud

![Screenshot of HashtableExperiment](AWS%20Screenshot.png)

## Sources used

Project Specification
Class Notes


