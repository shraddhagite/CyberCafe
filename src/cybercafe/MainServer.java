package cybercafe;
import java.io.*;
import java.net.*;

public class MainServer {
  private static ServerSocket ss = null;
  private static Socket socket = null;
  private static final int MAX_CLIENTS = 5;
  private static final int PORT = 1337;
  private static final ClientThread[] threads = new ClientThread[MAX_CLIENTS];

  public static void main(String[] args) {
    try {
      ss = new ServerSocket(PORT);
    } catch (IOException e) {
        System.out.println(e);
    }
      while(true) {
        try {
          socket = ss.accept();
          int i = 0;
          for(i = 0; i < MAX_CLIENTS; i++) {
            if(threads[i] == null) {
              (threads[i] = new ClientThread(socket)).start();
              break;
            }
          }
          if (i == MAX_CLIENTS) {
            PrintStream os = new PrintStream(socket.getOutputStream());
            os.println("Server too busy. Try later.");
            os.close();
            socket.close();
          }
        } catch(IOException r){
            r.printStackTrace();
        }
    }
  }
}

class ClientThread extends Thread {

  DataInputStream is = null;
  PrintStream ps = null;
  Socket clientSocket = null;
  BufferedInputStream bis = null;
  OutputStream os = null;
  final File root = new File(System.getProperty("user.dir"));
  
  public void writeToClient(String msg) {
    ps.println(msg);
  }

  public ClientThread(Socket cs) {
    this.clientSocket = cs;
    try {
      is = new DataInputStream(cs.getInputStream());
      ps = new PrintStream(cs.getOutputStream());
    } catch(IOException e) {
        e.printStackTrace();
    }
  }

  public void lS(String fName) {
    try {
      System.out.println(fName);
      final File folder = new File(fName);
      if(folder.isDirectory() && folder.exists()) {
        if(!(folder.list().length > 0)) {
          writeToClient("Directory is empty!");
          return;
        }
      }
      for(final File file : folder.listFiles()) {
        if(file.isDirectory())
          writeToClient("<DIR> "+file.getName());
        else
          writeToClient("<FILE> "+file.getName());
      }
    } catch(Exception r) {
        writeToClient("File does not exist.");
    }
  }

  public boolean sendFile(String fName) {
    try {
      System.out.println(fName);
      final File fileToSend = new File(fName);
      writeToClient(fileToSend.length() + " bytes file incoming...");
      byte[] sendArray = new byte[(int)fileToSend.length()];
      bis = new BufferedInputStream(new FileInputStream(fileToSend));
      bis.read(sendArray, 0, sendArray.length);
      os = clientSocket.getOutputStream();
      os.write(sendArray, 0, sendArray.length);
      os.flush();
      return true;
    } catch(Exception e) {
        e.printStackTrace();
        return false;
    }
  }

  public void run() {
	System.out.println(root);
    writeToClient("***Welcome to the Cloud***");
    while(true) {
      try {
        String request = is.readLine().trim();
        if(request.startsWith("quit")) {
          writeToClient("Hope to see you again! :-D Bye.");
          clientSocket.close();
          System.exit(0);
          break;
        }
        else if(request.startsWith("ls")) {
          try {
            lS(root.getAbsolutePath() +"/"+ (request.split("[ \t]+"))[1]);
          } catch(ArrayIndexOutOfBoundsException r) {
              writeToClient("Path is not specified, showing root directory contents..");
              lS(root.getAbsolutePath());
          }
        }
        else if(request.startsWith("get")) {
          try {
            if(sendFile(root.getAbsolutePath() +"/"+ (request.split("[ \t]+"))[1]))
              writeToClient("Transfer Successful!");
            else
              writeToClient("Transfer Failed!");
            } catch(ArrayIndexOutOfBoundsException e) {
                writeToClient("File is not specified!");
            }
        }
      } catch(IOException e) {
          e.printStackTrace();
      } catch(Exception r) {
          r.printStackTrace();
      }
    }
  }
}
