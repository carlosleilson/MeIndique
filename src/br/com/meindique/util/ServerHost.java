package br.com.meindique.util;

public enum ServerHost {
	HOST("http://meindique.esy.es/");
	private String host;

	private ServerHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
