import { Component, inject, signal, WritableSignal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth-service';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  imports: [ReactiveFormsModule, InputTextModule, MessageModule, ButtonModule, ToastModule],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.scss'
})
export class ResetPassword {
  private readonly _AuthService = inject(AuthService);
  private readonly _FormBuilder = inject(FormBuilder);
  private readonly _Router = inject(Router);
  private readonly _MessageService = inject(MessageService);
  
  resetPasswordForm!: FormGroup;

  isLoading: WritableSignal<boolean> = signal(false);
  formSubmitted: WritableSignal<boolean> = signal(false);
  
  resetPassSub!: Subscription;

  ngOnInit(): void {
    this.resetPasswordForm = this._FormBuilder.group({
    email: new FormControl(null, [Validators.required, Validators.email]),
    password: new FormControl(null, [Validators.required ])
    });
  }

  ngOnDestroy(): void {
    this.resetPassSub?.unsubscribe()
  }

  onSubmit() {
    this.formSubmitted.set(true);
    if (this.resetPasswordForm.valid && !this.isLoading()) {
      this.isLoading.set(true);
      this.resetPassSub = this._AuthService.resetPassword(this.resetPasswordForm.value).subscribe({
        next: (response) => {
          this.isLoading.set(false);
          this._MessageService.add({ severity: 'success', summary: 'Success', detail: 'Reset Password Successful', life: 3000 });
          this.formSubmitted.set(false);
          this._Router.navigate(['/login'])    
        },
        error: (err) => {
          this.isLoading.set(false); 
        }
      });
    } else {
      this.resetPasswordForm.markAllAsTouched();
    }
  }

  isInvalid(controlName: string) {
        const control = this.resetPasswordForm.get(controlName);
        return control?.invalid && (control.touched || this.formSubmitted());
  }

}
