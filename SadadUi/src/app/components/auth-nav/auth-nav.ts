import { CommonModule, NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { MegaMenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { MegaMenuModule } from 'primeng/megamenu';
import { AvatarModule } from 'primeng/avatar';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-auth-nav',
  imports: [MegaMenuModule, ButtonModule, AvatarModule, NgClass, CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './auth-nav.html',
  styleUrl: './auth-nav.scss'
})
export class AuthNav {
  items: MegaMenuItem[] = [];

 ngOnInit() {
    this.items = [
      {
        label: 'Login',
        root: true,
        routerLink: '/login',
        routerLinkActiveOptions: { exact: true }
      },
      {
        label: 'Sign up',
        root: true,
        routerLink: '/signup',
        routerLinkActiveOptions: { exact: true }
      }
    ];
  }
}
