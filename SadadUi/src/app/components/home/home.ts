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
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
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
    SelectModule, ToolbarModule, CommonModule, FormsModule, CurrencyPipe, DatePipe],
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

        this.getRecords();
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

    filterDate(event: any){
        console.log(event)
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
