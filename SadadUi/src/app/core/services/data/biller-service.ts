import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BillerService {
  private readonly _HttpClient = inject(HttpClient);

  getBillers() {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/billers`);
  }
}
