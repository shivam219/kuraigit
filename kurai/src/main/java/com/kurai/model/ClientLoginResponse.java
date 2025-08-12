package com.kurai.model;

@SuppressWarnings("all")
public class ClientLoginResponse {

	private String UserId;
	private String username;
	private String email;
	private String jwtToken;
	private String role;
	private boolean isAuthenticated;

	public ClientLoginResponse(String userId, String username, String email, String jwtToken, String role,
			boolean isAuthenticated) {
		UserId = userId;
		this.username = username;
		this.email = email;
		this.jwtToken = jwtToken;
		this.role = role;
		this.isAuthenticated = isAuthenticated;
	}

	public ClientLoginResponse() {
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAuthenticated(boolean isAuthenticated) {
		return this.isAuthenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		isAuthenticated = authenticated;
	}

	@Override
	public String toString() {
		return "RouletteUserLoginResponse{" + "UserId='" + UserId + '\'' + ", username='" + username + '\''
				+ ", email='" + email + '\'' + ", jwtToken='" + jwtToken + '\'' + ", role='" + role + '\''
				+ ", isAuthenticated=" + isAuthenticated + '}';
	}
}
