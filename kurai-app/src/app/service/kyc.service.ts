import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class KycService {

  private baseUrl = environment.apiUrl;

  private selfUrl = 'kyc';

  constructor(private http: HttpClient) { }

  submitUpiId(upiId: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/${this.selfUrl}/client-upi/connect-upi`, {
      upiId: upiId,
      isActive: true // or false — depending on UI
    });
  }
  getActiveUpi(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/api/${this.selfUrl}/client-upi/connect-upi`);
  }
}
