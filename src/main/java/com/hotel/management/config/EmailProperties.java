package com.hotel.management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("email")
public class EmailProperties {

	private String host;
	private String port;
	private String username;
	private String password;

	private String hostImap;
	private String portImap;
	private String protocolImap;

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getHostImap() {
		return hostImap;
	}

	public void setHostImap(String hostImap) {
		this.hostImap = hostImap;
	}

	public String getPortImap() {
		return portImap;
	}

	public void setPortImap(String portImap) {
		this.portImap = portImap;
	}

	public String getProtocolImap() {
		return protocolImap;
	}

	public void setProtocolImap(String protocolImap) {
		this.protocolImap = protocolImap;
	}

}