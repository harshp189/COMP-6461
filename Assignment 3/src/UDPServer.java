import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.*;

/**
 * This class contains the implementation of UDP Server.
 *
 */
public class UDPServer {

    private static int statusCode = 200;;
    static boolean debugFlag = false;
    static String dir = System.getProperty("user.dir");

    static File currentFolder;
    static int timeout = 3000;
    static int port = 8080;
    List<String> clientRequestList;


    public static void main(String[] args) throws Exception {
        String request;
        List<String> requestList = new ArrayList<>();

        //String dir = System.getProperty("user.dir");

        System.out.println("\nCurrent Directory is -> " + dir + "");
        System.out.print("-->");
        Scanner sc = new Scanner(System.in);

        request = sc.nextLine();
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
            dir = request.substring(request.indexOf("-d")+3);
            System.out.println("Selected directory for operations : " + dir + "\n");
        }

        //debugFlag = true;
        if (debugFlag)
            System.out.println("Server is up and it assign to port Number: " + port);

        currentFolder = new File(dir);

        UDPServer server = new UDPServer();

        Runnable task = () -> {
            try {
                server.listenAndServe(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();


       // server.listenAndServe(port);



    }

    /**
     * This method will extract payload from client request
     *
     */
    private void listenAndServe(int port) throws Exception
    {
        try (DatagramChannel channel = DatagramChannel.open())
        {
            channel.bind(new InetSocketAddress(port));  //will open datagram channel that will receive packets on port 8080
            Packet response;
            ByteBuffer buf = ByteBuffer.allocate(Packet.MAX_LEN).order(ByteOrder.BIG_ENDIAN);

            for (;;) {
                buf.clear();
                SocketAddress router = channel.receive(buf); //The receive() method will copy the content of a received packet of data into the given Buffer.
                if (router != null) {
                    // Parse a packet from the received raw data.
                    buf.flip();
                    Packet packet = Packet.fromBuffer(buf);
                    buf.flip();

                    String requestPayload = new String(packet.getPayload(), UTF_8);
                    // Send the response to the router not the client.
                    // The peer address of the packet is the address of the client already.
                    // We can use toBuilder to copy properties of the current packet.
                    // This demonstrate how to create a new packet from an existing packet.

                    if (requestPayload.equals("Hi S"))
                    {
                        System.out.println("Client: " + requestPayload);
                        response = packet.toBuilder().setPayload("Hi C".getBytes()).create();
                        channel.send(response.toBuffer(), router);
                        System.out.println("Sending Hi from Server");
                    }
                    else if (requestPayload.contains("httpfs") || requestPayload.contains("httpc"))
                    {
                        System.out.println("Client: " + requestPayload);
                        String responsePayload = processPayloadRequest(requestPayload);
                        response = packet.toBuilder().setPayload(responsePayload.getBytes()).create();
                        channel.send(response.toBuffer(), router);

                    }
                    else if (requestPayload.equals("Received"))
                    {
                        System.out.println("Client: " + requestPayload + "\nSending Close");
                        response = packet.toBuilder().setPayload("Close".getBytes()).create();
                        channel.send(response.toBuffer(), router);

                    }
                    else if (requestPayload.equals("Ok"))
                    {
                        System.out.println("Client: " + requestPayload);
                        System.out.println(requestPayload + " received..!");

                    }
                }
            }
        }


    }

    /**
     * This method proccesses the payload request from the client's input and will return the response body.
     *
     * @param request client's request
     * @return response body
     */
    private String processPayloadRequest(String request) throws Exception {

        return request;
    }

}
