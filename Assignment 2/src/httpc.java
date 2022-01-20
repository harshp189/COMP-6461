import java.io.*;
import java.net.*;
import java.util.*;

// -----------------------------------------------------
// Assignment 2
// © Chirag Hasmukhbhai Patel
// © Harsh Patel
// Written by: Chirag Hasmukhbhai Patel 40160656
// Written by: Harshkumar Nileshkumar Patel  Patel 40165709
// -----------------------------------------------------



public class httpc {

    static Socket socket = null;
    static PrintWriter pw = null;
    static BufferedReader br = null;


    public static void main(String[] args) throws Exception {
        HTTPLibrary httpLibrary = new HTTPLibrary();

        Scanner sc = new Scanner(System.in);
        String input;
        String result;


        do {
            System.out.print("Please Enter the command : ");
            input = sc.nextLine();
            System.out.println();

            if (input.startsWith("httpc"))
            {
                if (input.contains("help") && input.indexOf("help") == 6)
                {
                    if (input.endsWith("help"))
                    {
                        httpLibrary.displayHelp();

                    }
                    else if (input.contains("get") && input.indexOf("get") == 11 && input.endsWith("get"))
                    {
                        httpLibrary.displayGET();

                    }
                    else if (input.contains("post") && input.indexOf("post") == 11 && input.endsWith("post"))
                    {
                        httpLibrary.displayPOST();

                    }
                    else
                    {
                        System.out.println("Please Enter valid command");
                    }

                }
                else
                {
                    if (input.contains("get") && !(input.endsWith("help")) && !(input.endsWith("get")))
                    {


                        if(input.contains("-d") || input.contains("-f")){
                            System.out.println("GET command can not have -d or -f as options. \nTry Again");
                            continue;
                        }

                        String url = input.substring(input.indexOf("http://"), input.length() - 1);
                        if(url.contains(" "))
                        {
                            url = url.split(" ")[0];
                        }



                        String response;
                        URI uri = new URI(url);

                        String hostName = uri.getHost();
                        socket = new Socket(hostName, uri.getPort());

                        pw = new PrintWriter(socket.getOutputStream());


                        //Send Request
                        System.out.println("Sending request to Server");
                        pw.write(input + "\n");
                        pw.flush();


                        //Receive response
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        StringBuilder dat = new StringBuilder();
                        String line;

                        socket.setSoTimeout(1000);

                        try
                        {
                            while ((line = br.readLine()) != null) {
                                dat.append(line + "\n");
                            }
                        }
                        catch (SocketTimeoutException s)
                        {
                            socket.close();
                        }

                        pw.close();
                        br.close();
                        response = dat.toString();
                        System.out.println("\nResponse from Server : \n" + response);

                    }

                    else if (input.contains("post") && !(input.endsWith("help")))
                    {

                        if(input.contains("-d") && input.contains("-f")){
                            System.out.println("POST command can not have -d and -f as options.POST command can have either -d of -f \nTry Again with valid command");
                            continue;
                        }

                        String url = input.substring(input.indexOf("http://"), input.length());
                        if(url.contains(" "))
                        {
                            url = url.split(" ")[0];
                        }



                        URI uri = new URI(url);

                        String hostName = uri.getHost();
                        socket = new Socket(hostName, uri.getPort());

                        pw = new PrintWriter(socket.getOutputStream());


                        //Sending the Request
                        System.out.println("Sending request to Server");
                        pw.write(input + "\n");
                        pw.flush();

                        //Receiving the response
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        StringBuilder dat = new StringBuilder();
                        String line;

                        socket.setSoTimeout(1000);

                        try
                        {
                            while ((line = br.readLine()) != null) {
                                dat.append(line + "\n");
                            }
                        }
                        catch (SocketTimeoutException s)
                        {
                            socket.close();
                        }

                        pw.close();
                        br.close();
                        String response = dat.toString();
                        System.out.println("\nResponse from Server : \n" + response);


                    }
                    else
                    {
                        System.out.println("Please enter valid command");
                    }
                }
            }
            else
            {
                System.out.println("The Entered command must start with httpc");
            }

        }while (!input.equals("exit"));
    }
}


