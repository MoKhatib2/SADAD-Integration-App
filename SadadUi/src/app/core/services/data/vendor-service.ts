import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { icodename } from '../../interfaces/icodename';

@Injectable({
  providedIn: 'root'
})
export class VendorService {
  private readonly _HttpClient = inject(HttpClient);

  getVendorsByBillerId(id: number) {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/vendors/${id}/billers`);
  }
}
