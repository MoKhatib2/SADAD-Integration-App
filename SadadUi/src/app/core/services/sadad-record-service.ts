import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, ObservedValueOf } from 'rxjs';
import { irecord } from '../interfaces/irecord';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SadadRecordService {
    private _HttpClient = inject(HttpClient);

    getAllRecords(): Observable<any> {
      return this._HttpClient.get(`${environment.baseUrl}/api/sadad-records`)
    }

    getRecordById(id: number): Observable<any> {
      return this._HttpClient.get(`${environment.baseUrl}/api/sadad-records/${id}`)
    }

    createRecord(recordData: any): Observable<any> {
      return this._HttpClient.post(`${environment.baseUrl}/api/sadad-records`, recordData);
    }

    duplicateRecord(id: number): Observable<any> {
      return this._HttpClient.post(`${environment.baseUrl}/api/sadad-records/duplicate`, {id});
    }

    updateRecord(recordData: any, id: number): Observable<any> {
      return this._HttpClient.put(`${environment.baseUrl}/api/sadad-records/${id}`, recordData);
    }

    confirmRecord(id: number): Observable<any> {
      return this._HttpClient.put(`${environment.baseUrl}/api/sadad-records/confirm/${id}`, {})
    }

    cancelRecord(id: number): Observable<any> {
      return this._HttpClient.put(`${environment.baseUrl}/api/sadad-records/cancel/${id}`, {})
    }

    releaseRecord(id: number): Observable<any> {
      return this._HttpClient.put(`${environment.baseUrl}/api/sadad-records/release/${id}`, {})
    }

    retryInvoice(id: number): Observable<any> {
      return this._HttpClient.put(`${environment.baseUrl}/api/sadad-records/retry-invoice/${id}`, {})
    }
}
