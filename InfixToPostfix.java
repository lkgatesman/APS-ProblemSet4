import java.util.*;

public class InfixToPostfix{

    public static void main(String args[]){

        Scanner in = new Scanner(System.in);

        //Read in the infix expression as one string.
        String infix = "";
        
        //Read in each character (each character is on a new line).
        while (in.hasNextLine()){
            infix += in.nextLine();
        }

        //Create postfix string that will hold the same expression in postfix notation.
        String postfix = "";
        //Initialize empty stack that will hold the operators of the expression.
        Stack<Character> operators = new Stack<Character>();

        //Iterate through the input string character by character
        for (int i = 0; i < infix.length(); i++){

            //Store the current character that we are looking at in the string.
            Character current = infix.charAt(i);

            //If the character is a digit (i.e. not an operator)
            if (Character.isDigit(current)){
                //Then add the digit to the postfix expression
                postfix += current.toString();
            }

            //If the character is an operator or a parentheses
            else if (current.equals('+') || current.equals('-') || current.equals('*') || current.equals('/') || current.equals('(') || current.equals(')')){

                //If its an opening parentheses, push it onto the operator stack
                if (current.equals('(')){
                    operators.push(current);
                    continue;
                }

                //If its a closing parentheses
                else if (current.equals(')')){
                    //If the stack of operators is empty, print error message.
                    if (operators.empty()){
                        System.out.println("No matching closing parentheses.");
                        in.close();
                        return;
                    }
                    
                    //Pop a character off the stack, and as long as that character is not the opening
                    //parentheses that matches the "current" character, keep adding characters from
                    //the stack onto the postfix expression.
                    Character next = operators.pop();
                    while (!next.equals('(')){
                        postfix += next;
                        next = operators.pop();
                    }

                }

                //Else if its not a parentheses, then it must be an operator
                else{
                    //If the operators stack is not empty, check precendence of the current
                    //top of the stack before pushing the current character onto the stack.
                    while (!operators.empty() && precedenceValue(operators.peek()) >= precedenceValue(current)){
                        postfix += operators.pop();
                    }
                    //Push the current var onto the operators stack
                    //once all of the appropriate characters have been popped off the operators stack.
                    operators.push(current);

                }


            }

            //If the character isn't classified as a digit, operator, or parentheses, print an error message.
            else{
                System.out.println("Error classifying the character.");
            }


        }

        //After we have iterated through all of the infix expression's characters and classified them,
        //pop off all the operator characters from the stack and add them to the postfix expression.
        while (!operators.empty()){
            postfix += operators.pop();
        }

        //Print out the postfix result.
        System.out.println(postfix);
        in.close();

    }

    //Function that returns the precendence value of an operator, where * and / have
    // the highest precendence and + and - have the lowest.
    //Any char that is not an operator has a precedence of -1
    static int precedenceValue(Character c){
        //Return the precedence of the character, where * and / have the highest.
        switch (c){
        case '+': return 0;
        case '-': return 0;
        case '*': return 1;
        case '/': return 1;
        }
        //Else
        return -1;
    }


}