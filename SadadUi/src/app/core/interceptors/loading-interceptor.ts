import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { finalize, pipe } from 'rxjs';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
    const _NgxSpinnerService = inject(NgxSpinnerService);

    _NgxSpinnerService.show('loading-2')
    
    return next(req).pipe(finalize(()=>{
        setTimeout(() => {_NgxSpinnerService.hide('loading-2')}, 700)
        
    }));
}