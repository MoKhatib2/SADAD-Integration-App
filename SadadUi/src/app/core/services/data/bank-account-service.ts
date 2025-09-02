import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BankAccountService {
  private readonly _HttpClient = inject(HttpClient);

  getBankAccountsByBankId(bankId: number): Observable<any> {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/bank-accounts/${bankId}/banks`);
  }

  getBankAccountsByBankIdAndOrganizationId(bankId: number, organizationId: number): Observable<any> {
    let params = new HttpParams();
    params = params.append('bankId', bankId);
    params = params.append('organizationId', organizationId);
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/bank-accounts/getBybankAndOrganization` ,{params: params} );
  }
}
