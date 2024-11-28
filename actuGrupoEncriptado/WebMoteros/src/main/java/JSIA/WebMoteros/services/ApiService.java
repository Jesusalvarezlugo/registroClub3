package JSIA.WebMoteros.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import JSIA.WebMoteros.dtos.ClubDto;
import JSIA.WebMoteros.dtos.LoginRequestDto;

@Service
public class ApiService {

	@Value("${api.endpoint}")
	private String apiEndpoint;

	public String sendLoginData(LoginRequestDto loginRequest, String campo) {
		// Crear una instancia de RestTemplate
		try {
			System.out.print("entramos");
			URI uri = new URI("http://localhost:8081/apiMoteros/api/"+campo+"/login");
            URL url = uri.toURL();
            
	        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
	        conexion.setRequestMethod("POST");
	        conexion.setRequestProperty("Content-Type", "application/json");
	        conexion.setDoOutput(true);

	        // Crear cuerpo de la solicitud con Jackson
	        ObjectMapper mapper = new ObjectMapper();
	        String jsonInput = mapper.writeValueAsString(loginRequest);
	        
	        System.out.print(jsonInput);
	        
	        try (OutputStream os = conexion.getOutputStream()) {
	            os.write(jsonInput.getBytes());
	            os.flush();
	        }

	        int codigoRespuesta = conexion.getResponseCode();
	        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
	        	System.out.print("credenciales validas");
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	            String inputLine;
	            StringBuilder response = new StringBuilder();
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            return "success";
	        } else {
	            System.out.println("Error en la conexión: " + codigoRespuesta);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "asdf";
	}
	
	/**
	 * Método para enviar el registro del club a la api
	 * @param nuevoClub
	 * @return un string para verificar si  el método ha funcionado correctamente.
	 */
	public String enviarRegistroClub(ClubDto nuevoClub) {
		
		try {
			
			URI uri = new URI("http://localhost:8081/apiMoteros/api/clubs");
			URL url = uri.toURL();
			
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			
			conexion.setRequestProperty("content-Type", "application/json");
			conexion.setDoOutput(true);
			
			//Pasar el dto  a json para enviarlo a la api
			
			ObjectMapper mapper = new ObjectMapper();
			
			String dtoAJson = mapper.writeValueAsString(nuevoClub);
			System.out.println(dtoAJson);
			
			//Se envian los datos al servidor
			try (OutputStream os = conexion.getOutputStream()) {
	            os.write(dtoAJson.getBytes());
	            os.flush();
	            
	            // Leer la respuesta del servidor
	            int codigoRespuesta = conexion.getResponseCode();
	            
	            if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
	            	
	                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	                
	                StringBuilder response = new StringBuilder();
	                
	                String inputLine;
	                
	                while ((inputLine = in.readLine()) != null) {
	                	
	                    response.append(inputLine);
	                }
	                in.close();
	                
	                return "success"; // Puedes analizar más la respuesta si es necesario
	                
	            } else {
	            	
	                System.out.println("Error en la conexión: " + codigoRespuesta);
	            }
	        }
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return "error";
	}

}
