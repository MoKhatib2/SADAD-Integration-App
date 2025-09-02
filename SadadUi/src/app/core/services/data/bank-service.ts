import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { icodename } from '../../interfaces/icodename';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankService {
  private readonly _HttpClient = inject(HttpClient);

  getBanksByOrganizationId(organizationId: number): Observable<any> {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/banks/${organizationId}/organizations`);
  }
}
