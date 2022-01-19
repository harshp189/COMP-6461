import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


// -----------------------------------------------------
// Assignment 1
// © Chirag Hasmukhbhai Patel
// © Harsh Patel
// Written by: Chirag Hasmukhbhai Patel 40160656
// Written by: Harshkumar Nileshkumar Patel  Patel 40165709
// -----------------------------------------------------


public class HTTPLibrary {
    public final String USER_AGENT = "Concordia-HTTP/1.0";
    public static String NEWLINE = "\n";

    public void displayHelp() {
        System.out.println("httpc is a curl-like application but supports HTTP protocol only. \n" +
                "Usage: \n " +
                "\t httpc command [arguments] \n " +
                "The commands are: \n" +
                "\t get\texecutes a HTTP GET request and prints the response. \n" +
                "\t post\texecutes a HTTP POST request and prints the response. \n " +
                "\t help\tprints this screen.\n\n" +
                "Use \"httpc help [command]\" for more information about a command.");
    }

    public void displayGET() {
        System.out.println("usage: httpc get [-v] [-h key:value] URL \n\n" +
                "Get executes a HTTP GET request for a given URL.\n\n " +
                "\t-v\t\t\t\tPrints the detail of the response such as protocol, status,\n" +
                "and headers.\n" +
                "\t-h key:value\tAssociates headers to HTTP Request with the format\n" +
                "'key:value'.");

    }

    public void displayPOST() {
        System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL \n\n" +
                "Post executes a HTTP POST request for a given URL with inline data or from\n" +
                "file.\n\n " +
                "-v\t\t\t\tPrints the detail of the response such as protocol, status,\n" +
                "and headers.\n" +
                "-h key:value\tAssociates headers to HTTP Request with the format\n" +
                "'key:value'.\n" +
                "-d string\t\tAssociates an inline data to the body HTTP POST request.\n" +
                "-f file\t\t\tAssociates the content of a file to the body HTTP POST\n" +
                "request.\n\n" +
                "Either [-d] or [-f] can be used but not both."
        );

    }


    public static void GET(String[] argumentTokens, String input){

    }



    public static void POST(String[] argumentTokens, String input){

    }


    public void callHelp(String[] argumentTokens) {
        if (argumentTokens[1].equals("help")) {
            if (argumentTokens.length == 2)
                displayHelp();
            else {
                if (argumentTokens[2].equals("get") && argumentTokens.length == 3) {
                    displayGET();
                } else if (argumentTokens[2].equals("post") && argumentTokens.length == 3) {
                    displayPOST();
                } else {
                    System.out.println("Invalid arguments Please run again with valid arguments");
                }
            }
        }
    }


    public void validateInput(String input) throws IOException {
        String[] argumentTokens = input.split(" ");
        String command = argumentTokens[0];
        if (!command.equals("httpc")) {
            System.out.println("Please run again with valid command");
            return;
        }
        if (argumentTokens.length == 1) {
            System.out.println("Please enter a valid command");
            return;
        }
        switch (argumentTokens[1]) {
            case "help":
                callHelp(argumentTokens);
                break;
            case "get":
                GET(argumentTokens, input);
                break;
            case "post":
                POST(argumentTokens, input);
                break;
            default:
                System.out.println("Invalid arguments Please run again with valid arguments");
        }

    }
}


