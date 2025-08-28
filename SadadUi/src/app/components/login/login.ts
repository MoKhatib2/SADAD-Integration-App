import { Component, inject, Signal, signal, WritableSignal, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, InputTextModule, MessageModule, ButtonModule, ToastModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  private readonly _AuthService = inject(AuthService);
  private readonly _FormBuilder = inject(FormBuilder);
  private readonly _Router = inject(Router);
  private readonly _MessageService = inject(MessageService);
  
  loginForm!: FormGroup;

  errorMessage: WritableSignal<string | null> = signal(null);
  isLoading: WritableSignal<boolean> = signal(false);
  formSubmitted: WritableSignal<boolean> = signal(false);
  
  loginSub!: Subscription;

  ngOnInit(): void {
    this.loginForm = this._FormBuilder.group({
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required ])
    });
  }

  ngOnDestroy(): void {
    this.loginSub?.unsubscribe()
  }

  onSubmit() {
    this.formSubmitted.set(true);
    if (this.loginForm.valid && !this.isLoading()) {
      this.isLoading.set(true);
      this.errorMessage.set(null);
      this.loginSub = this._AuthService.signup(this.loginForm.value).subscribe({
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
      this.loginForm.markAllAsTouched();
    }
  }

  isInvalid(controlName: string) {
        const control = this.loginForm.get(controlName);
        return control?.invalid && (control.touched || this.formSubmitted());
  }
}
