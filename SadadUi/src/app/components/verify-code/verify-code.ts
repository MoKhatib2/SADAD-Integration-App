import { Component, inject, OnDestroy, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputOtpModule } from 'primeng/inputotp';
import { ToastModule } from 'primeng/toast';
import { ButtonModule } from 'primeng/button';
import { AuthService } from '../../core/services/auth-service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-verify-code',
  imports: [InputOtpModule, ToastModule, ButtonModule, MessageModule, FormsModule],
  templateUrl: './verify-code.html',
  styleUrl: './verify-code.scss'
})
export class VerifyCode implements OnDestroy{
  private readonly _AuthService = inject(AuthService);
  private readonly _Router = inject(Router);
  private readonly _MessageService = inject(MessageService);
  private readonly _ToastrService = inject(ToastrService);
  
  verificationCode = signal('')

  verifyCodeSub!: Subscription;

  ngOnDestroy(): void {
      this.verifyCodeSub?.unsubscribe();
  }

  onVerify() {
    if (this.verificationCode().length == 6) {
      this.verifyCodeSub = this._AuthService.verifyCode(this._AuthService.email(), this.verificationCode()).subscribe({
        next: (res) => {
          this._ToastrService.success('Code verified', 'SADAD')
          //this._MessageService.add({ severity: 'success', summary: 'Success', detail: 'Code verified', life: 3000 });
          this._Router.navigate(['/reset-password'])
        },
        error: (err) => {
         
        }
      })
    }
  }
}
