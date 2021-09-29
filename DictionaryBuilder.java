import java.util.*;
import java.util.regex.*;

public class DictionaryBuilder{

    public static void main(String args[]){

        Scanner in = new Scanner(System.in);

        //List that will hold each unique word.
        List myList = new ArrayList();

        while (in.hasNextLine()){

            String input = in.nextLine();

            //If the input string is empty, continue to the next iteration of the while loop.
            if (input.equals("")){
                continue;
            }

            //Call the recursive function that finds all words in the input string.
            findAllWords(input, myList);

        }

        //Sort the list of words lexicographically.
        Collections.sort(myList);

        //Print out each word from the list, one word per line.
        for (int i = 0; i < myList.size(); i++){
            System.out.println(myList.get(i));
        }

        in.close();
    }

    public static void findAllWords(String s, List myList){

        //Create a pattern matcher to find only alphabetical characters.
        Pattern matcher = Pattern.compile("[a-zA-Z]+");

        //Convert all characters in the input string to lower case.
        String string = s.toLowerCase();
        
        //Create a pattern matcher for the input string.
        Matcher m1 = matcher.matcher(string);
          
        //Each time a word is found, and it's not in the list already, add it to the list.
        while (m1.find()) {
            String current = m1.group();
            if (!myList.contains(current)){
                myList.add(current);
            }
        }

    }
    
}
