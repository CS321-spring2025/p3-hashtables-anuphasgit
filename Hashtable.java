import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * An abstract class representing a hash table with open addressing.
 * Provides common functionality for hash tables.
 */
public abstract class Hashtable {
    protected HashObject[] table;
    protected int tableSize;
    protected int numElements;
    protected int totalProbes;
    protected int duplicates;
    protected int debugLevel;
    
    /**
     * Constructor for the Hashtable.
     * 
     * @param tableSize The size of the hash table
     * @param debugLevel The debug level (0, 1, or 2)
     */
    public Hashtable(int tableSize, int debugLevel) {
        this.tableSize = tableSize;
        this.table = new HashObject[tableSize];
        this.numElements = 0;
        this.totalProbes = 0;
        this.duplicates = 0;
        this.debugLevel = debugLevel;
    }
    
    /**
     * Abstract method for finding the hash index for a key.
     * To be implemented by subclasses for different probing strategies.
     * 
     * @param key The key to find the index for
     * @param i The probe number
     * @return The hash index
     */
    protected abstract int findHashIndex(Object key, int i);
    
    /**
     * Method to convert negative modulus to positive.
     * 
     * @param dividend The dividend
     * @param divisor The divisor
     * @return The positive modulus
     */
    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }
        return quotient;
    }
    
    /**
     * Insert a HashObject into the hash table.
     * 
     * @param obj The HashObject to insert
     * @return true if inserted as a new object, false if a duplicate was found
     */
    public boolean insert(HashObject obj) {
        if (numElements >= tableSize) {
            System.err.println("Error: Hash table is full");
            return false;
        }
        
        Object key = obj.getKey();
        int probeCount = 0;
        
        for (int i = 0; i < tableSize; i++) {
            probeCount++;
            int index = findHashIndex(key, i);
            
            if (table[index] == null) {
                // Found an empty slot, insert the object
                obj.setProbeCount(probeCount);
                table[index] = obj;
                numElements++;
                totalProbes += probeCount;
                
                if (debugLevel >= 2) {
                    System.out.println("Inserted " + key + " at index " + index + 
                                       " with " + probeCount + " probes");
                }
                
                return true;
            } else if (table[index].getKey().equals(key)) {
                // Found a duplicate, increment frequency
                table[index].incrementFrequency();
                duplicates++;
                
                if (debugLevel >= 2) {
                    System.out.println("Found duplicate for " + key + 
                                       " at index " + index + ", frequency now " + 
                                       table[index].getFrequency());
                }
                
                return false;
            }
            // Slot is occupied by a different object, continue probing
        }
        
        // This should never happen if the table isn't full
        System.err.println("Error: Could not insert " + key + 
                           " after " + tableSize + " probes");
        return false;
    }
    
    /**
     * Search for a key in the hash table.
     * 
     * @param key The key to search for
     * @return The HashObject if found, null otherwise
     */
    public HashObject search(Object key) {
        int probeCount = 0;
        
        for (int i = 0; i < tableSize; i++) {
            probeCount++;
            int index = findHashIndex(key, i);
            
            if (table[index] == null) {
                // Found an empty slot, key is not in the table
                return null;
            } else if (table[index].getKey().equals(key)) {
                // Found the key
                return table[index];
            }
            // Slot is occupied by a different object, continue probing
        }
        
        // Key not found after probing the entire table
        return null;
    }
    
    /**
     * Get the number of elements in the hash table.
     * 
     * @return The number of elements
     */
    public int getNumElements() {
        return numElements;
    }
    
    /**
     * Get the load factor of the hash table.
     * 
     * @return The load factor (numElements/tableSize)
     */
    public double getLoadFactor() {
        return (double) numElements / tableSize;
    }
    
    /**
     * Get the average number of probes per successful insertion.
     * 
     * @return The average number of probes
     */
    public double getAverageProbes() {
        if (numElements == 0) {
            return 0.0;
        }
        return (double) totalProbes / numElements;
    }
    
    /**
     * Get the number of duplicates found.
     * 
     * @return The number of duplicates
     */
    public int getDuplicates() {
        return duplicates;
    }
    
    /**
     * Dump the hash table to a file in the required format.
     * Only non-null entries are included in the output.
     * 
     * @param fileName The name of the file to dump to
     */
    public void dumpToFile(String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (int i = 0; i < tableSize; i++) {
                if (table[i] != null) {
                    HashObject obj = table[i];
                    out.println("table[" + i + "]: " + obj.getKey() + " " + 
                                obj.getFrequency() + " " + obj.getProbeCount());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error dumping hash table to file: " + e.getMessage());
        }
    }
    
    /**
     * Get a string representation of the hash table.
     * 
     * @return A string representation of the hash table
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hash Table Type: ").append(this.getClass().getSimpleName()).append("\n");
        sb.append("Table Size: ").append(tableSize).append("\n");
        sb.append("Number of Elements: ").append(numElements).append("\n");
        sb.append("Load Factor: ").append(getLoadFactor()).append("\n");
        sb.append("Average Probes: ").append(getAverageProbes()).append("\n");
        sb.append("Duplicates: ").append(duplicates).append("\n");
        return sb.toString();
    }
}