import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth-service';

export const headerInterceptor: HttpInterceptorFn = (req, next) => {
    const _AuthService = inject(AuthService)
    
    if (_AuthService.token !== null){
        if(!req.url.includes('auth')) {
            req = req.clone({
                setHeaders: {Authorization: `Bearer ${ _AuthService.token}`}
            })
        }
    }

  return next(req).pipe(catchError((err) => {
    if(err.error.message == "UNAUTHORIZED" || err.status === 401) {
        _AuthService.logout()
    }
    return throwError(()=>err)
  }));
}