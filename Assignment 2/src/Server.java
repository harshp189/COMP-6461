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


public class Server {

    private static ServerSocket serverSocket;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static int port = 8080;
    private static int statusCode = 200;


    static boolean debugFlag = false;

    public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException {

        String request;
        List<String> requestList = new ArrayList<>();

        String directory = System.getProperty("user.dir");

        System.out.println("\nCurrent Directory is: " + directory + "\n");

        System.out.print("Enter the command to run the server >>");
        Scanner scan = new Scanner(System.in);

        request = scan.nextLine();
        if (request.isEmpty()) {
            System.out.println("Invalid Command Please try again!!");
        }
        String[] requestArray = request.split(" ");

        requestList.addAll(Arrays.asList(requestArray));

        if (requestList.contains("-v")) {
            debugFlag = true;
        }

        if (requestList.contains("-p")) {
            String portStr = requestList.get(requestList.indexOf("-p") + 1).trim();
            port = Integer.parseInt(portStr);
        }

        if (requestList.contains("-d")) {
            //directory = requestList.get(requestList.indexOf("-d") + 1).trim();
            directory = request.substring(request.indexOf("-d") + 3);
            System.out.println("Directory for performing operations is now set to : " + directory + "\n");
        }

        //debugFlag = true;
        serverSocket = new ServerSocket(port);
        if (debugFlag)
            System.out.println("Server is up and is assigned port number : " + port);

        File currentFolder = new File(directory);

        while (true) {
            Socket socket = serverSocket.accept();
            if (debugFlag)
                System.out.println("Connection establishment successful between server and client");

            try {
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            request = in.readLine();

            String clientRequestType = request.substring(0, 7);


            if (clientRequestType.contains("httpc")) {


            } else if (clientRequestType.contains("httpfs")) {


            }

        }

    }

    }

