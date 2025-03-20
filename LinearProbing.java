/**
 * Linear probing implementation of the Hashtable.
 * Uses h(k, i) = (h(k) + i) mod m where h(k) is the primary hash function.
 */
public class LinearProbing extends Hashtable {
    
    /**
     * Constructor for LinearProbing.
     * 
     * @param tableSize The size of the hash table
     * @param debugLevel The debug level (0, 1, or 2)
     */
    public LinearProbing(int tableSize, int debugLevel) {
        super(tableSize, debugLevel);
    }
    
    /**
     * Find the hash index using linear probing.
     * h(k, i) = (h(k) + i) mod m
     * 
     * @param key The key to hash
     * @param i The probe number
     * @return The hash index
     */
    @Override
    protected int findHashIndex(Object key, int i) {
        int h1 = key.hashCode() % tableSize;
        return positiveMod(h1 + i, tableSize);
    }
}