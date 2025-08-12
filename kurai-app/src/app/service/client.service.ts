import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class ClientService {
  constructor(private http: HttpClient) {}

  private baseUrl = environment.apiUrl;
  registerClient(data: {
    name: string;
    email: string;
    phone: string;
    upiId: string;
    password: string;
  }) {
    return this.http.post(`${this.baseUrl}/client/register`, data);
  }
}
