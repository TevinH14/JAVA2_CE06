package com.example.hamiltontevin_ce06.network;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

	public static byte[] getNetworkData(String _url) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		if(_url == null){
			return null;
		}

		HttpURLConnection connection = null;
		byte[] data = null;
		URL url;

		try {
			String URL_BASE = "https://i.imgur.com/";
			url = new URL(URL_BASE + _url);
			connection = (HttpURLConnection)url.openConnection();
			connection.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputStream is = null;

		try{
			if(connection != null){
				is = connection.getInputStream();
				int num;
				byte[] ba = new byte[1024];
				while (-1 != (num = is.read(ba))) {
					output.write(ba, 0, num);
				}
				 data = output.toByteArray();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connection != null){
				if(is != null){
					try{
						is.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					connection.disconnect();
				}
			}
		}
		return data ;
	}
}