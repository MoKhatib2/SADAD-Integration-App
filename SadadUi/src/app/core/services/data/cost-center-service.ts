import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CostCenterService {
  private readonly _HttpClient = inject(HttpClient);

  getCostCenters() {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/cost-centers`);
  }

  
}
