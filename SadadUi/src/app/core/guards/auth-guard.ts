import { isPlatformBrowser } from '@angular/common';
import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth-service';

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);
  const _AuthService = inject(AuthService)

  if (_AuthService.isAuthenticated) {
    return true;
  }
  
  return router.createUrlTree(['/login']);
};