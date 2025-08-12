import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface ClientPaymentOrder {
  clientId?: number;
  clientKey: string;
  amount: number;
  currency: string;
  description?: string;
  company?: string;
}

@Injectable({
  providedIn: 'root'
})
export class OrderPaymentService {
  private baseUrl = environment.apiUrl;
  private baseUrlOrder = 'api/orders';
  private baseUrlPayment = 'api/payment';
  constructor(private http: HttpClient) { }
  createOrder(order: ClientPaymentOrder): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlOrder}/create`, order);
  }
  orderPayment(order: ClientPaymentOrder): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlPayment}/create`, order);
  }
  generateQrCode(req: any): Observable<{ upiLink: string; qrCodeBase64: string }> {
    return this.http.post<{ upiLink: string; qrCodeBase64: string }>(`${this.baseUrl}/${this.baseUrlPayment}/create`, req);
  }
}
