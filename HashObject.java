/**
 * A class representing objects to be stored in a hash table.
 * Contains a key object, frequency count for duplicates, and probe count.
 */
public class HashObject {
    private Object key;
    private int frequency;
    private int probeCount;
    
    /**
     * Constructor for HashObject.
     * 
     * @param key The key object for this HashObject
     */
    public HashObject(Object key) {
        this.key = key;
        this.frequency = 1;
        this.probeCount = 0;
    }
    
    /**
     * Get the key object.
     * 
     * @return The key object
     */
    public Object getKey() {
        return key;
    }
    
    /**
     * Get the frequency count.
     * 
     * @return The frequency count
     */
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * Increment the frequency count when a duplicate is found.
     */
    public void incrementFrequency() {
        frequency++;
    }
    
    /**
     * Get the probe count.
     * 
     * @return The probe count
     */
    public int getProbeCount() {
        return probeCount;
    }
    
    /**
     * Set the probe count.
     * 
     * @param probeCount The probe count to set
     */
    public void setProbeCount(int probeCount) {
        this.probeCount = probeCount;
    }
    
    /**
     * Returns a string representation of this HashObject.
     * 
     * @return A string representation of this HashObject
     */
    @Override
    public String toString() {
        return key + " " + frequency + " " + probeCount;
    }
}