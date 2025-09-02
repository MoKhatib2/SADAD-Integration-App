import { HttpClient } from '@angular/common/http';
import { inject, Injectable, OnInit, PLATFORM_ID, signal } from '@angular/core';
import { environment } from '../environments/environment';
import { BehaviorSubject, tap } from 'rxjs';
import { iuser } from '../interfaces/iuser';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _HttpClient = inject(HttpClient);
  private _PLATFORM_ID = inject(PLATFORM_ID);
  private _Router = inject(Router);

  userSubject = new BehaviorSubject<iuser | null>(null);
  email = signal('')
  token: string | null = null;
  isAuthenticated: boolean = false;
  private tokenExpirationTimer: any;
  
  login(userData: any) {
    return this._HttpClient.post(`${environment.baseUrl}/api/auth/signin`, userData).pipe(
      tap(this.handleAuthentication.bind(this))
    );
  }

  signup(userData: any){
    return this._HttpClient.post(`${environment.baseUrl}/api/auth/signup`, userData).pipe(
      tap(this.handleAuthentication.bind(this))
    );
  }

  forgetPassword(data: any){
    return this._HttpClient.post(`${environment.baseUrl}/api/auth/forget-password`, data).pipe(
      tap(res => this.email.set(data.email))
    );
  }

  verifyCode(email: string, code: string){
    return this._HttpClient.post(`${environment.baseUrl}/api/auth/verify-code`, {email, code});
  }

  resetPassword(data: any){
    return this._HttpClient.put(`${environment.baseUrl}/api/auth/reset-password`, data);
  }

  handleAuthentication(authData: any) {
    const user: iuser = authData.user;
    this.userSubject.next(user)
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

  autoLogin() {
    if (isPlatformBrowser(this._PLATFORM_ID) && localStorage.getItem('user') && localStorage.getItem('token')) {    
      const user: iuser = JSON.parse(localStorage.getItem('user')!);
      this.token = JSON.parse(localStorage.getItem('token')!);
      this.userSubject.next(user);
      this.autoLogout();
      this.isAuthenticated = true;
      //this._Router.navigate(['/home']);
    }        
  }

  logout() {
    this.userSubject.next(null);
    localStorage.removeItem('user');
    localStorage.removeItem('expirationDate');
    localStorage.removeItem('token');
    if(this.tokenExpirationTimer){
        clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
    this.isAuthenticated = false;
    this._Router.navigate(['/login']);
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
