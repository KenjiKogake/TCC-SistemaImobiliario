package br.com.imobiliaria.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.imobiliaria.util.ContextUtil;

public class GoogleGeocoding {

	@SuppressWarnings("resource")
	public static List<Double> calcular(String endereco){

		List<Double> listResults = new ArrayList<Double>();
		
		try {
			String urlTexto = "http://maps.googleapis.com/maps/api/geocode/json?address="
					+ tratarEndereco(endereco) + "&sensor=false";
			
			URLConnection conexao = new URL(urlTexto).openConnection();
	
        	String texto = new Scanner(conexao.getInputStream())
        		.useDelimiter("\\Z").next();
        	
			JSONObject jSon = new JSONObject(texto);
			
			//Pega o status da busca
			String status = jSon.getString("status");
			
			if(status.equals("OK")){
				//Entra nos results do JSON
				jSon = new JSONObject(jSon.get("results").toString().substring(1, jSon.get("results").toString().length() - 1));
				
				//Entra na geometry do JSON
				jSon = new JSONObject(jSon.get("geometry").toString());
				
				//Entra no location do JSON
				jSon = new JSONObject(jSon.get("location").toString());
				
				System.out.println(jSon);
				//Pega a distancia do JSON
				Double latitude = jSon.getDouble("lat");
				
				//Pega a dura��o do JSON
				Double longitude = jSon.getDouble("lng");
				
				listResults.add(latitude);
				listResults.add(longitude);
			}else if(status.equals("ZERO_RESULTS")){
				ContextUtil.getAnyMessage("Endereço não encontrado.");
			}else if(status.equals("INVALID_REQUEST")){
				ContextUtil.getAnyMessage("Serviço de geolocalização indisponível.");
			}else if(status.equals("OVER_QUERY_LIMIT")){
				ContextUtil.getAnyMessage("O número de consultas ao serviço foi excedida.");
			}else if(status.equals("REQUEST_DENIED")){
				ContextUtil.getAnyMessage("Solicitação negada pelo Google.");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listResults;
	}

	public static String tratarEndereco(String texto) throws Exception{
		return URLEncoder.encode(texto, "UTF-8");
	}
}
