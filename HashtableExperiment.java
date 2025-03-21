import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Driver program for hash table experiments.
 * Compares linear probing and double hashing for various data sources and load factors.
 * 
 * @author Anup Bhattarai
 */
public class HashtableExperiment {
    private static final String WORD_LIST_FILE = "word-list.txt";
    private static int dataSource;
    private static double loadFactor;
    private static int debugLevel;
    
    /**
     * Main method to run the hash table experiments.
    */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            printUsage();
            System.exit(1);
        }

        debugLevel = 0;
        
        try {
            dataSource = Integer.parseInt(args[0]);
            loadFactor = Double.parseDouble(args[1]);
            
            if (args.length == 3) {
                debugLevel = Integer.parseInt(args[2]);
            }
            
            if (dataSource < 1 || dataSource > 3) {
                System.err.println("Error: dataSource must be 1, 2, or 3");
                printUsage();
                System.exit(1);
            }
            
            if (loadFactor <= 0 || loadFactor >= 1) {
                System.err.println("Error: loadFactor must be between 0 and 1");
                printUsage();
                System.exit(1);
            }
            
            if (debugLevel < 0 || debugLevel > 2) {
                System.err.println("Error: debugLevel must be 0, 1, or 2");
                printUsage();
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing command-line arguments: " + e.getMessage());
            printUsage();
            System.exit(1);
        }
        
        int tableSize = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + tableSize);
        
        int numElements = (int) Math.ceil(loadFactor * tableSize);
        
        String inputDescription = getInputDescription(dataSource);
        System.out.println("HashtableExperiment: Input: " + inputDescription + "   Loadfactor: " + String.format("%.2f", loadFactor));
        System.out.println();
        
        List<Object> data = generateData(dataSource, numElements);
        
        System.out.println("\tUsing Linear Probing");
        runExperiment(new LinearProbing(tableSize), data, numElements, debugLevel, "linear-dump.txt");
        
        System.out.println();
        
        System.out.println("\tUsing Double Hashing");
        runExperiment(new DoubleHashing(tableSize), data, numElements, debugLevel, "double-dump.txt");
    }
    
    /**
     * Print usage information for the program.
     */
    private static void printUsage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println(" <dataSource>: 1 ==> random numbers");
        System.out.println("              2 ==> date value as a long");
        System.out.println("              3 ==> word list");
        System.out.println(" <loadFactor>: The ratio of objects to table size, denoted by alpha = n/m");
        System.out.println(" <debugLevel>: 0 ==> print summary of experiment (default)");
        System.out.println("               1 ==> save the two hash tables to a file at the end");
        System.out.println("               2 ==> print debugging output for each insert");
    }
    
    /**
     * Get a description of the input source.
     * 
     * @param dataSource the data source identifier
     * @return description of the data source
     */
    private static String getInputDescription(int dataSource) {
        switch (dataSource) {
            case 1: return "Random-Numbers";
            case 2: return "Date-Time";
            case 3: return "Word-List";
            default: return "Unknown";
        }
    }
    
    /**
     * Generate data based on the specified source.
     */
    private static List<Object> generateData(int dataSource, int numElements) {
        List<Object> data = new ArrayList<>();
        
        switch (dataSource) {
            case 1:
                Random rand = new Random();
                for (int i = 0; i < numElements * 2; i++) {
                    data.add(rand.nextInt());
                }
                break;
                
            case 2:
                long current = new Date().getTime();
                for (int i = 0; i < numElements * 2; i++) {
                    data.add(new Date(current));
                    current += 1000;
                }
                break;
                
            case 3:
                try (BufferedReader reader = new BufferedReader(new FileReader(WORD_LIST_FILE))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        data.add(line);
                        if (data.size() >= numElements * 3) { 
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading word list file: " + e.getMessage());
                    System.exit(1);
                }
                break;
        }
        
        return data;
    }
    
    /**
     * Run the experiment with the specified hash table and data.
     */
    private static void runExperiment(Hashtable hashTable, List<Object> data, int numElements, int debugLevel, String dumpFileName) {
        int insertedCount = 0;
        int totalElements = 0;
        
        System.out.println("HashtableExperiment: size of hash table is " + numElements);
        
        for (Object obj : data) {
            HashObject hashObj = new HashObject(obj);
            boolean inserted = hashTable.insert(hashObj);
            
            if (debugLevel == 2) {
                if (inserted) {
                    System.out.println("Inserted " + obj + " at position " + 
                            " with " + hashObj.getProbeCount() + " probes");
                } else {
                    System.out.println("Found duplicate of " + obj);
                }
            }
            
            totalElements++;
            if (inserted) {
                insertedCount++;
                if (insertedCount >= numElements) {
                    break;
                }
            }
        }
        
        System.out.println("\tInserted " + totalElements + " elements, of which " + 
                hashTable.getDuplicates() + " were duplicates");
        System.out.println("\tAvg. no. of probes = " + String.format("%.2f", hashTable.getAverageProbes()) + " ");
        
        if (debugLevel >= 1) {
            hashTable.dumpToFile(dumpFileName);
            System.out.println("HashtableExperiment: Saved dump of hash table");
        }
    }
}