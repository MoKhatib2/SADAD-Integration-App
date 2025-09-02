import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { icodename } from '../../interfaces/icodename';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {
  private readonly _HttpClient = inject(HttpClient);

  getOrganizations() {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/organizations`);
  }
}
