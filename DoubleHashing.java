/**
 * DoubleHashing class extends Hashtable and implements double hashing for collision resolution.
 * 
 * @author Anup Bhattarai
 */
public class DoubleHashing extends Hashtable {
    
    /**
     * Constructor to create a new DoubleHashing hash table with given size.
     *
     * @param tableSize the size of the hash table
     */
    public DoubleHashing(int tableSize) {
        super(tableSize);
    }
    
    /**
     * Implementation of getNextProbe for double hashing.
     * Double hashing uses the formula h(k, i) = (h1(k) + i * h2(k)) mod m
     * where h1(k) = k mod m and h2(k) = 1 + (k mod (m - 2))
     *
     */
    @Override
    protected int getNextProbe(Object key, int i) {
        int h1 = positiveMod(key.hashCode(), tableSize);
        
        int h2 = 1 + positiveMod(key.hashCode(), tableSize - 2);
        
        return positiveMod(h1 + i * h2, tableSize);
    }
}