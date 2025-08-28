import { CommonModule, NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { MegaMenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { MegaMenuModule } from 'primeng/megamenu';
import { AvatarModule } from 'primeng/avatar';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-main-nav',
  imports: [MegaMenuModule, ButtonModule, AvatarModule, NgClass, CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './main-nav.html',
  styleUrl: './main-nav.scss'
})
export class MainNav {
items: MegaMenuItem[] = [];

 ngOnInit() {
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
  }
}
