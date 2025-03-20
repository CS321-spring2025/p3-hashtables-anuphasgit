/**
 * This class is designed to find the twin prime numbers between a given range.
 */
public class TwinPrimeGenerator {
    /**
     * Generate a twin prime number between min and max.
     * Returns the larger of the twin prime pair.
     * 
     * @param min The minimum value to consider
     * @param max The maximum value to consider
     * @return The larger of a twin prime pair, or -1 if no twin prime is found
     */
    public static int generateTwinPrime(int min, int max) {
        // Minimum value should be at least 3 because the smallest twin prime is (3,5)
        if (min < 3) {
            min = 3;
        }
        
        // Twin prime number are always odd
        if (min % 2 == 0) {
            min++;
        }
        
        // Start iterating from min and only check odd numbers
        for (int i = min; i <= max - 2; i += 2) {
            if (isPrime(i) && isPrime(i + 2)) {
                // Found a twin prime, return the larger number
                return i + 2;
            }
        }
        
        return -1; // No twin prime found
    }
    
    /**
     * Helper method to check if a number is prime.
     * 
     * @param n The number to check
     * @return true if n is prime, false otherwise
     */
    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        
        if (n <= 3) {
            return true;
        }
        
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        
        int i = 5;
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        
        return true;
    }
}