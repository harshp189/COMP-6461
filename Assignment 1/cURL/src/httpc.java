import java.io.*;
import java.util.*;

// -----------------------------------------------------
// Assignment 1
// © Chirag Hasmukhbhai Patel
// © Harsh Patel
// Written by: Chirag Hasmukhbhai Patel 40160656
// Written by: Harshkumar Nileshkumar Patel  Patel 40165709
// -----------------------------------------------------



/* Test Commands

httpc get 'http://httpbin.org/get?course=networking&assignment=1'

httpc get -v 'http://httpbin.org/get?course=networking&assignment=1'

httpc get -v 'http://httpbin.org/get?course=networking&assignment=1' -o output.txt

httpc get -h Content-Type:application/json 'http://httpbin.org/get?course=networking&assignment=1'

httpc post -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post

httpc post -h Content-Type:application/json -f data.txt http://httpbin.org/post

httpc post -v -h Content-Type:application/json -f data.txt http://httpbin.org/post -o output.txt

 */


public class httpc {


    public static void main(String[] args) throws IOException {


        HTTPLibrary httpLibrary = new HTTPLibrary();

        String input;
        if (args.length == 0) {
            int flag = 0;
            System.out.println("-------------------Welcome to cURL like application ------------------------------");

            do {
                System.out.println("Enter Command or Enter 0 to exit from the system");
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine();
                if (input.equals("0")) {
                    flag = 1;
                    return;
                } else {
                    httpLibrary.validateInput(input);
                }


                System.out.println("------------------------------------------------------------------------");
            } while (flag != 1);
        } else {
            input = args.toString();
        }
        httpLibrary.validateInput(input);
    }
}
