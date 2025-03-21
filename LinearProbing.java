/**
 * LinearProbing class extends Hashtable and implements linear probing for collision resolution.
 * 
 * @author Anup Bhattarai
 */
public class LinearProbing extends Hashtable {
    
    /**
     * Constructor to create a new LinearProbing hash table with given size.
     */
    public LinearProbing(int tableSize) {
        super(tableSize);
    }
    
    /**
     * Implementation of getNextProbe for linear probing.
     * Linear probing uses the formula h(k, i) = (h1(k) + i) mod m
     * where h1(k) = k mod m
     */
    @Override
    protected int getNextProbe(Object key, int i) {
        int h1 = positiveMod(key.hashCode(), tableSize);
        return positiveMod(h1 + i, tableSize);
    }
}