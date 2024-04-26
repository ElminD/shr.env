import java.net.*; 
import java.io.*; 

public class EchoServer 
{ 
 public static void main(String[] args) throws IOException 
 { 
    ServerSocket serverSocket = null; 

    String command = "node DownloadLink.js https://459-shr-env-demo.s3.amazonaws.com/Chick-fil-A-Blog-Feature-1-scaled-735x735.jpg";

    Process proc = Runtime.getRuntime().exec(command);

    BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

    try { 
         serverSocket = new ServerSocket(10008); 
    }catch (IOException e){ 
         System.err.println("Could not listen on port: 10008."); 
         System.exit(1); 
    } 

    String hostName = InetAddress.getLocalHost().getHostName();

    while(true){

      Socket clientSocket = null; 
      System.out.println ("Waiting for connection.....");

      try { 
         clientSocket = serverSocket.accept(); 
      }catch (IOException e){ 
         System.err.println("Accept failed."); 
         System.exit(1); 
      } 

      System.out.println ("Connection successful");
      System.out.println ("Waiting for input.....");

      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true); 
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 

      String inputLine; 
      String line = "";

      while ((inputLine = in.readLine()) != null){ 
         if(inputLine.equals("password")){
            while((line = reader.readLine()) != null){
               out.println(line + "\n S");
            }
         }
         else{
            out.println("Invalid password");
            out.close(); 
            in.close(); 
            clientSocket.close();
         }

         if (inputLine.startsWith("Bye.")) break; 
      } 

      out.close(); 
      in.close(); 
      clientSocket.close();

   }
 } 
} 
