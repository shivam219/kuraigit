import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { PaymentDialogComponent } from '../payment-dialog/payment-dialog.component';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
  ]
})
export class PaymentComponent {
  paymentForm: FormGroup;

  constructor(private fb: FormBuilder, private dialog: MatDialog) {
    this.paymentForm = this.fb.group({
      amount: [10, [Validators.required, Validators.min(1)]]
    });
  }

  openPaymentDialog(): void {
    const amount = this.paymentForm.get('amount')?.value;
    if (this.paymentForm.valid) {
      this.dialog.open(PaymentDialogComponent, {
        data: { amount },
        width: '600px'
      });
    }
  }
}
