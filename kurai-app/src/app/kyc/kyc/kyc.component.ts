import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-kyc',
  standalone: true,
  templateUrl: './kyc.component.html',
  styleUrls: ['./kyc.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
})
export class KycComponent {
  kycForm!: FormGroup;
  panFile: File | null = null;
  aadhaarFile: File | null = null;

  constructor(private fb: FormBuilder) {
    // ✅ initialize form here
    this.kycForm = this.fb.group({
      pan: ['', [Validators.required, Validators.pattern(/[A-Z]{5}[0-9]{4}[A-Z]{1}/)]],
      aadhaar: ['', [Validators.required, Validators.pattern(/^[0-9]{12}$/)]],
    });
  }

  onFileChange(event: any, type: 'panFile' | 'aadhaarFile') {
    const file = event.target.files[0];
    if (type === 'panFile') this.panFile = file;
    else this.aadhaarFile = file;
  }

  submitKyc() {
    if (this.kycForm.valid && this.panFile && this.aadhaarFile) {
      const formData = new FormData();
      formData.append('pan', this.kycForm.value.pan!);
      formData.append('aadhaar', this.kycForm.value.aadhaar!);
      formData.append('panFile', this.panFile);
      formData.append('aadhaarFile', this.aadhaarFile);

      // ✅ replace with API call
      alert('KYC submitted successfully!');
    }
  }
}
