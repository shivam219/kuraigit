import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { KycService } from '../../service/kyc.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackBarService } from '../../service/snackbar.service';

@Component({
  selector: 'app-upi-connect',
  standalone: true,
  templateUrl: './upi-connect.component.html',
  styleUrls: ['./upi-connect.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class UpiConnectComponent {
  upiForm!: FormGroup;


  constructor(
    private fb: FormBuilder,
    private kycService: KycService,
    private snackBarService: SnackBarService
  ) {
    this.upiForm = this.fb.group({
      upiId: ['', [Validators.required, Validators.pattern(/^[\w.-]+@[\w]+$/)]]
    });
    this.getUpi();
  }

  connectUPI() {
    if (this.upiForm.valid) {
      const upiId = this.upiForm.value.upiId!;
      this.kycService.submitUpiId(upiId).subscribe({
        next: () => {
          this.snackBarService.showSuccess('UPI connected successfully');
          this.getUpi(); // Refresh UPI details after successful connection
        },
        error: (err) => {
          this.snackBarService.showError(err.error?.message || 'UPI connection failed');
        }
      });
    }
  }

  getUpi() {
    this.kycService.getActiveUpi().subscribe({
      next: (data) => {
        console.log('Active UPI:', data);
        if (data && data.upiId) {
          this.upiForm.get('upiId')?.setValue(data.upiId);
          this.snackBarService.showSuccess('Active UPI fetched successfully');
        }
      },
      error: (err) => {
        console.log('Error fetching UPI:', err);
        this.snackBarService.showInfo('No active UPI found');
      }
    });
  }
}
