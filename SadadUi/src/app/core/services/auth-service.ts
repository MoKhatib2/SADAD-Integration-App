import { HttpClient } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../environments/environment';
import { BehaviorSubject, tap } from 'rxjs';
import { Iuser } from '../interfaces/iuser';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _HttpClient = inject(HttpClient);
  private _PLATFORM_ID = inject(PLATFORM_ID);

  userSubject = new BehaviorSubject<Iuser | null>(null);
  token: string | null = null;
  isAuthenticated: boolean = false;
  private tokenExpirationTimer: any;

  login(userData: any) {
    return this._HttpClient.post(`${environment.baseUrl}/auth/sigin`, userData).pipe(
      tap(this.handleAuthentication)
    );
  }

  signup(userData: any){
    return this._HttpClient.post(`${environment.baseUrl}/auth/signup`, userData).pipe(
      tap(this.handleAuthentication)
    );
  }

  handleAuthentication(authData: any) {
    const user: Iuser = authData.user;
    this.userSubject.next(user);
    this.token = authData.token;
    const expirationDate = new Date(new Date().getTime() + 1 * 60 * 60 * 1000);
    if (isPlatformBrowser(this._PLATFORM_ID)) { 
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('expirationDate', JSON.stringify(expirationDate));
      localStorage.setItem('token', JSON.stringify(authData.token));
      this.autoLogout();
      this.isAuthenticated = true;
    } 
  }

  logout() {
    this.userSubject.next(null);
    //this.router.navigate(['/home']);
    localStorage.removeItem('user');
    localStorage.removeItem('expirationDate');
    localStorage.removeItem('token');
    if(this.tokenExpirationTimer){
        clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
    this.isAuthenticated = false;
  }

  autoLogout(){
    const expirationDate = JSON.parse(localStorage.getItem('expirationDate')!);
    if(!expirationDate){
        this.logout();
        return;
    }     
    const expiresIn = new Date(expirationDate).getTime() - new Date().getTime()
    if( expiresIn < 0 ){
        this.logout();
        return;
    }
    this.tokenExpirationTimer = setTimeout(() => {
        this.logout();
    }, expiresIn);
  }
}
