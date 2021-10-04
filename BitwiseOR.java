//I worked on planning this algorithm with Brian Kim from our class.

import java.util.Scanner;

public class BitwiseOR {

    public static void main(String args[]){

        //Scanner to read in standard input.
        Scanner in = new Scanner(System.in);

        //Read in the input values n, l, and r
        long n = in.nextLong();
        long l = in.nextLong();
        long r = in.nextLong();

        in.close(); //Close the scanner.

        char[] nBits = new char[32];    //Array of chars representing the bits for number n.
        char[] lBits = new char[32];    //Array of chars representing the bits for number l.
        char[] rBits = new char[32];    //Array of chars representing the bits for number r.

        //For n, insert all bits into the array of chars that represent the bits.
        for (int k=31; k>=0; k--){
            if ((n & (1 << k)) != 0){
                nBits[31-k]='1';
            }
            else{
                nBits[31-k]='0';
            }
        }

        //For l, insert all bits into the array of chars that represent the bits.
        for (int k=31; k>=0; k--){
            if ((l & (1<<k)) != 0){
                lBits[31-k]='1';
            }
            else{
                lBits[31-k]='0';
            }
        }

        //For r, insert all bits into the array of chars that represent the bits.
        for (int k=31; k>=0; k--){
            if ((r & (1<<k)) != 0){
                rBits[31-k]='1';
            }
            else{
                rBits[31-k]='0';
            }
        }

        //System.out.println("nBits: " + Arrays.toString(nBits));
        //System.out.println("lBits: " + Arrays.toString(lBits));
        //System.out.println("rBits: " + Arrays.toString(rBits));

        String result = "";   //String that holds the result number.

        //Boolean to hold whether current value is less than L or not (since L is lower bound)
        boolean lessThanL = true;

        //Boolean to hold whether we encountered a 1 or not
        boolean xZeroAndROne = false;

        //For each bit in the three numbers...
        for (int i = 0; i < 32; i++){
            //1. Set the X bits as long as l and r are different
            //If the current bit of l and r are different, we set less than L equal to false
            if (lBits[i] != rBits[i]){
                lessThanL = false;
            }
            //If we are less than L, then we add the lBit to the result.
            if (lessThanL){
                result += lBits[i];
            }

            /*2. Once we have set the X bits while l and r were different,
                then we know the x is at least equal to L. So now we focus
                on the bits of r and n.
            */
            else {  
                //If the nBit is 0 and before x was 0 and r was 1, then we give x an "on" bit
                //(We want the x bit to be 1 when n is 0, so we can maximize x)
                if (nBits[i] == '0'){
                    if (xZeroAndROne) result += '1';    //X was 0 and R was 1, so we give X a 1
                    else result += rBits[i];    //Else, we give x the current bit of R
                }

                //But we want x to be 0 when the n bit is 1
                else {
                    //We give x an off bit
                    result += '0'; 
                    //If R has an on bit, then we set the x was zero and R was one boolean to true!
                    if (rBits[i]=='1'){
                        xZeroAndROne = true;
                    }
                }
            }
        }

        //System.out.println(result);
        
        //Convert the binary string of x to a long base 10 of x.
        long number = Long.parseLong(result, 2);

        //Print the value of x.
        System.out.println(number);

    }

    
}
