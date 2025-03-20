/**
 * Double hashing implementation of the Hashtable.
 * Uses h(k, i) = (h1(k) + i * h2(k)) mod m where:
 * h1(k) is the primary hash function
 * h2(k) is the secondary hash function
 */
public class DoubleHashing extends Hashtable {
    
    /**
     * Constructor for DoubleHashing.
     * 
     * @param tableSize The size of the hash table
     * @param debugLevel The debug level (0, 1, or 2)
     */
    public DoubleHashing(int tableSize, int debugLevel) {
        super(tableSize, debugLevel);
    }
    
    /**
     * Find the hash index using double hashing.
     * h(k, i) = (h1(k) + i * h2(k)) mod m
     * 
     * @param key The key to hash
     * @param i The probe number
     * @return The hash index
     */
    @Override
    protected int findHashIndex(Object key, int i) {
        int h1 = key.hashCode() % tableSize;
        // The secondary hash function is h2(k) = 1 + (k mod (m - 2))
        int h2 = 1 + Math.abs(key.hashCode() % (tableSize - 2));
        return positiveMod(h1 + i * h2, tableSize);
    }
}