import java.io.PrintWriter;
import java.io.FileNotFoundException;

/**
 * Abstract Hashtable class that provides common functionality for hash tables.
 * Concrete subclasses must implement specific probing strategies.
 * 
 * @author Anup Bhattarai
 */
public abstract class Hashtable {
    protected HashObject[] table;
    protected int tableSize;
    protected int size;
    protected int totalProbes;
    protected int totalInsertions;
    protected int duplicates;
    
    /**
     * Constructor to create a new Hashtable with given size.
     *
     */
    public Hashtable(int tableSize) {
        this.tableSize = tableSize;
        table = new HashObject[tableSize];
        size = 0;
        totalProbes = 0;
        totalInsertions = 0;
        duplicates = 0;
    }
    
    /**
     * Helper method to ensure mod operations always return positive values.
     *
     */
    protected int positiveMod(int dividend, int divisor) {
        int quotient = dividend % divisor;
        if (quotient < 0) {
            quotient += divisor;
        }
        return quotient;
    }
    
    /**
     * Abstract method to get the next probe position.
     * Must be implemented by subclasses according to their probing strategy.
     *
     */
    protected abstract int getNextProbe(Object key, int i);
    
    /**
     * Insert a HashObject into the hash table.
     * If a duplicate is found, increment its frequency instead of inserting.
     */
    public boolean insert(HashObject obj) {
        if (size >= tableSize) {
            System.err.println("Error: Hash table is full");
            return false;
        }
        
        Object key = obj.getKey();
        int probeCount = 0;
        
        for (int i = 0; i < tableSize; i++) {
            probeCount++;
            int pos = getNextProbe(key, i);
            
            if (table[pos] == null) {
                obj.setProbeCount(probeCount);
                table[pos] = obj;
                size++;
                totalProbes += probeCount;
                totalInsertions++;
                return true;
            }
            
            if (table[pos].getKey().equals(key)) {
                table[pos].incrementFrequency();
                duplicates++;
                return false;
            }
        }
        
        System.err.println("Error: Could not insert, hash table may be full");
        return false;
    }
    
    /**
     * Search for a key in the hash table.
     */
    public HashObject search(Object key) {
        for (int i = 0; i < tableSize; i++) {
            int pos = getNextProbe(key, i);
            
            if (table[pos] == null) {
                return null;
            }
            
            if (table[pos].getKey().equals(key)) {
                return table[pos];
            }
        }
        
        return null;
    }
    
    /**
     * Get the average number of probes for successful insertions.
     *
     */
    public double getAverageProbes() {
        if (totalInsertions == 0) {
            return 0.0;
        }
        return (double) totalProbes / totalInsertions;
    }
    
    /**
     * Get the number of elements in the hash table.
     *
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Get the number of duplicates encountered.
     *
     */
    public int getDuplicates() {
        return duplicates;
    }
    
    /**
     * Get the total number of successful insertions.
     *
     */
    public int getTotalInsertions() {
        return totalInsertions;
    }
    
    /**
     * Dump the contents of the hash table to a file.
     * Only non-null entries are written.
     */
    public void dumpToFile(String fileName) {
        try {
            PrintWriter out = new PrintWriter(fileName);
            for (int i = 0; i < tableSize; i++) {
                if (table[i] != null) {
                    out.println("table[" + i + "]: " + table[i]);
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not write to file " + fileName);
            e.printStackTrace();
        }
    }
}