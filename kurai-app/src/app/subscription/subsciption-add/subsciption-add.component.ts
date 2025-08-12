import { Component, OnInit } from '@angular/core';
import { SubscriptionService } from '../../service/subscription.service';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

@Component({
  selector: 'app-subsciption-add',
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule,
    MatInputModule, MatButtonModule,
    MatCardModule, MatButtonModule, MatSelectModule, MatOptionModule,
  ],
  templateUrl: './subsciption-add.component.html',
  styleUrl: './subsciption-add.component.css'
})
export class SubsciptionAddComponent implements OnInit {

  planTypes: any[] = [];
  addPlanForm = new FormGroup({
    name: new FormControl('Welcome Plan', [Validators.required]),
    title: new FormControl('Developer Welcome Plan', [Validators.required]),
    description: new FormControl('Free will Get 100 free', [Validators.required]),
    amount: new FormControl(0, [Validators.required]),
    numberOfScans: new FormControl(100, [Validators.required]),
    iconImage: new FormControl<File | null>(null),
    backgroundImage: new FormControl<File | null>(null),
    planType: new FormControl('FREE', [Validators.required]),
  }, { validators: freePlanValidator });

  
  
  ngOnInit() { }
  constructor(private fb: FormBuilder, private subService: SubscriptionService) {
    this.fetchSubscriptionPlanTye();
  }

  fetchSubscriptionPlanTye(): void {
    this.subService.getSubscriptionPlanTye().subscribe({
      next: (res) => {
        this.planTypes = res
      },
      error: () => this.planTypes = []
    });
  }

  onFileSelected(event: Event, type: 'icon' | 'background') {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      if (type === 'icon') {
        this.addPlanForm.patchValue({ iconImage: file });
      } else {
        this.addPlanForm.patchValue({ backgroundImage: file });
      }
      this.addPlanForm.get(type === 'icon' ? 'iconImage' : 'backgroundImage')?.updateValueAndValidity();
    }
  }
  submit() {
    if (this.addPlanForm.valid) {
      this.subService.addSubscriptionPlan(this.addPlanForm.value).subscribe({
        next: () => alert('Subscription Plan Added!'),
        error: (err) => alert(err.error?.message || 'Failed to add plan')
      });
    }
  }
}
export function plantTypeValidator(planType: AbstractControl): ValidationErrors | null {
  if (planType.value && planType.value.indexOf(' ') >= 0) {
    return { noSpace: true };
  }
  return null;
}

export function freePlanValidator(group: AbstractControl): ValidationErrors | null {
  const planType = group.get('planType')?.value;
  const amountControl = group.get('amount');
  const scansControl = group.get('numberOfScans');

  if (planType === 'FREE') {
    // Amount must be 0
    if (amountControl && amountControl.value !== 0) {
      amountControl.setErrors({ freePlanAmountError: true });
      // amountControl?.updateValueAndValidity({ emitEvent: false, onlySelf: false });
    } else if (amountControl?.hasError('freePlanAmountError')) {
      // Remove error if fixed
      amountControl.setErrors(null);
    }
    // Number of scans must be > 100
    if (scansControl && scansControl.value > 100) {
      scansControl.setErrors({ freePlanScansError: true });
      // scansControl?.updateValueAndValidity({ emitEvent: false, onlySelf: true });
    } else if (scansControl?.hasError('freePlanScansError')) {
      scansControl.setErrors(null);
    }
  } else {
    // If plan is not FREE, clear both errors
    amountControl?.setErrors(null);
    scansControl?.setErrors(null);
  }

  return null;
}
