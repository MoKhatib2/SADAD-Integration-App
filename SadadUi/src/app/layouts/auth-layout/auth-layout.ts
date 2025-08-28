import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthNav } from '../../components/auth-nav/auth-nav';

@Component({
  selector: 'app-auth-layout',
  imports: [RouterOutlet, AuthNav],
  templateUrl: './auth-layout.html',
  styleUrl: './auth-layout.scss'
})
export class AuthLayout {

}
