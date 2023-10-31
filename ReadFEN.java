/** 
 * ReadFEN.java 
 * A program for printing out a board from an FEN line
 * Homework 2: Parts 1 and 2 
 */ 

import java.util.Scanner;
import java.lang.StringBuilder;
import java.io.*;


public class ReadFEN{

    /**
     * Checks the length of the array
     * @param String[] arr
     * @return false, if there are < or > than 8 indexes in the array
     * @return true, if there are 8 indexes in the array
     */
    public static boolean CheckLength(String[] arr){          
        if (arr.length < 8){
            System.out.println("invalid input"); //. There are less than 8 rows in the string.
            return false;
        }
        else if (arr.length < 8){
            System.out.println("invalid input"); //. There are more than 8 rows in the string.
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks to see if there are 8 values in each index
     * @param String[] arr
     * @return false, if there are > or < than 8 values in any index
     * @return true, if there are 8 values in every index
     */
    public static boolean CheckIndex(String[] arr){            
        for (int i = 0; i < arr.length; i++){
            int amount = 0;
            StringBuilder str = new StringBuilder();
            String temp = arr[i].toString();
            str.append(temp);                                   

            for (int j = 0; j < temp.length(); j++){
                char letter = temp.charAt(j);
                int num = (int)letter;
                if (num > 48 && num < 57){ //for a num with ASCII value equivalent to 1-9, amount += num
                    int num1 = Character.getNumericValue(letter);
                    amount += num1;
                }
                else if ((num > 64 && num < 91) || (num > 96 && num < 122)){
                    amount++;
                }
            }
            if (amount != 8){
                System.out.println("invalid input");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there are consecutive numbers
     * @param String[] arr
     * @return false, if there is a repeat
     * @return true, if no repeat
     */
    public static boolean CheckRepeat(String[] arr){            
        int repeat = 0;
        for (int i = 0; i < arr.length; i++){
            StringBuilder str = new StringBuilder();
            String temp = arr[i].toString();
            str.append(temp);                                   

            for (int j = 0; j < temp.length(); j++){
                char letter = temp.charAt(j);
                int num = (int)letter;
                if (num > 48 && num < 57){ //for a num with ASCII value equivalent to 1-8, repeat++
                    repeat ++;
                }
                else {
                    repeat = 0; //for anything other ASCII value, repeat == 0
                }
                if (repeat > 1){
                    System.out.println("invalid input"); //. Two or more number characters are next to each other.
                    return false;
                }
            }
            repeat = 0;
        }
        return true;
    }

    /**
     * Checks to make sure the input letters are valid
     * @param String[] arr
     * @return false, if any invalid letters
     * @return true, if all letters are valid
     */
    public static boolean CheckLetters(String[] arr){
        String valid = "rnbqkpRNBQKP12345678";

        for (int i = 0; i < arr.length; i++){
        
            StringBuilder str = new StringBuilder();
            String temp = arr[i].toString();
            str.append(temp);
            
            for (int j = 0; j < temp.length(); j++){
                char letter = temp.charAt(j);
                int charLoc = valid.indexOf(letter);

                if (charLoc == -1){
                    System.out.println("invalid input");
                    return false;
                }
            }
        }
        return true;
    } 

    /**
     * function prints the board
     * @param String[] arr
     * @return String, the board to print out in main
     */
    public static String PrintFen(String[] arr){          
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < arr.length; i++){
            String temp = arr[i].toString();

            for (int j = 0; j < temp.length(); j++){
                char letter = temp.charAt(j);
                int num = Character.getNumericValue(letter);

                if (num > 9){
                    board.append(letter);
                }
                else{
                    for (int q = 0; q < num; q++){
                        board.append('.');
                    }
                }
                
            }
            board.append('\n');          
        }
        String returnedBoard = board.toString();
        return returnedBoard;
    }


    public static void main (String[] args) throws IOException{

        String[] arr;
        if (args.length > 0){   //checks to see if there is an arg
            String fileName = args[0];
            String fileContent = "";
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));   //opens a file
                String line = reader.readLine();    //reads file
                while (line != null) {  //goes until there are no more lines
                    fileContent += line;
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {   //catches any exceptions
                System.err.println("Error reading file: " + e.getMessage());
            }
            String[] newArr = fileContent.split("/");   //creates an array, each element is split at '/'
            arr = newArr;
        }
            //if no argument, gets FEN through user input
        else{
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter a FEN: ");
            String FEN = scan.nextLine();                       
            String[] newArr1 = FEN.split("/");
            arr = newArr1;
        }

        /**
         * checks to see if the checks are all working. 
         * If not then there is an "invalid input" within each method
         */
         if (CheckLength(arr) && CheckIndex(arr) && CheckRepeat(arr) && CheckLetters(arr)){
            if (args.length > 0){
                String newFileName = args[1];
                File file = new File(newFileName);

                try {
                    String fileContents = PrintFen(arr);
                    System.out.println(fileContents);
                    PrintWriter writer = new PrintWriter(file);
                    writer.println(fileContents);
                    writer.close();
                    } catch (IOException e) {
                       System.out.println("An error occurred: " + e.getMessage());
                }
            } else {
                System.out.print(PrintFen(arr));
            }
        }
    }  
}