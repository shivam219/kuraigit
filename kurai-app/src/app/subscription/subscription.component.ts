import { Component } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { SubscriptionService } from '../service/subscription.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-subscription',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent {


  availablePlans: any[] = [];
  clientBoughtSubscriptionPlans: any[] = [];

  constructor(private subscriptionService: SubscriptionService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.fetchClientBoughtSubscription();
    this.fetchAvailablePlans();
  }
  buyPlan(planId: number): void {
    this.subscriptionService.buySubscription(planId).subscribe({
      next: () => {
        this.snackBar.open('Subscription purchased successfully!', 'Close', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
        this.fetchClientBoughtSubscription();
      },
      error: (error) => {
        const errorMessage = error?.error?.message || 'Failed to activate subscription.';
        this.snackBar.open(errorMessage, 'Close', {
          duration: 4000,
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  /*Commented*/
  /*fetchCurrentActivatedSubscription(): void {
    this.subscriptionService.getCurrentSubscription().subscribe({
      next: (res) => {
        this.currentSubscription = res
      },
      error: () => this.currentSubscription = null
    });
  }*/
  /*End Commented*/


  fetchClientBoughtSubscription(): void {
    this.subscriptionService.getClientBoughtSubscription().subscribe({
      next: (res) => {
        this.clientBoughtSubscriptionPlans = res
      },
      error: () => this.clientBoughtSubscriptionPlans = []
    });
  }
  isActiveEntry() {
    return this.clientBoughtSubscriptionPlans.find(e => e.active);
  }

  ActivateNow(index: number, clientPlanId: number): void {
    this.subscriptionService.activateSubscription(clientPlanId).subscribe({
      next: () => {
        this.snackBar.open('Subscription activated successfully!', 'Close', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
        this.clientBoughtSubscriptionPlans.at(index).active = 'Y';
      },
      error: (error) => {
        this.snackBar.open('Failed to activate subscription. Please try again.', 'Close', {
          duration: 3000,
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  turnOnAutoActivateNow(index: number, clientPlanId: number): void {
    this.subscriptionService.turnOnAutoActivateNow(clientPlanId).subscribe({
      next: () => {
        this.snackBar.open(
          'Auto-activation enabled. This plan will activate automatically when your current subscription ends.',
          'Close',
          {
            duration: 3000,
            panelClass: ['snackbar-success']
          }
        );
        this.clientBoughtSubscriptionPlans.at(index).autoActive = 'Y';
      },
      error: () => {
        this.snackBar.open(
          'Failed to enable auto-activation. Please try again.',
          'Close',
          {
            duration: 3000,
            panelClass: ['snackbar-error']
          }
        );
      }
    });

  }



  fetchAvailablePlans(): void {
    this.subscriptionService.getAvailablePlans().subscribe({
      next: (res) => this.availablePlans = res,
      error: () => this.availablePlans = []
    });
  }


}
