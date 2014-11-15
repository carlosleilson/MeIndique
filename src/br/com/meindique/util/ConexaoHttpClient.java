package br.com.meindique.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ConexaoHttpClient {
	private static final int HTTP_TIMEOUT = 30 * 1000;
	private static HttpClient httpClient;
	
	private static HttpClient getHttpClient(){
		if(httpClient == null){
			httpClient = new DefaultHttpClient();
			final HttpParams HTTPPARRAMS = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(HTTPPARRAMS, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(HTTPPARRAMS, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(HTTPPARRAMS, HTTP_TIMEOUT);
		}
		return httpClient;
	}
	
	public static String executaHttpPost(String url, ArrayList<NameValuePair> parametrosPost )throws Exception{
		BufferedReader bufferedReader = null;
		String resultado = "";
		try{
			HttpClient client = getHttpClient();
			HttpPost httpPost = new HttpPost(url.trim());
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parametrosPost);
			httpPost.setEntity(formEntity);
			HttpResponse httpResponse = client.execute(httpPost);
			bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = "";
			String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bufferedReader != null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return resultado;
	} 
	
	public static String executaHttpGet(String url)throws Exception{
		BufferedReader bufferedReader = null;
		String resultado = "";
		try{
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet(url.trim());
			httpGet.setURI(new URI(url.trim()));
			HttpResponse httpResponse = client.execute(httpGet);
			bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = "";
			String LS = System.getProperty("line.separator");
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bufferedReader != null){
				try{
					bufferedReader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return resultado;
	} 	
}
