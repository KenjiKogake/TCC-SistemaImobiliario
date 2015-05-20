package br.com.imobiliaria.ws;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.imobiliaria.util.ContextUtil;

public class BuscaCEP {

	@SuppressWarnings("resource")
	public static ArrayList<String> buscarCEP(String CEP) {

		ArrayList<String> listResults = new ArrayList<String>();
		
		if (!CEP.contains("_")) {
			try {
				
				URLConnection connection =
				           new URL("http://viacep.com.br/ws/" + CEP + "/json/").openConnection();
				String texto = new Scanner(connection.getInputStream(), "UTF-8")
		        	.useDelimiter("\\Z").next();
				
				JSONObject obj = new JSONObject(texto);

				listResults.add((String) obj.get("cep"));
				listResults.add((String) obj.get("logradouro"));
				listResults.add((String) obj.get("bairro"));
				listResults.add((String) obj.get("localidade")); // cidade
				listResults.add((String) obj.get("uf"));
				
				ContextUtil.getAnyMessage("CEP buscado com sucesso.");
				
			} catch (FileNotFoundException e) {
				ContextUtil.getAnyMessage("CEP digitado inválido.");
			} catch (JSONException e) {
				ContextUtil.getAnyMessage("CEP buscado não existente.");
			} catch (MalformedURLException e) {
				ContextUtil
						.getAnyMessage("Ocorreu um erro ao buscar o CEP, entre em contato com o suporte.");
			} catch (Exception e) {
				ContextUtil
						.getAnyMessage("Ocorreu um erro ao buscar o CEP, entre em contato com o suporte.");
			}
		}
		return listResults;
	}
	
}
