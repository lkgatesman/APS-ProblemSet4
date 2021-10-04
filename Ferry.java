//I worked on planning this algorithm with Brian Kim from our class.

import java.util.Scanner;
import java.util.PriorityQueue;

public class Ferry{

    static int ferryCapacity;       //How many cars the ferry can hold at once.
    static int ferryTime;   //How long it takes the ferry to cross the river.
    static int numCars;     //How many cars will be transported total.  
    static int timer;   //Variable that keeps track of the current time.
    static int carsTransported; //How many cars have been transported across the river at any given time.

    public static void main(String args[]){

        Scanner in = new Scanner(System.in);

        carsTransported = 0;    //initial number of cars transported is zero

        ferryCapacity = in.nextInt();   //n
        ferryTime = in.nextInt();  //t
        numCars = in.nextInt(); //m
        timer = 0;  //initial time is 0

        //Read out the line break
        in.nextLine();

        PriorityQueue<Car> left = new PriorityQueue<Car>(); //Simulates the left side of the river.
        PriorityQueue<Car> right = new PriorityQueue<Car>();    //Simulates the right side of the river.

        Car[] ferry = new Car[ferryCapacity];   //Array that will represent the ferry.
        Car[] cars = new Car[numCars];  //Array that will hold each car in the order it was received via input.

        //For each car, read in a line and store its information properly.
        for (int i = 0; i < numCars; i++){

            String input = in.nextLine();   //Line of input read in
            String[] clean = input.split(" ");  //Parsing the line of input by spaces

            //If the input line has more than 2 components, something is WRONG!!
            if (clean.length != 2){
                System.out.println("Error reading line of input.");
            }

            //Create temp car object, which assigns its arrivalTime, arrivalSide, and index.
            Car temp = new Car(Integer.parseInt(clean[0]), clean[1], i);

            //Load it into the array of cars
            cars[i] = temp;

            //Assign the car to the appropriate queue.
            if (clean[1].equals("left")){
                left.add(temp);
            }
            else if (clean[1].equals("right")){
                right.add(temp);
            }
            //If it's not left or right, something is WRONG!!
            else{
                System.out.println("Error reading input.");
            }

        }

        //While the number of cars that have been transported is less than the
        //total number of cars...
        while (carsTransported < numCars){
            
            //Unload the ferry (use unload function)
            unload(ferry);

            //Load cars up from left side (if possible)
            leftSide(ferry, left, right);

            //Increase the timer, because now it's going across the river...
            timer += ferryTime;
                
            //Unload the ferry (use unload function)
            unload(ferry);

            //Load cars up from right side (if possible)
            rightSide(ferry, left, right);
            
            //Increase the timer, because now it's going back across the river...
            timer += ferryTime;

        }

        //Iterate through the cars in the order they were received
        //via the input, and output their unloading times.
        for (int i = 0; i < numCars; i++){
            System.out.println(cars[i].unloadTime);
        }

        //Close the scanner.
        in.close();

    }

    //Method that takes care of loading cars on the left side (if possible/needed)
    public static void leftSide(Car[] ferry, PriorityQueue<Car> left, PriorityQueue<Car> right){

        //If the left queue is empty, exit the function.
        if (left.isEmpty()){
            return;
        }

        //If the left queue has no cars at it currently..
        //[that is, the arrivalTime of the next car in the queue is AFTER the current time]
        if (left.peek().arrivalTime > timer){

            //And the rigt queue will get another car before the left queue does,
            // then move to the right side, and adjust the timer accordingly.
            //NOTE: We only move to the right side once the car has arrived on the right side.
            if ((!right.isEmpty()) && right.peek().arrivalTime < left.peek().arrivalTime){
                int difference = right.peek().arrivalTime - timer;
                timer += difference;
                return;
            }

            //But, if the left side will receive a car before the right side, 
            //continue counting until the left side "gets" its car
            else{
                while (left.peek().arrivalTime > timer){
                    timer += 1;
                }
            }

        }
        
        //Load up the ferry with cars from the left side.
        for (int i = 0; i < ferry.length; i++){
            if (!left.isEmpty() && left.peek().arrivalTime <= timer){
                ferry[i] = left.poll();
            }
        }

        return;

    }

    //Method that takes care of loading cars on the right side (if possible/needed)
    public static void rightSide(Car[] ferry, PriorityQueue<Car> left, PriorityQueue<Car> right){

        //If the right queue is empty, exit the function.
        if (right.isEmpty()){
            return;
        }

        //If the right queue has no cars at it currently..
        //[that is, the arrivalTime of the next car in the queue is AFTER the current time]
        if (right.peek().arrivalTime > timer){

            //And the left queue will get another car before the right queue does,
            // then move to the left side, and adjust the timer accordingly.
            //NOTE: We only move to the left side once the car has arrived on the left side.
            if ((!left.isEmpty()) && left.peek().arrivalTime < right.peek().arrivalTime){
                int difference = left.peek().arrivalTime - timer;
                timer += difference;
                return;
            }

            //But, if the right side will receive a car before the left side, 
            //continue counting until the right side "gets" its car
            else{
                while (right.peek().arrivalTime > timer){
                    timer += 1;
                }
            }

        }

        //Load up the ferry with cars from the right side.
        for (int i = 0; i < ferry.length; i++){
            if (!right.isEmpty() && right.peek().arrivalTime <= timer){
                ferry[i] = right.poll();
            }
        }

        return;

    }

    //Method that handles unloading the ferry and setting the unload time of each car.
    public static void unload(Car[] ferry){

        for (int i = 0; i < ferry.length; i++){

            //If this element of the ferry exists (i.e. if the ferry is null)
            if (ferry[i] != null){

                //Set the car's arrival time.
                ferry[i].unloadTime = timer;

                //"Remove" the car from the ferry.
                ferry[i] = null;

                //Increase the number of cars that have been transported by 1.
                carsTransported += 1;

            }

        }

    }

}

//User-defined object of a car.
class Car implements Comparable<Car>{

    int arrivalTime;
    String arrivalSide;
    int unloadTime;
    int index;

    //Constructor
    public Car(int arrivalTime, String arrivalSide, int index){

        this.arrivalTime = arrivalTime;
        this.arrivalSide = arrivalSide;
        this.unloadTime = -1;
        this.index = index;

    }

    //Required comparable function
    //Used by the PriorityQueue to keep the cars in order of index.
    @Override
    public int compareTo(Car car) {
        if(this.index > car.index) {
            return 1;
        } else if (this.index < car.index) {
            return -1;
        } else {
            return 0;
        }
    }


}