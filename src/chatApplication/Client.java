package chatApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	Socket socket;
	ServerSocket server;
	BufferedReader br;
	PrintWriter out;

	public Client() {
		try {
			System.out.println("Sending request....");
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("Connection Done...");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			startReader();
			startWriting();

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	private void startReader() {
		Runnable r1 = () -> {
			System.out.println("Reader Started......");
			try {
				while (!socket.isClosed()) {
					String msg = br.readLine();
					if (msg.equals("exit")) {
						System.out.println("Server terminated the chat");
						socket.close();
						break;
					}
					System.out.println("Server : " + msg);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		new Thread(r1).start();
		;
	}

	private void startWriting() {
		Runnable r2 = () -> {
			try {
				while (!socket.isClosed()) {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String content = br.readLine();
					out.println(content);
					out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		new Thread(r2).start();
	}

	public static void main(String[] args) {
		System.out.println("Client started....");
		new Client();
	}
}
