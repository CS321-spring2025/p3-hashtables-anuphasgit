/**
 * HashObject class contains a generic key object, frequency count for duplicates, and probe count.
 * 
 * @author Anup Bhattarai
 */
public class HashObject {
    private Object key;
    private int frequency;
    private int probeCount;
    
    /**
     * Constructor to create a new HashObject with the given key.
     */
    public HashObject(Object key) {
        this.key = key;
        this.frequency = 1;
        this.probeCount = 0;
    }
    
    /**
     * Get the key of this HashObject.
     *
     */
    public Object getKey() {
        return key;
    }
    
    /**
     * Increment the frequency count when a duplicate is encountered.
     */
    public void incrementFrequency() {
        frequency++;
    }
    
    /**
     * Set the probe count for this HashObject during insertion.
     *
     */
    public void setProbeCount(int probeCount) {
        this.probeCount = probeCount;
    }
    
    /**
     * Get the probe count for this HashObject.
     *
     */
    public int getProbeCount() {
        return probeCount;
    }
    
    /**
     * Get the frequency count for this HashObject.
     *
     */
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * Compare this HashObject with another object for equality.
     * Two HashObjects are equal if their keys are equal.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        HashObject other = (HashObject) obj;
        return key.equals(other.key);
    }
    
    /**
     * Return a string representation of this HashObject including key, frequency, and probe count.
     *
     */
    @Override
    public String toString() {
        return key.toString() + " " + frequency + " " + probeCount;
    }
}