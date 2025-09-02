import { iuser } from './../../core/interfaces/iuser';
import { NgxSpinnerComponent } from 'ngx-spinner';
import { Component, inject, OnDestroy, OnInit, signal, WritableSignal } from '@angular/core';
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
import { SadadRecordService } from '../../core/services/sadad-record-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../../core/services/auth-service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-home',
  imports: [TableModule, ButtonModule, TagModule, IconFieldModule, InputTextModule, InputIconModule, MultiSelectModule, 
    SelectModule, ToolbarModule, CommonModule, FormsModule, CurrencyPipe],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit, OnDestroy{
    private readonly _AuthService = inject(AuthService);
    private readonly _SadadRecordService = inject(SadadRecordService);
    private readonly _Router = inject(Router);
    private readonly _ToastrService = inject(ToastrService);

    currUser: WritableSignal<iuser | null> = signal(null)
    records: WritableSignal<irecord[]> = signal([]);
    statuses: WritableSignal<{label: string, value: string}[]> = signal([]);
    loading: WritableSignal<boolean> = signal(false);
    selectedRecord: WritableSignal<irecord | null> = signal(null);

    getRecordsSub!: Subscription;
    confirmRecordSub!: Subscription;
    cancelRecordSub!: Subscription;
    releaseRecordSub!: Subscription;
    dupeRecordSub!: Subscription;

    ngOnInit(): void {
        this._AuthService.userSubject.subscribe({
            next: (user) => {
                this.currUser.set(user);
            }
        })

        this.getRecords()
        // this.records.set( [
        //     {
        //         "id": 16,
        //         "organization": {
        //             "id": 1,
        //             "code": "ORG001",
        //             "name": "Global Holdings Inc."
        //         },
        //         "legalEntity": {
        //             "id": 1,
        //             "code": "EG-LE01",
        //             "name": "Global Holdings Egypt"
        //         },
        //         "remitterBank": {
        //             "id": 3,
        //             "code": "NBE",
        //             "name": "National Bank of Egypt"
        //         },
        //         "remitterBankAccount": {
        //             "id": 1,
        //             "accountNumber": "1234567890",
        //             "accountName": "Global Egypt Main Account"
        //         },
        //         "biller": {
        //             "id": 1,
        //             "code": "BILLER-EG-1001",
        //             "name": "Cairo Telecom Biller"
        //         },
        //         "vendor": {
        //             "id": 1,
        //             "code": "VEN-EG-001",
        //             "name": "Cairo Telecom"
        //         },
        //         "vendorSite": {
        //             "id": 1,
        //             "code": "SITE-EG-C01",
        //             "name": "Cairo Main Site"
        //         },
        //         "invoiceType": {
        //             "id": 1,
        //             "code": "Utility Bill",
        //             "name": "Utility Bill"
        //         },
        //         "invoiceNumber": "INV-2025-0001",
        //         "subscriptionAccountNumber": "SUB-123456",
        //         "amount": 1500.75,
        //         "expenseAccount": {
        //             "id": 1,
        //             "code": "EXP-1000",
        //             "name": "Office Supplies"
        //         },
        //         "business": {
        //             "id": 1,
        //             "code": "BUS-001",
        //             "name": "Telecommunications"
        //         },
        //         "location": {
        //             "id": 1,
        //             "code": "CAI",
        //             "name": "Cairo Office"
        //         },
        //         "costCenters": [
        //             {
        //                 "id": 2,
        //                 "code": "CC-200",
        //                 "description": "IT Department",
        //                 "percentage": 40
        //             },
        //             {
        //                 "id": 1,
        //                 "code": "CC-100",
        //                 "description": "Finance",
        //                 "percentage": 60
        //             }
        //         ],
        //         "status": "SAVED"
        //     },
        //     {
        //         "id": 15,
        //         "organization": {
        //             "id": 1,
        //             "code": "ORG001",
        //             "name": "Global Holdings Inc."
        //         },
        //         "legalEntity": {
        //             "id": 1,
        //             "code": "EG-LE01",
        //             "name": "Global Holdings Egypt"
        //         },
        //         "remitterBank": {
        //             "id": 3,
        //             "code": "NBE",
        //             "name": "National Bank of Egypt"
        //         },
        //         "remitterBankAccount": {
        //             "id": 1,
        //             "accountNumber": "1234567890",
        //             "accountName": "Global Egypt Main Account"
        //         },
        //         "biller": {
        //             "id": 1,
        //             "code": "BILLER-EG-1001",
        //             "name": "Cairo Telecom Biller"
        //         },
        //         "vendor": {
        //             "id": 1,
        //             "code": "VEN-EG-001",
        //             "name": "Cairo Telecom"
        //         },
        //         "vendorSite": {
        //             "id": 1,
        //             "code": "SITE-EG-C01",
        //             "name": "Cairo Main Site"
        //         },
        //         "invoiceType": {
        //             "id": 1,
        //             "code": "Utility Bill",
        //             "name": "Utility Bill"
        //         },
        //         "invoiceNumber": "INV-2025-0001",
        //         "subscriptionAccountNumber": "SUB-123456",
        //         "amount": 1500.75,
        //         "expenseAccount": {
        //             "id": 1,
        //             "code": "EXP-1000",
        //             "name": "Office Supplies"
        //         },
        //         "business": {
        //             "id": 1,
        //             "code": "BUS-001",
        //             "name": "Telecommunications"
        //         },
        //         "location": {
        //             "id": 1,
        //             "code": "CAI",
        //             "name": "Cairo Office"
        //         },
        //         "costCenters": [
        //             {
        //                 "id": 1,
        //                 "code": "CC-100",
        //                 "description": "Finance",
        //                 "percentage": 60
        //             },
        //             {
        //                 "id": 2,
        //                 "code": "CC-200",
        //                 "description": "IT Department",
        //                 "percentage": 40
        //             }
        //         ],
        //         "status": "CONFIRMED"
        //     }
        // ])
        this.statuses.set([
            {label: 'Canceled', value: 'CANCELED'},
            {label: 'Released', value: 'RELEASED'},
            {label: 'Saved', value: 'SAVED'},
            {label: 'Confirmed', value: 'CONFIRMED'},
            {label: 'Invoice Failed', value: 'INVOICE_FAILED'},
        ])
    }

    ngOnDestroy(): void {
        this.getRecordsSub?.unsubscribe();
        this.getRecordsSub?.unsubscribe();
        this.confirmRecordSub?.unsubscribe();
        this.cancelRecordSub?.unsubscribe();
        this.releaseRecordSub?.unsubscribe();
    }

    getRecords() {
        this.getRecordsSub?.unsubscribe();
        this.getRecordsSub = this._SadadRecordService.getAllRecords().subscribe({
            next: (res) => {
                this.records.set(res.data); 
                this.selectedRecord.set(null); 
            }
        });
    }

    onNew() {
        this._Router.navigate(["/create"])
    }

    onDuplicate() {
        if(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'SAVED') {
            this.dupeRecordSub?.unsubscribe()
            this.dupeRecordSub = this._SadadRecordService.duplicateRecord(this.selectedRecord()!.id).subscribe({
                next: (res) => {
                    this._ToastrService.success("Record duplicated successfully", "Duplicate SADAD record")
                    this.getRecords()
                }
            })
        }
    }

    onConfirm() {
        if(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'SAVED') {
            this.confirmRecordSub?.unsubscribe();
            this.confirmRecordSub = this._SadadRecordService.confirmRecord(this.selectedRecord()!.id).subscribe({
                next: (res) => {
                   this._ToastrService.success("Record confirmed successfully", "Confirm SADAD record")
                   this.getRecords()
                }
            })
        }
    }

    onCancel() {
        if(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'SAVED') {
            this.cancelRecordSub?.unsubscribe();
            this.cancelRecordSub = this._SadadRecordService.cancelRecord(this.selectedRecord()!.id).subscribe({
                next: (res) => {
                    this._ToastrService.success("Record canceled successfully", "Cancel SADAD record")
                    this.getRecords()
                }
            })
        } 
    }

    onRelease() {
        if(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'CONFIRMED') {
            this.releaseRecordSub?.unsubscribe();
            this.releaseRecordSub = this._SadadRecordService.releaseRecord(this.selectedRecord()!.id).subscribe({
                next: (res) => {
                    this.getRecords()
                    if (res.data.status === 'RELEASED') {
                        this._ToastrService.success("Record released successfully", "Release SADAD record")
                    } else {
                        this._ToastrService.warning("Record released but invoice creation failed", "Release SADAD record")
                    }
                    
                }
            })
        } 
    }

    onRetry() {
        if(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'INVOICE_FAILED') {
            this.releaseRecordSub?.unsubscribe();
            this.releaseRecordSub = this._SadadRecordService.retryInvoice(this.selectedRecord()!.id).subscribe({
                next: (res) => {
                    if (res.data.status === 'RELEASED') {
                        this._ToastrService.success("Invoice created successfully", "Retry Invoice Creation")
                    } else {
                        this._ToastrService.warning("Invoice creation failed", "Retry Invoice Creation")
                    }

                    this.getRecords()
                }
            })
        } 
    }

    disableButtons() {
        return !(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'SAVED')
    }

    disableRelease() {
        return !(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'CONFIRMED')
    }

    disableRetry() {
        return !(this.selectedRecord() && this.selectedRecord()?.status.toUpperCase() == 'INVOICE_FAILED')
    }

    editProduct(id: number) {
        this._Router.navigate(["/update", id])
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
                return 'warn';
        }
        return null;
    }

}
