import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { inject } from '@angular/core/primitives/di';
import { icodename } from '../../interfaces/icodename';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InvoiceTypeService {
  private readonly _HttpClient = inject(HttpClient);

  getInvoiceTypes() {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/invoice-types`);
  }
}
