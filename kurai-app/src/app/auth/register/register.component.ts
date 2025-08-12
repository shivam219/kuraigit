import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterModule } from '@angular/router';
import { ClientService } from '../../service/client.service';

@Component({
  standalone: true,
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [
    ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, MatCardModule, MatButtonModule, RouterModule
  ]
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup;
  constructor(private fb: FormBuilder, private clientService: ClientService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      upiId: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
    
    //   this.registerForm = this.fb.group({
    //   name: ['Shivam', Validators.required],
    //   email: ['shivamchoudhary2002119@gmail.com', [Validators.required, Validators.email]],
    //   phone: ['8779750059', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
    //   upiId: ['shivamchoudhary2002119-1@okaxis', Validators.required],
    //   password: ['Micro@219@', [Validators.required, Validators.minLength(6)]],
    // });
  }

  submit() {
    if (this.registerForm.valid) {
      this.clientService.registerClient(this.registerForm.value).subscribe({
        next: () => {
          this.snackBar.open('Registration successful!', 'Close', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });
        },
        error: (err) => {
          this.snackBar.open(err.error?.message || 'Something went wrong', 'Close', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      });
    } else {
      this.snackBar.open('Please fill all required fields correctly.', 'Close', {
        duration: 3000,
        panelClass: ['snackbar-warning']
      });
    }
  }
}
