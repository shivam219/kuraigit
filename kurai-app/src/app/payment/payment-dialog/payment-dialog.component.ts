import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ClientPaymentOrder, OrderPaymentService } from '../../service/order-payment.service';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-payment-dialog',
  templateUrl: './payment-dialog.component.html',
  imports: [MatSnackBarModule, MatCardModule, CommonModule, ReactiveFormsModule
    , MatFormFieldModule, MatInputModule, MatButtonModule, MatDialogModule,
  ],
  styleUrls: ['./payment-dialog.component.css']
})
export class PaymentDialogComponent implements OnInit {
  user = {
    name: 'Shivam Choudhary',
    phone: '+91 87797 50059',
  };

  showQR = false;
  merchantOrder: any
  qrCodeBase64: string | null = null;
  upiLink: string | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { amount: number; number: number },
    private orderService: OrderPaymentService,
    private snackBar: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.createOrder();
  }

  createOrder() {
    const orderData: ClientPaymentOrder = {
      amount: this.data.amount,
      currency: 'INR',
      description: 'UPI Payment for Kurai Gateway',
      clientKey: 'SDFDSBTlhUmDhPrm',
      company: 'Kurai Pvt Ltd'
    };

    this.orderService.createOrder(orderData).subscribe({
      next: (data) => {
        // this.snackBar.open('Order created successfully! Scan the QR to pay.', 'Close', {
        //   duration: 3000,
        //   panelClass: ['snackbar-success']
        // });
        this.merchantOrder = data;
        console.log(data);
      },
      error: err => {
        if (err.status === 403) {
          this.snackBar.open('Subscription expired. Please upgrade to continue.', 'Close', {
            duration: 4000,
            panelClass: ['snackbar-error']
          });
        } else {
          this.snackBar.open('Failed to create order. Please try again.', 'Close', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      }
    });
  }

  payViaUPI() {
    if (!this.merchantOrder) return;
    const upiRequest = {
      clientId: this.merchantOrder.clientId,
      amount: this.merchantOrder.amount,
      purpose: this.merchantOrder.description,
      orderId: this.merchantOrder.orderId,
      clientKey: this.merchantOrder.clientKey,
      paymentType: 'UPI'
    };

    this.orderService.generateQrCode(upiRequest).subscribe({
      next: (resp) => {
        this.showQR = true;
        this.qrCodeBase64 = resp.qrCodeBase64;
        this.upiLink = resp.upiLink;
      },
      error: () => {
        this.snackBar.open('Failed to generate QR Code.', 'Close', {
          duration: 3000,
          panelClass: ['snackbar-error']
        });
      }
    });
  }


}
