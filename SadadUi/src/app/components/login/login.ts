import { Component, inject, Signal, signal, WritableSignal, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { ToastrService } from 'ngx-toastr';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, InputTextModule, ButtonModule, MessageModule],
  providers: [MessageService],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login implements OnInit, OnDestroy{
  private readonly _AuthService = inject(AuthService);
  private readonly _FormBuilder = inject(FormBuilder);
  private readonly _Router = inject(Router);
  private readonly _ToastrService = inject(ToastrService);
  
  loginForm!: FormGroup;
  emailForm!: FormGroup;

  errorMessage: WritableSignal<string | null> = signal(null);
  isLoading: WritableSignal<boolean> = signal(false);
  formSubmitted: WritableSignal<boolean> = signal(false);
  forgotPassword: WritableSignal<boolean> = signal(false);
  
  loginSub!: Subscription;

  ngOnInit(): void {
    this.loginForm = this._FormBuilder.group({
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [Validators.required ])
    });

    this.emailForm = this._FormBuilder.group({
      email: new FormControl(null, [Validators.required, Validators.email])
    })
  }

  ngOnDestroy(): void {
    this.loginSub?.unsubscribe()
  }

  onSubmit() {
    this.formSubmitted.set(true);
    if (this.loginForm.valid && !this.isLoading()) {
      this.isLoading.set(true);
      this.errorMessage.set(null);
      this.loginSub = this._AuthService.login(this.loginForm.value).subscribe({
        next: (response) => {
          this.isLoading.set(false);
          console.log('here')
          this._ToastrService.success('Login Successful', 'SADAD')
          this.formSubmitted.set(false);
          this._Router.navigate(['/home'])    
        },
        error: (err) => {
          this.errorMessage.set(err.message)
          this.isLoading.set(false); 
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  onForgetPassword() {
    if (!this.isLoading()) {
      this.forgotPassword.set(true)
      this.formSubmitted.set(false)
    }
  }

  onSendCode() {
    this.formSubmitted.set(true);
    if (this.emailForm.valid && !this.isLoading()) {
      this.isLoading.set(true);
      this._AuthService.forgetPassword(this.emailForm.value).subscribe({
        next: (res) => {
          this._ToastrService.warning('A code was sent to your email', 'Password Reset')
          this._Router.navigate(['/verify-code'])
        }, 
        error: (err) => {
          this.isLoading.set(false)
        }
      })
    }
    
  }

  isInvalid(controlName: string) {
        const control = this.loginForm.get(controlName);
        return control?.invalid && (control.touched || this.formSubmitted());
  }
}
