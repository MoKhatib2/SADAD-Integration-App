import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgxSpinnerComponent } from 'ngx-spinner';
import { ButtonModule } from 'primeng/button';
import { AuthService } from './core/services/auth-service';
import { Toast, ToastModule } from "primeng/toast";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ButtonModule, NgxSpinnerComponent, ToastModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit{
  protected readonly title = signal('SadadUi');
  private readonly _AuthService = inject(AuthService);

  ngOnInit(): void {
    this._AuthService.autoLogin();
  }
}
