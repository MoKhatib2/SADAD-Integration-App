import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private readonly _HttpClient = inject(HttpClient);

  getLocations() {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/locations`);
  }
}
