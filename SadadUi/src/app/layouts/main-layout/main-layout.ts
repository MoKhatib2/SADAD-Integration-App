import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MainNav } from '../../components/main-nav/main-nav';

@Component({
  selector: 'app-main-layout',
  imports: [RouterOutlet, MainNav],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss'
})
export class MainLayout {

}
