/**
 * TwinPrimeGenerator class generates twin primes for use in determining hash table capacity.
 * 
 * @author Anup Bhattarai
 */
public class TwinPrimeGenerator {
    
    /**
     * Generates the smallest twin prime within the given range [min, max].
     * Returns the larger of the two twin primes.
     */
    public static int generateTwinPrime(int min, int max) {
        for (int i = min; i <= max - 2; i++) {
            if (isPrime(i) && isPrime(i + 2)) {
                return i + 2;
            }
        }
        System.err.println("No twin primes found in range [" + min + ", " + max + "]");
        return max;
    }
    
    /**
     * Checks if a number is prime.
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
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        int min = 95500;
        int max = 96000;
        int twinPrime = generateTwinPrime(min, max);
        System.out.println("Found twin prime: " + twinPrime);
        System.out.println("Is " + twinPrime + " prime? " + isPrime(twinPrime));
        System.out.println("Is " + (twinPrime - 2) + " prime? " + isPrime(twinPrime - 2));
    }
}