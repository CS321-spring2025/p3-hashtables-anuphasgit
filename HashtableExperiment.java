import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class HashtableExperiment {
    private static final double[] LOAD_FACTORS = {0.5, 0.6, 0.7, 0.8, 0.9, 0.95, 0.99};
    private static final String[] DATA_SOURCE_NAMES = {"Random Numbers", "Date Values", "Word List"};
    
    private static int tableSize;
    private static int dataSource;
    private static double loadFactor;
    private static int debugLevel;
    
    /**
     * Main method to run the hash table experiment.
     * 
     * @param args Command line arguments:
     *        args[0]: Data source (1=random, 2=date, 3=word list)
     *        args[1]: Load factor (ratio of objects to table size)
     *        args[2]: Debug level (0=summary, 1=save to file, 2=debug output)
     */
    public static void main(String[] args) {
        if (!parseCommandLineArgs(args)) {
            printUsage();
            return;
        }
        
        // Generate twin prime for table size
        tableSize = TwinPrimeGenerator.generateTwinPrime(95500, 96000);
        System.out.println("Table size (twin prime): " + tableSize);
        
        // Calculate number of elements needed to reach desired load factor
        int numElementsNeeded = (int) Math.ceil(loadFactor * tableSize);
        System.out.println("Target load factor: " + loadFactor);
        System.out.println("Number of elements needed: " + numElementsNeeded);
        System.out.println("Data source: " + DATA_SOURCE_NAMES[dataSource - 1]);
        System.out.println("Debug level: " + debugLevel);
        System.out.println();
        
        // Create hash tables
        LinearProbing linearProbingTable = new LinearProbing(tableSize, debugLevel);
        DoubleHashing doubleHashingTable = new DoubleHashing(tableSize, debugLevel);
        
        // Perform experiment
        performExperiment(linearProbingTable, doubleHashingTable, numElementsNeeded);
        
        // Print results
        printResults(linearProbingTable, doubleHashingTable);
        
        // Save hash tables to files if debug level is 1 or higher
        if (debugLevel >= 1) {
            linearProbingTable.dumpToFile("linear-dump.txt");
            doubleHashingTable.dumpToFile("double-dump.txt");
            System.out.println("Hash tables saved to files linear-dump.txt and double-dump.txt");
        }
    }
    
    /**
     * Parse and validate command line arguments.
     * 
     * @param args Command line arguments
     * @return true if arguments are valid, false otherwise
     */
    private static boolean parseCommandLineArgs(String[] args) {
        if (args.length < 2 || args.length > 3) {
            return false;
        }
        
        try {
            dataSource = Integer.parseInt(args[0]);
            if (dataSource < 1 || dataSource > 3) {
                System.err.println("Error: Data source must be 1, 2, or 3");
                return false;
            }
            
            loadFactor = Double.parseDouble(args[1]);
            if (loadFactor <= 0.0 || loadFactor >= 1.0) {
                System.err.println("Error: Load factor must be between 0 and 1");
                return false;
            }
            
            debugLevel = (args.length == 3) ? Integer.parseInt(args[2]) : 0;
            if (debugLevel < 0 || debugLevel > 2) {
                System.err.println("Error: Debug level must be 0, 1, or 2");
                return false;
            }
            
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Print usage information.
     */
    private static void printUsage() {
        System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
        System.out.println(" <dataSource>: 1 ==> random numbers");
        System.out.println("              2 ==> date value as a long");
        System.out.println("              3 ==> word list");
        System.out.println(" <loadFactor>: The ratio of objects to table size, denoted by alpha = n/m");
        System.out.println(" <debugLevel>: 0 ==> print summary of experiment");
        System.out.println("              1 ==> save the two hash tables to a file at the end");
        System.out.println("              2 ==> print debugging output for each insert");
    }
    
    /**
     * Perform the hash table experiment by inserting objects into both hash tables.
     * 
     * @param linearProbingTable The linear probing hash table
     * @param doubleHashingTable The double hashing hash table
     * @param numElementsNeeded The number of elements to insert
     */

    private static void performExperiment(LinearProbing linearProbingTable, 
                                     DoubleHashing doubleHashingTable, 
                                     int numElementsNeeded) {
    List<Object> data = new ArrayList<>();
    Random random = new Random(42);
    
    System.out.println("Inserting into Linear Probing table...");
    while (linearProbingTable.getNumElements() < numElementsNeeded) {
        // Generate more data as needed
        if (data.size() < 10000) { // Keep a buffer of data
            List<Object> moreData = generateData(100000);
            data.addAll(moreData);
        }
        
        // Insert data
        Object keyObj = data.remove(0); // Take from the front
        HashObject obj = new HashObject(keyObj);
        linearProbingTable.insert(obj);
    }
    
    // Reset data for double hashing
    data = new ArrayList<>();
    
    System.out.println("Inserting into Double Hashing table...");
    while (doubleHashingTable.getNumElements() < numElementsNeeded) {
        // Generate more data as needed
        if (data.size() < 10000) {
            List<Object> moreData = generateData(100000);
            data.addAll(moreData);
        }
        
        // Insert data
        Object keyObj = data.remove(0);
        HashObject obj = new HashObject(keyObj);
        doubleHashingTable.insert(obj);
    }
}
    
    /**
     * Generate data based on the selected data source.
     * 
     * @param size The number of data items to generate
     * @return A list of data items
     */
    private static List<Object> generateData(int size) {
        List<Object> data = new ArrayList<>(size);
        Random random = new Random(42); // Using a fixed seed for reproducibility
        
        switch (dataSource) {
            case 1: // Random integers
                for (int i = 0; i < size; i++) {
                    data.add(Integer.valueOf(random.nextInt(1000000)));
                }
                break;
                
            case 2: // Date values
                long current = new Date().getTime();
                for (int i = 0; i < size; i++) {
                    Date date = new Date(current);
                    data.add(date);
                    current += 1000; // Increase by 1 second (1000 ms)
                }
                break;
                
            case 3: // Word list
                try {
                    List<String> wordList = readWordList("word-list.txt");
                    if (wordList.isEmpty()) {
                        throw new IOException("Word list is empty");
                    }
                    
                    // If word list is smaller than size, use words cyclically
                    int wordListSize = wordList.size();
                    for (int i = 0; i < size; i++) {
                        data.add(wordList.get(i % wordListSize));
                    }
                } catch (IOException e) {
                    System.err.println("Error reading word list: " + e.getMessage());
                    System.err.println("Falling back to random strings");
                    
                    // Fallback to random strings
                    for (int i = 0; i < size; i++) {
                        data.add(generateRandomString(random, 5 + random.nextInt(10)));
                    }
                }
                break;
        }
        
        return data;
    }
    
    /**
     * Read a word list from a file.
     * 
     * @param filename The name of the file to read
     * @return A list of words
     * @throws IOException If an I/O error occurs
     */
    private static List<String> readWordList(String filename) throws IOException {
        List<String> wordList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    wordList.add(line.trim());
                }
            }
        }
        
        return wordList;
    }
    
    /**
     * Generate a random string of the specified length.
     * 
     * @param random The random number generator
     * @param length The length of the string
     * @return A random string
     */
    private static String generateRandomString(Random random, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
    
    /**
     * Insert data into a hash table until it reaches the target number of elements.
     * 
     * @param table The hash table to insert into
     * @param data The data to insert
     * @param targetElements The target number of elements
     */
    private static void insertData(Hashtable table, List<Object> data, int targetElements) {
        int dataIndex = 0;
        
        while (table.getNumElements() < targetElements && dataIndex < data.size()) {
            Object keyObj = data.get(dataIndex++);
            HashObject obj = new HashObject(keyObj);
            table.insert(obj);
        }
        
        if (table.getNumElements() < targetElements) {
            System.out.println("Warning: Could not reach target load factor. " +
                              "Inserted " + table.getNumElements() + " out of " +
                              targetElements + " elements.");
        }
    }
    
    /**
     * Print the results of the experiment.
     * 
     * @param linearProbingTable The linear probing hash table
     * @param doubleHashingTable The double hashing hash table
     */
    private static void printResults(LinearProbing linearProbingTable, DoubleHashing doubleHashingTable) {
        System.out.println("HashtableExperiment: Found a twin prime table capacity: " + tableSize);
        System.out.println("HashtableExperiment: Input: Word-List   Loadfactor: " + 
                          String.format("%.2f", loadFactor));
        
        // Linear Probing results
        System.out.println("        Using Linear Probing");
        System.out.println("HashtableExperiment: size of hash table is " + linearProbingTable.getNumElements());
        int totalInserts = linearProbingTable.getNumElements() + linearProbingTable.getDuplicates();
        System.out.println("        Inserted " + totalInserts + " elements, of which " + 
                          linearProbingTable.getDuplicates() + " were duplicates");
        System.out.println("        Avg. no. of probes = " + 
                          String.format("%.2f", linearProbingTable.getAverageProbes()));
        
        // Double Hashing results
        System.out.println("        Using Double Hashing");
        System.out.println("HashtableExperiment: size of hash table is " + doubleHashingTable.getNumElements());
        totalInserts = doubleHashingTable.getNumElements() + doubleHashingTable.getDuplicates();
        System.out.println("        Inserted " + totalInserts + " elements, of which " + 
                          doubleHashingTable.getDuplicates() + " were duplicates");
        System.out.println("        Avg. no. of probes = " + 
                          String.format("%.2f", doubleHashingTable.getAverageProbes()));
    }
}