package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/*
Assignment number : 4
File Name : HelloClient.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/


public class HelloClient {

	Socket clientSocket;

	public static final int COUNT = 10;

	/**
	 * Connect to a remote host using TCP/IP and set {@link #clientSocket} to be the
	 * resulting socket object.
	 * 
	 * @param host remote host to connect to.
	 * @param port remote port to connect to.
	 * @throws IOException
	 */
	public void connect(String host, int port) throws IOException {
		this.clientSocket = new Socket (host , port);
	}

	/**
	 * Perform the following actions {@link #COUNT} times in a row: 1. Connect
	 * to the remote server (host:port). 2. Write the string in myname (followed
	 * by newline) to the server 3. Read one line of response from the server,
	 * write it to sysout (without the trailing newline) 4. Close the socket.
	 * 
	 * Then do the following (only once): 1. send
	 * {@link HelloServer#BYE_MESSAGE} to the server (followed by newline). 2.
	 * Read one line of response from the server, write it to sysout (without
	 * the trailing newline)
	 * 
	 * If there are any IO Errors during the execution, output {@link HelloServer#ERR_MESSAGE}
	 * (followed by newline) to sysout. If the error is inside the loop,
	 * continue to the next iteration of the loop. Otherwise exit the method.
	 * 
	 * @param sysout
	 * @param host
	 * @param port
	 * @param myname
	 */
	public void run(PrintStream sysout, String host, int port, String myname) {
		for (int i = 0; i < COUNT; i++) {
			try {	
				connect(host, port);
				
				// opens a way to sent the client a message
				PrintWriter out = new PrintWriter 
									(clientSocket.getOutputStream() , true);
				out.println(myname);
				
				// Receiving the message
				BufferedReader temp = new BufferedReader(new
							InputStreamReader(clientSocket.getInputStream()));
				String line = temp.readLine();
				sysout.print(line);
				
				//closes
				temp.close();
				clientSocket.close();
				out.close();
			} catch (IOException e) {
				continue;
			}
		}
		try {
			// opens a way to sent the client a message
			PrintWriter out2 = new PrintWriter 
									(clientSocket.getOutputStream() , true);
			out2.println(HelloServer.BYE_MESSAGE);
			
			// Receiving the message
			BufferedReader temp2 = new BufferedReader(new 
							InputStreamReader(clientSocket.getInputStream()));
			String line2 = temp2.readLine();
			sysout.print(line2);
			
			//closes
			temp2.close();
			out2.close();
			
		} catch (IOException e) {
			sysout.print(HelloServer.ERR_MESSAGE + "\n");
			return;
		}
	}
}
