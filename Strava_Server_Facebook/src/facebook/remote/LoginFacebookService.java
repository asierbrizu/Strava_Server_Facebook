package facebook.remote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

public class LoginFacebookService extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket tcpSocket;

	private HashMap<String, String> cuentas=new HashMap<>();
	
	
	private static String DELIMITER = "#";
	
	public LoginFacebookService(Socket socket) {
		cuentas.put("herrero", "pantalon");
		cuentas.put("brizuela", "camisa");
		try {
			this.tcpSocket = socket;
		    this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.start();
		} catch (Exception e) {
			System.err.println("# LoginFacebookService - TCPConnection IO error:" + e.getMessage());
		}
	}

	public void run() {
		try {
			String data = this.in.readUTF();			
			String[] separado=data.split(DELIMITER);
			System.out.println("   - LoginFacebookService - Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");					
			this.out.writeBoolean(comprobarContrasenya(separado[0],separado[1]));					
			System.out.println("   - LoginFacebookService - Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + comprobarContrasenya(separado[0],separado[1]) + "'");
		} catch (Exception e) {
			System.out.println("   # LoginFacebookService - TCPConnection error" + e.getMessage());
		} finally {
			try {
				tcpSocket.close();
			} catch (Exception e) {
				System.out.println("   # LoginFacebookService - TCPConnection IO error:" + e.getMessage());
			}
		}
	}
	
public boolean comprobarContrasenya(String email, String contrasenya) {
	return (cuentas.containsKey(email)&&cuentas.get(email).equals(contrasenya));
}
}