import { serverRoutes } from './../../app.routes.server';
import { Component, inject, Signal, signal, WritableSignal, OnInit, OnDestroy } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { Select } from 'primeng/select';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-signup',
  imports: [ReactiveFormsModule, InputTextModule, MessageModule, ButtonModule, ToastModule, Select],
  templateUrl: './signup.html',
  styleUrl: './signup.scss'
})
export class Signup implements OnInit, OnDestroy {
  private readonly _AuthService = inject(AuthService);
  private readonly _FormBuilder = inject(FormBuilder);
  private readonly _Router = inject(Router);
  private readonly _MessageService = inject(MessageService);
  
  signupForm: FormGroup = this._FormBuilder.group({
    firstName: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
    lastName: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
    role: new FormControl(null, [Validators.required]),
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required, Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,25}$/)])
  });

  roles: WritableSignal<string[]> = signal([]);

  errorMessage: WritableSignal<string | null> = signal(null);
  isLoading: WritableSignal<boolean> = signal(false);
  formSubmitted: WritableSignal<boolean> = signal(false);
  
  signupSub!: Subscription;

  ngOnInit(): void {
      this.roles.set(['Entry', 'Approve', 'Release'])
  }

  ngOnDestroy(): void {
    this.signupSub?.unsubscribe()
  }

  onSubmit() {
    this.formSubmitted.set(true);
    if (this.signupForm.valid && !this.isLoading()) {
      this.isLoading.set(true);
      this.errorMessage.set(null);
      this.signupSub = this._AuthService.signup(this.signupForm.value).subscribe({
        next: (response) => {
          this.isLoading.set(false);
          this._MessageService.add({ severity: 'success', summary: 'Success', detail: 'Form Submitted', life: 3000 });
          this.formSubmitted.set(false);
          setTimeout(() => {
            this._Router.navigate(['/home']);
          }, 1000);       
        },
        error: (err) => {
          this.errorMessage = err.message
          this.isLoading.set(false); 
        }
      });
    } else {
      this.signupForm.markAllAsTouched();
    }
  }

  isInvalid(controlName: string) {
        const control = this.signupForm.get(controlName);
        return control?.invalid && (control.touched || this.formSubmitted());
  }
}
