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
			+ "<!DOCTYPE html>" + "<html>\r\n" + 
					"	<head>\r\n" + 
					"<meta charset=\"utf-8\">\r\n" + 
					"<title>WeatherApplication</title>\r\n" + 
					"	\r\n" + 
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
					"	<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\r\n" + 
					"\r\n" + 
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\r\n" + 
					"	<link href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css\" rel=\"stylesheet\"/>\r\n" + 
					"	<script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n" + 
					"	<script src=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.0/js/bootstrap.bundle.min.js\"></script>\r\n" + 
					"	<script src=\"/js/apiclient.js\"></script>\r\n" + 
					"	<script src=\"/js/app.js\"></script>\r\n" + 
					"\r\n" + 
					"</head>\r\n" + 
					"	<body>\r\n" + 
					"		<center>\r\n" + 
					"			<div id=\"contenedor\">\r\n" + 
					"				<div class=\"section encabezado\">\r\n" + 
					"					<h1>Temperatura en las Ciudades</h1>\r\n" + 
					"				</div>\r\n" + 
					"				<div class=\"section consulta\">\r\n" + 
					"					<input name=\"nickname\" id=\"nickname\" placeholder=\"Escriba una ciudad\" type=\"text\" size=\"20\" maxlength=\"40\" required=\"true\">\r\n" + 
					"					<button type=\"button\" id=\"buscar\" class=\"btn btn-primary\">Buscar</button>\r\n" + 
					"				</div>\r\n" + 
					"				<div class=\"section clima\">\r\n" + 
					"					<label class=\"sub\">Ciudad:</label>\r\n" + 
					"					<label id=\"ciudad\"></label><br>\r\n" + 
					"					<label class=\"sub\">Temperatura:</label>\r\n" + 
					"					<label id=\"temperatura\"></label><br>\r\n" + 
					"					<label class=\"sub\">Latitud:</label>\r\n" + 
					"					<label id=\"latitud\"></label><br>\r\n" + 
					"					<label class=\"sub\">Longitud:</label>\r\n" + 
					"					<label id=\"longitud\"></label>\r\n" + 
					"				</div>\r\n" + 
					"			</div>\r\n" + 
					"		</center>\r\n" + 
					"	</body> \r\n" + 
					"</html>";
	}

}
