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

public class GoogleDirections {

	@SuppressWarnings("resource")
	public static List<Integer> calcular(String origem, String destino){

		List<Integer> listResults = new ArrayList<Integer>();
		
		try {
			String urlTexto = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ tratarEndereco(origem) + "&destination=" + tratarEndereco(destino)
					+ "&sensor=false&mode=walking";
			
			URLConnection conexao = new URL(urlTexto).openConnection();
	
        	String texto = new Scanner(conexao.getInputStream())
        		.useDelimiter("\\Z").next();
        	
			JSONObject jSon = new JSONObject(texto);
			
			//Pega o status da busca
			String status = jSon.getString("status");
			
			if(status.equals("OK")){
				//Entra nas rotas do JSON
				jSon = new JSONObject(jSon.get("routes").toString().substring(1, jSon.get("routes").toString().length() - 1));
				
				//Entra nas legs do JSON
				jSon = new JSONObject(jSon.get("legs").toString().substring(1, jSon.get("legs").toString().length() - 1));
				
				//Pega a distancia do JSON
				JSONObject distance = jSon.getJSONObject("distance");
				int distancia = distance.getInt("value");
				
				//Pega a dura��o do JSON
				JSONObject duration = jSon.getJSONObject("duration");
				int duracao = duration.getInt("value");
				
				listResults.add(distancia);
				listResults.add(duracao);
			}else if(status.equals("NOT_FOUND")){
				ContextUtil.getAnyMessage("Um dos locais especificados não foi geocodificado.");
			}else if(status.equals("ZERO_RESULTS")){
				ContextUtil.getAnyMessage("Nenhum trajeto foi encontrado.");
			}else if(status.equals("INVALID_REQUEST")){
				ContextUtil.getAnyMessage("Solicitação inválida.");
			}else if(status.equals("OVER_QUERY_LIMIT")){
				ContextUtil.getAnyMessage("O número de consultas ao serviço foi excedida.");
			}else if(status.equals("REQUEST_DENIED")){
				ContextUtil.getAnyMessage("Solicitação negada pelo Google.");
			}else if(status.equals("UNKNOWN_ERROR")){
				ContextUtil.getAnyMessage("Erro no Servidor, tente novamente.");
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
