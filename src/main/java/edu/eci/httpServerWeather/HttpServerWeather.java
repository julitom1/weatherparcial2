package edu.eci.httpServerWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HttpServerWeather {

	private static final HttpServerWeather instance=new HttpServerWeather();
	public static HttpServerWeather getInstance() {
		return instance;
	}
	
	private HttpServerWeather() {
		
	}
	
	public void start(Integer puerto) throws IOException, URISyntaxException {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(puerto);
		} catch (IOException e) {
			System.err.println("Could not listen on port:."+ puerto);
			System.exit(1);
		}
		boolean running=true;
		while (running){
			Socket clientSocket = null;
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			serveConnection(clientSocket);
		}
		serverSocket.close();
	}
	
public void serveConnection(Socket clientSocket) throws IOException, URISyntaxException {
	OutputStream outStream=clientSocket.getOutputStream();
	PrintWriter out = new PrintWriter(outStream, true);
	InputStream inputStream=clientSocket.getInputStream();
	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
	String rta=computeDefaultResponse();
	out.println(rta);				
	out.close();
	in.close();
	clientSocket.close();
	}

public String computeDefaultResponse() {
	return "HTTP/1.1 200 OK\r\n" 
			+ "Content-Type: text/html\r\n"
			+ "\r\n"
			+ "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset=\"UTF-8\">"
			+ "<title>Title of the document</title>\n" + "</head>" + "<body>" + "My Web Site" 
			+ "<img src=\"https://pbs.twimg.com/media/EeMZ_gyXoAATpew.jpg\"> "
			+ "</body>"
			+ "</html>";
	}

}
