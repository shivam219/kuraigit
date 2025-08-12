package com.kurai.model;

public class UpiPaymentResponse {
	public String upiLink;
	public String qrCodeBase64;

	public UpiPaymentResponse(String upiLink, String qrCodeBase64) {
		this.upiLink = upiLink;
		this.qrCodeBase64 = qrCodeBase64;
	}
}
