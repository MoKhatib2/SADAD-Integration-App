import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { Table } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { ToolbarModule } from 'primeng/toolbar';
import { SelectModule } from 'primeng/select';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { irecord } from '../../core/interfaces/irecord';


@Component({
  selector: 'app-home',
  imports: [TableModule, ButtonModule, TagModule, IconFieldModule, InputTextModule, InputIconModule, MultiSelectModule, 
    SelectModule, ToolbarModule, CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit{
editProduct(_t118: any) {
throw new Error('Method not implemented.');
}
  records: WritableSignal<irecord[]> = signal([]);
  statuses: WritableSignal<{label: string, value: string}[]> = signal([]);
  loading: WritableSignal<boolean> = signal(false);
  selectedRecord: WritableSignal<irecord | null> = signal(null);

  ngOnInit(): void {
    this.records.set( [
        {
            "id": 16,
            "organization": {
                "id": 1,
                "code": "ORG001",
                "name": "Global Holdings Inc."
            },
            "legalEntity": {
                "id": 1,
                "code": "EG-LE01",
                "name": "Global Holdings Egypt"
            },
            "remitterBank": {
                "id": 3,
                "code": "NBE",
                "name": "National Bank of Egypt"
            },
            "remitterBankAccount": {
                "id": 1,
                "accountNumber": "1234567890",
                "accountName": "Global Egypt Main Account"
            },
            "biller": {
                "id": 1,
                "code": "BILLER-EG-1001",
                "name": "Cairo Telecom Biller"
            },
            "vendor": {
                "id": 1,
                "code": "VEN-EG-001",
                "name": "Cairo Telecom"
            },
            "vendorSite": {
                "id": 1,
                "code": "SITE-EG-C01",
                "name": "Cairo Main Site"
            },
            "invoiceType": {
                "id": 1,
                "code": "Utility Bill",
                "name": "Utility Bill"
            },
            "invoiceNumber": "INV-2025-0001",
            "subscriptionAccountNumber": "SUB-123456",
            "amount": 1500.75,
            "expenseAccount": {
                "id": 1,
                "code": "EXP-1000",
                "name": "Office Supplies"
            },
            "business": {
                "id": 1,
                "code": "BUS-001",
                "name": "Telecommunications"
            },
            "location": {
                "id": 1,
                "code": "CAI",
                "name": "Cairo Office"
            },
            "costCenters": [
                {
                    "id": 2,
                    "code": "CC-200",
                    "description": "IT Department",
                    "percentage": 40
                },
                {
                    "id": 1,
                    "code": "CC-100",
                    "description": "Finance",
                    "percentage": 60
                }
            ],
            "status": "SAVED"
        },
        {
            "id": 15,
            "organization": {
                "id": 1,
                "code": "ORG001",
                "name": "Global Holdings Inc."
            },
            "legalEntity": {
                "id": 1,
                "code": "EG-LE01",
                "name": "Global Holdings Egypt"
            },
            "remitterBank": {
                "id": 3,
                "code": "NBE",
                "name": "National Bank of Egypt"
            },
            "remitterBankAccount": {
                "id": 1,
                "accountNumber": "1234567890",
                "accountName": "Global Egypt Main Account"
            },
            "biller": {
                "id": 1,
                "code": "BILLER-EG-1001",
                "name": "Cairo Telecom Biller"
            },
            "vendor": {
                "id": 1,
                "code": "VEN-EG-001",
                "name": "Cairo Telecom"
            },
            "vendorSite": {
                "id": 1,
                "code": "SITE-EG-C01",
                "name": "Cairo Main Site"
            },
            "invoiceType": {
                "id": 1,
                "code": "Utility Bill",
                "name": "Utility Bill"
            },
            "invoiceNumber": "INV-2025-0001",
            "subscriptionAccountNumber": "SUB-123456",
            "amount": 1500.75,
            "expenseAccount": {
                "id": 1,
                "code": "EXP-1000",
                "name": "Office Supplies"
            },
            "business": {
                "id": 1,
                "code": "BUS-001",
                "name": "Telecommunications"
            },
            "location": {
                "id": 1,
                "code": "CAI",
                "name": "Cairo Office"
            },
            "costCenters": [
                {
                    "id": 1,
                    "code": "CC-100",
                    "description": "Finance",
                    "percentage": 60
                },
                {
                    "id": 2,
                    "code": "CC-200",
                    "description": "IT Department",
                    "percentage": 40
                }
            ],
            "status": "CONFIRMED"
        }
    ])
    this.statuses.set([
        {label: 'Canceled', value: 'CANCELED'},
        {label: 'Released', value: 'RELEASED'},
        {label: 'Saved', value: 'SAVED'},
        {label: 'Confirmed', value: 'CONFIRMED'},
        {label: 'Invoice Failed', value: 'INVOICE_FAILED'},
    ])
  }

  onNew() {

  }

  onCancel() {

  }

  getSeverity(status: string) {
      switch (status.toUpperCase()) {
          case 'CANCELED':
              return 'danger';

          case 'RELEASED':
              return 'success';

          case 'SAVED':
              return 'info';

          case 'CONFIRMED':
              return 'warn';

          case 'INVOICE_FAILED':
              return null;
      }
      return null;
    }

}
