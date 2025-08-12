import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({ providedIn: 'root' })
export class SubscriptionService {
  private baseUrl = environment.apiUrl;
  private baseUrlSubs = 'api/subscriptions';
  private baseUrlAdmin = 'api/admin/subscriptions';
  constructor(private http: HttpClient) { }
  /*Client API*/
  getSubscriptionPlanTye(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${this.baseUrlSubs}/plan-types`);
  }
  /*Not used*/
  getCurrentSubscription(): Observable<any> {
    return this.http.get(`${this.baseUrl}/${this.baseUrlSubs}/client-current-activated-subscription`);
  }
  /*End not used*/

  getClientBoughtSubscription(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${this.baseUrlSubs}/client-subscription-current`);
  }
  buySubscription(planId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlSubs}/client-subscription-buy?planId=${planId}`, {});
  }
  activateSubscription(clientPlanId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlSubs}/client-subscription-active?clientPlanId=${clientPlanId}`, {});
  }
  turnOnAutoActivateNow(clientPlanId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlSubs}/client-subscription-auto-active?clientPlanId=${clientPlanId}`, {});
  }
  addSubscriptionPlan(planData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlAdmin}/plans`, planData);
  }

  /*End Client API*/

  /*Admin API*/
  getAvailablePlans(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${this.baseUrlSubs}/plans`);
  }

  AddToCartSubscription(planData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${this.baseUrlAdmin}/plans`, planData);
  }
  /*End Admin API*/
}
