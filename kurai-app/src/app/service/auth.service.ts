import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  
  private baseUrl = environment.apiUrl;
  constructor(private http: HttpClient) { }

  login(data: { username: string; password: string }) {
    return this.http.post<{ token: string }>(`${this.baseUrl}/api/client/login`, data);
    // return this.http.post<{ token: string }>('/client/login', data);
  }

  logout() {
    localStorage.removeItem('auth_token');
    // clear any other session data if needed
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

}