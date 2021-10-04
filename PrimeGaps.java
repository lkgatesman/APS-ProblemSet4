//Source used for finding primes within a specific range, with specific factors:
//https://www.geeksforgeeks.org/segmented-sieve-print-primes-in-a-range/

//Source used for Sieve of Eratosthenes:
//https://www.geeksforgeeks.org/sieve-of-eratosthenes/

//Source used for Prime Numbers check:
//https://www.geeksforgeeks.org/prime-numbers/

//I worked on planning this algorithm with Brian Kim from our class.


import java.util.*;
import java.lang.Math;

public class PrimeGaps {

    public static void main(String[] args) {
                
        Scanner in = new Scanner(System.in);

        int a = in.nextInt();
        int b = in.nextInt();

        if (a == 0 || b == 0 || a == b || (b - a > 1000000) || (a > Integer.MAX_VALUE) || (b > Integer.MAX_VALUE)){
            System.out.println("-1");
            in.close();
            return;
        }

        //Find all prime factors between 0 and the square root of b.
        Double n = Math.sqrt((double)b);
        boolean[] primeNums = sieveOfEratosthenes(n);

        //All of the prime factors between 0 and square root of b.
        List<Integer> primeFactors = new ArrayList<Integer>();
        //For each number between 0 and the square root of b, if it's prime, add it
        //to the list of prime factors.
        for (int i = 2; i <= n; i++){
            if (primeNums[i]){
                primeFactors.add(i);
            }
        }

        //List of the primes in range a to b
        List<Integer> primesList = new ArrayList<>();

        //Generate a list of all the prime numbers between a and b, using the list primeFactors that we already have.
        findPrimesInRange(primeFactors, primesList, a, b);

        //If the list of primes is less than 2, there isn't enough to generate prime gaps.
        if(primesList.size() < 2) {
            System.out.println("-1");
            in.close();
            return;
        } 

        if (primesList.size() == 2 && !isPrime(primesList.get(0)) || !isPrime(primesList.get(1))){
            System.out.println("-1");
            in.close();
            return;
        }

        //Find the prime gaps for all the primes between a and b, and print them out.
        findPrimeGaps(primesList);

        in.close(); //Close scanner.
    }

    public static void findPrimeGaps(List<Integer> primesList){

        if (primesList.contains(1)){
            int index = primesList.indexOf(1);
            primesList.remove(index);
        }

        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int p4 = 0;
        int min = 1000000;
        int max = -1;

        //For each two numbers in the list of primes
        for(int i = 1 ; i < primesList.size(); i++) {
            int numOne = primesList.get(i);
            int numTwo = primesList.get(i-1);
            //Find their difference.
            int diff = numOne-numTwo;
            //If the minimum is greater than their difference, update the minimum, and p1 and p2.
            if(min > diff) {
                min = diff;
                p1 = numTwo;
                p2 = numOne;
            }
            //If the maximum is less than their difference, update the maximum, and p3 and p4.
            if(max < diff) {
                max = diff;
                p3 = numTwo;
                p4 = numOne;
            }
        }

        System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);

    }

    //Algorithm to find prime numbers between 0 and n as fast as possible.
    public static boolean[] sieveOfEratosthenes(Double num)
    {

        int n = num.intValue();

        // Boolean array to hold which are prime numbers. 
        // If a number i is prime, then prime[i] = true. Else, prime[i] = false.
        boolean prime[] = new boolean[n+1];

        for(int i = 0; i <= n; i++){
            prime[i] = true;
        }

        for(int p = 2; p*p <= n; p++)
        {
            // If prime[p] is not changed, then it is a prime
            if(prime[p] == true)
            {
                // Update all multiples of p
                for(int i = p*p; i <= n; i += p)
                    prime[i] = false;
            }
        }
         
        return prime;
    }

    // in segmented sieve we check for prime from range [low, high]
    public static void findPrimesInRange(List<Integer> primeFactors, List<Integer> primesList, int low, int high)
    {
        //Array to hold all the primes between low and high (a and b)
        boolean[] prime = new boolean [high-low+1];
        Arrays.fill(prime,true);

        //For each factor, determine which numbers in the range are divisible by it.
        for(int i : primeFactors){

            int lower = (low / i);
            
            if(lower <= 1){
                lower= i + i;
            }
            else if(low % i != 0){
                lower = (lower * i) + i;
            }
            else{
                lower = (lower * i);
            }
            for(int j = lower; j <= high; j = j + i)
            {
                if (j-low >= prime.length){
                    break;
                }
                else{
                    prime[j-low]=false;
                }
            }
        }

        //For each number in the range, if it's prime, add it to the list of primes between a and b.
        for(int i = low; i <= high; i++)
        {

            if (i-low >= prime.length){
                break;
            }

            if(prime[i-low]==true){
                primesList.add(i);
            }
        }    

    }

    // Check for number prime or not
    static boolean isPrime(int n)
    {
 
        // Check if number is less than
        // equal to 1
        if (n <= 1)
            return false;
 
        // Check if number is 2
        else if (n == 2)
            return true;
 
        // Check if n is a multiple of 2
        else if (n % 2 == 0)
            return false;
 
        // If not, then just check the odds
        for (int i = 3; i <= Math.sqrt(n); i += 2)
        {
            if (n % i == 0)
                return false;
        }
        return true;
    }

}

