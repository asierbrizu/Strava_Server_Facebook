package facebook.server;

import java.io.IOException;
import java.net.ServerSocket;

import facebook.remote.LoginFacebookService;

public class LoginFacebookServer {
	
	private static int numClients = 0;
	
	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println(" # Usage: LoginFacebookServer [PORT]");
			System.exit(1);
		}
		
		//args[1] = Server socket port
		int serverPort = Integer.parseInt(args[0]);
		
		try (ServerSocket tcpServerSocket = new ServerSocket(serverPort);) {
			System.out.println(" - LoginFacebookServer: Waiting for connections '" + tcpServerSocket.getInetAddress().getHostAddress() + ":" + tcpServerSocket.getLocalPort() + "' ...");
			while (true) {
                new LoginFacebookService(tcpServerSocket.accept());
                System.out.println("Facebook Login Server: new client joined (" + (++numClients) + " clients)");
            }
			
		} catch (IOException e) {
			System.out.println("# LoginFacebookServer: IO error:" + e.getMessage());
		}
	}
}
