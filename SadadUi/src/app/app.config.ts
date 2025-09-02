import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideAnimations } from '@angular/platform-browser/animations';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { headerInterceptor } from './core/interceptors/header-interceptor';
import { errorsInterceptor } from './core/interceptors/errors-interceptor';
import { loadingInterceptor } from './core/interceptors/loading-interceptor';
import { provideToastr } from 'ngx-toastr';
import { NgxSpinnerModule } from 'ngx-spinner';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes), provideClientHydration(withEventReplay()),
    provideHttpClient(withFetch(), withInterceptors([headerInterceptor, errorsInterceptor, loadingInterceptor])),
    provideAnimationsAsync(),
    providePrimeNG({
            theme: {
                preset: Aura
            }
    }),
    //provideAnimations(),
    provideToastr(),
    importProvidersFrom(NgxSpinnerModule),
    MessageService
  ]
};

