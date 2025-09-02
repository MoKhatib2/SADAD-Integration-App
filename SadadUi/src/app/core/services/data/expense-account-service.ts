import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExpenseAccountService {
  private readonly _HttpClient = inject(HttpClient);

  getExpenseAccounts() {
    return this._HttpClient.get<any>(`${environment.baseUrl}/api/expense-accounts`);
  }
}
