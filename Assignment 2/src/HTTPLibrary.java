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


    public void GET(String[] argumentTokens, String input) {
        boolean isVerbose = false;
        List<String> data = Arrays.asList(input.split(" "));
        boolean writeToFile = false;
        String url = null;
        String fileName = "";
        try {

            int URLlength = 0;
            if (data.contains("-o")) {
                URLlength = input.indexOf("-o") - 2;

                fileName = input.substring(input.indexOf("-o") + 2);

            } else
                URLlength = input.length() - 1;


            url = input.substring(input.indexOf("http://"), URLlength); // will output the url http://httpbin.org/get?course=networking&assignment=1

        } catch (Exception e) {
            System.out.println(url);
            System.out.println("Please enter a valid URL");
            return;
        }

        URI uri = null;
        String server = null;
        try {
            uri = new URI(url); //Constructs a URI object by parsing the specified string url
            server = uri.getHost(); //Constructs a URI object by parsing the specified string , will return httpbin.org

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        if (data.contains("-d") || data.contains("-f")) {
            System.out.println("Arguments invalid please enter valid arguments");
            return;
        }

        if (data.contains("-v"))
            isVerbose = true;
        if (data.contains("-o"))
            writeToFile = true;
        String headerInfoKeyValue = "";
        List<String> headerInfoList = new ArrayList<>();

        StringBuilder request = new StringBuilder();

        if (data.contains("-h")) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).equals("-h")) {
                    headerInfoList.add(data.get(i + 1));
                }
            }

            if (!headerInfoList.isEmpty()) {
                for (String headerInfo : headerInfoList) {
                    headerInfoKeyValue += headerInfo.split(":")[0] + ":" + headerInfo.split(":")[1] + NEWLINE;
                }
            }
        }
        headerInfoKeyValue += "User-Agent:" + USER_AGENT;

        try {

            Socket socket = new Socket(server, 80);

            PrintStream out = new PrintStream(socket.getOutputStream()); //for sending the data to the stream , we can easily write text with methods like println().
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //for reading from the socket stream in order to easily read text with methods like readLine()

            String r = "GET " + url + " HTTP/1.0 ";
            request.append(r);
            request.append(NEWLINE);
            request.append(headerInfoKeyValue);

            out.println(request);

            out.println();
            String line = in.readLine();

            StringBuilder output = new StringBuilder();
            if (isVerbose) {
                while (line != null) {
                    line = in.readLine();

                    if (writeToFile && line != null)
                        output.append(line + NEWLINE);
                    else if (line != null)
                        System.out.println(line);

                }
            } else {

                while (line != null) {
                    if (line.startsWith("{") && line != null) {

                        if (writeToFile && line != null)
                            output.append(line + NEWLINE);
                        else if (line != null)
                            System.out.println(line);
                        while (!line.startsWith("}") && line != null) {
                            line = in.readLine();

                            if (writeToFile && line != null)
                                output.append(line + NEWLINE);
                            else if (line != null)
                                System.out.println(line);
                        }
                    }
                    line = in.readLine();
                }
            }

            if (writeToFile) {
                try {

                    String currentDir = System.getProperty("user.dir");

                    String filePath = currentDir + "\\" + fileName;

                    FileWriter fileWriter = new FileWriter(filePath, false);
                    fileWriter.write(output.toString());
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                System.out.println("Data written to File Successfully");
            }
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void POST(String[] argumentTokens, String input) {
        boolean isVerbose = false;
        List<String> data = Arrays.asList(input.split(" "));
        boolean writeToFile = false;
        String url = null;
        String fileName = "";
        try {

            int URLlength = 0;
            if (data.contains("-o")) {
                URLlength = input.indexOf("-o") - 2;
                fileName = input.substring(input.indexOf("-o") + 2);
            } else
                URLlength = input.length() - 1;

            url = input.substring(input.indexOf("http://"), input.length());
            if (url.contains(" ")) {
                url = url.split(" ")[0];
            }
            if (url.contains("'"))
                url = url.substring(0, url.length() - 1);
        } catch (Exception e) {
            System.out.println("Please enter a valid URL");
            return;
        }

        URI uri = null;
        String server = null;
        try {
            uri = new URI(url);
            server = uri.getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        if (data.contains("-f") && (data.contains("-d") || data.contains("--d"))) {
            System.out.println("Arguments invalid please enter valid arguments");
            return;
        }
        if (data.contains("-v"))
            isVerbose = true;

        if (data.contains("-o"))
            writeToFile = true;
        String contentData = "";
        int contentLength = 0;

        StringBuilder readData = new StringBuilder("");

        if (data.contains("--d") || data.contains("-d")) {
            contentData = input.substring(input.indexOf("{", input.indexOf("-d")), input.indexOf("}") + 1);
            contentLength = contentData.length();
        } else {
            String inputLines ="";
            try
            {
                String currentDir = System.getProperty("user.dir");
                String fileToRead = input.substring(input.indexOf("-f") + 3, input.indexOf("http://")-1);

                String filePath = currentDir + "\\" + fileToRead;

                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

                while((inputLines = bufferedReader.readLine()) != null)
                {
                    readData.append(inputLines + "\n");
                }
                bufferedReader.close();
            }
            catch(IOException e)
            {
                System.out.println("Error reading file named ");
            }
            contentData = readData.toString();
            contentLength = readData.toString().length();
            //System.out.println(contentData);

        }


        try {
            String headerInfoKeyValue = "";
            StringBuilder request = new StringBuilder();
            List<String> headerInfoList = new ArrayList<>();

            List<String> inputData = Arrays.asList(argumentTokens);


            Socket socket = new Socket(server, 80);
            String r = "POST " + url + " HTTP/1.0 " + NEWLINE + "Host: " + server + NEWLINE;
            request.append(r);

            request.append("Content-Length: " + contentLength);
            request.append(NEWLINE);


            if (!headerInfoList.isEmpty()) {
                for (String headerInfo : headerInfoList) {
                    headerInfoKeyValue = headerInfo.split(":")[0] + ":" + headerInfo.split(":")[1] + NEWLINE;
                    request.append(headerInfoKeyValue);
                }
            }

            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            request.append(NEWLINE);
            request.append(contentData);


            out.print(request);

            String line = in.readLine();

            StringBuilder output = new StringBuilder();
            if (isVerbose) {
                while (line != null) {
                    line = in.readLine();

                    if (writeToFile && line != null)
                        output.append(line + NEWLINE);
                    else if (line != null)
                        System.out.println(line);

                }
            } else {

                while (line != null) {
                    if (line.startsWith("{") && line != null) {

                        if (writeToFile && line != null)
                            output.append(line + NEWLINE);
                        else if (line != null)
                            System.out.println(line);
                        while (!line.startsWith("}") && line != null) {
                            line = in.readLine();

                            if (writeToFile && line != null)
                                output.append(line + NEWLINE);
                            else if (line != null)
                                System.out.println(line);
                        }
                    }
                    line = in.readLine();
                }
            }

            if (writeToFile) {
                try {

                    String currentDir = System.getProperty("user.dir");
                    String filePath = currentDir + "\\" + fileName;

                    FileWriter fileWriter = new FileWriter(filePath, false);
                    fileWriter.write(output.toString());
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                System.out.println("Data written to File Successfully");
            }
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


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


