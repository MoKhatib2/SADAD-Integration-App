import { CommonModule, NgClass } from '@angular/common';
import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { MegaMenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { MegaMenuModule } from 'primeng/megamenu';
import { AvatarModule } from 'primeng/avatar';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../core/services/auth-service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-main-nav',
  imports: [MegaMenuModule, ButtonModule, AvatarModule, NgClass, CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './main-nav.html',
  styleUrl: './main-nav.scss'
})
export class MainNav implements OnInit, OnDestroy{
  private readonly _AuthService = inject(AuthService);

  items: MegaMenuItem[] = [];

  userSub!: Subscription;

  ngOnInit() {
    this.userSub = this._AuthService.userSubject.subscribe({
      next: (user) => {
        if (user?.role.toUpperCase() === 'ENTRY') {
          this.items = [
              {
                label: 'View Records',
                root: true,
                routerLink: '/home',
                routerLinkActiveOptions: { exact: true }
              },
              {
                label: 'Create Record',
                root: true,
                routerLink: '/create',
                routerLinkActiveOptions: { exact: true }
              }
          ];
        } else {
          this.items = [
              {
                label: 'View Records',
                root: true,
                routerLink: '/home',
                routerLinkActiveOptions: { exact: true }
              }
          ];
        }
      }
    })
  }

  ngOnDestroy(): void {
      this.userSub?.unsubscribe();
  }

  onSignout() {
    this._AuthService.logout();
  }
}
