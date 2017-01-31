package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/*
Assignment number : 4
File Name : HelloServer.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/

public class HelloServer {
	
	ServerSocket serverSocket = null;
	
	public static final String ERR_MESSAGE = "IO Error!";
	public static final String LISTEN_MESSAGE = "Listening on port: ";
	public static final String HELLO_MESSAGE = "hello ";
	public static final String BYE_MESSAGE = "bye"; 


	public ServerSocket getServerSocket() {
        return serverSocket;
	}
	
	/**
	 * Listen on the first available port in a given list.
	 * 
	 * <p>Note: Should not throw exceptions due to ports being unavailable</p> 
	 *  
	 * @return The port number chosen, or -1 if none of the ports were available.
	 *   
	 */
	public int listen(List<Integer> portList) throws IOException {
		int availablePort = -1;
		int i = 0;
		
		//seek after available port in the list
		while ((i < portList.size()) && (availablePort == -1)) {
			try {
				int port = portList.get(i);
				this.serverSocket = new ServerSocket(port);
				availablePort = port;
			}
			catch (IOException e){
				i++;
			}
		}
        return availablePort;
	}

	
	/**
	 * Listen on an available port. 
	 * Any available port may be chosen.
	 * @return The port number chosen.
	 */
	public int listen() throws IOException {
		serverSocket = new ServerSocket(0);
		return serverSocket.getLocalPort();
	}


	/**
	 * 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed by the port number (and a newline) to sysout.
	 * 	  If there's an IOException at this stage, exit the method.
	 * 
	 * 2. Run in a loop; 
	 * in each iteration of the loop, wait for a client to connect,
	 * then read a line of text from the client. If the text is {@link #BYE_MESSAGE}, 
	 * send {@link #BYE_MESSAGE} to the client and exit the loop. Otherwise, send {@link #HELLO_MESSAGE} 
	 * to the client, followed by the string sent by the client (and a newline)
	 * After sending the hello message, close the client connection and wait for the next client to connect.
	 * 
	 * If there's an IOException while in the loop, or if the client closes the connection before sending a line of text,
	 * send the text {@link #ERR_MESSAGE} to sysout, but continue to the next iteration of the loop.
	 * 
	 * *: in any case, before exiting the method you must close the server socket. 
	 *  
	 * @param sysout a {@link PrintStream} to which the console messages are sent.
	 * 
	 * 
	 */
	public void run(PrintStream sysout) {
	
		try {
			//Start listening on an open port
			int port = listen();
			sysout.println(LISTEN_MESSAGE + port);
			sysout.flush();
		}
		catch (IOException e){
			return;
		}
		
		Socket client;
		String s;
		// waits for clients until client send bye.
		while (true) {
			try {
				client = getServerSocket().accept();
				
				// read message from the client
				BufferedReader bw = new BufferedReader
						(new InputStreamReader(client.getInputStream()));
				
				//if the client send bye then send bye back.
				//else send hello back
				if ((s = bw.readLine()).equals(BYE_MESSAGE)){
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
					out.println(BYE_MESSAGE);
					out.close();
					client.close();
					break;
				}
				else {
					PrintWriter	out = new PrintWriter(client.getOutputStream(), true);
					out.println(HELLO_MESSAGE + " " + s);
					out.close();
					client.close();
				}
			} catch (IOException e) {
				sysout.println(ERR_MESSAGE);
			}
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			sysout.println(ERR_MESSAGE);
		}
		
	}

	public static void main(String args[]) {
		HelloServer server = new HelloServer();

		server.run(System.err);
	}

}
