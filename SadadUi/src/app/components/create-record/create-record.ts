import { Component, inject, OnDestroy, OnInit, signal, WritableSignal } from '@angular/core';
import { AbstractControl, AbstractControlOptions, Form, FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumber } from 'primeng/inputnumber';
import { IftaLabelModule } from 'primeng/iftalabel';
import { Message } from 'primeng/message';
import { Select } from 'primeng/select';
import { OrganizationService } from '../../core/services/data/organization-service';
import { LegalEntityService } from '../../core/services/data/legal-entity-service';
import { BankService } from '../../core/services/data/bank-service';
import { BankAccountService } from '../../core/services/data/bank-account-service';
import { BillerService } from '../../core/services/data/biller-service';
import { VendorService } from '../../core/services/data/vendor-service';
import { VendorSiteService } from '../../core/services/data/vendor-site-service';
import { BusinessService } from '../../core/services/data/business-service';
import { LocationService } from '../../core/services/data/location-service';
import { CostCenterService } from '../../core/services/data/cost-center-service';
import { ExpenseAccountService } from '../../core/services/data/expense-account-service';
import { icodename } from '../../core/interfaces/icodename';
import { Subscription } from 'rxjs';
import { InvoiceTypeService } from '../../core/services/data/invoice-type-service';
import { ibankAccount } from '../../core/interfaces/ibankAccount';
import { Booleanish } from 'primeng/ts-helpers';
import { SadadRecordService } from '../../core/services/sadad-record-service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-record',
  imports: [ReactiveFormsModule, Select, Message, InputTextModule, ButtonModule, IftaLabelModule, InputNumber],
  templateUrl: './create-record.html',
  styleUrl: './create-record.scss'
})
export class CreateRecord implements OnInit, OnDestroy{
  private readonly _FormBuilder = inject(FormBuilder);
  private readonly _SadadRecordService = inject(SadadRecordService);
  private readonly _OrganizationService = inject(OrganizationService);
  private readonly _LegalEntityService = inject(LegalEntityService);
  private readonly _BankService = inject(BankService);
  private readonly _BankAccountService = inject(BankAccountService);
  private readonly _BillerService = inject(BillerService);
  private readonly _VendorService = inject(VendorService);
  private readonly _VendorSiteService = inject(VendorSiteService);
  private readonly _InvoiceTypesService = inject(InvoiceTypeService);
  private readonly _ExpenseAccountService = inject(ExpenseAccountService);
  private readonly _BusinessService = inject(BusinessService);
  private readonly _LocationService = inject(LocationService);
  private readonly _CostCenterService = inject(CostCenterService);
  private readonly _ToastrService = inject(ToastrService);
  
  recordForm!: FormGroup;
  formSubmitted: WritableSignal<boolean> = signal(false);

  organizations: WritableSignal<icodename[]> = signal([
    { id: 1, code: 'ORG001', name: 'Finance Department' },
    { id: 2, code: 'ORG002', name: 'HR Department' },
    { id: 3, code: 'ORG003', name: 'IT Department' }
  ]);
  legalEntities: WritableSignal<icodename[]> = signal([]);
  remitterBanks: WritableSignal<icodename[]> = signal([]);
  remitterBankAccounts: WritableSignal<ibankAccount[]> = signal([]);
  billers: WritableSignal<icodename[]> = signal([]);
  vendors: WritableSignal<icodename[]> = signal([]);
  vendorSites: WritableSignal<icodename[]> = signal([]);
  invoiceTypes: WritableSignal<icodename[]> = signal([]);
  expenseAccounts: WritableSignal<icodename[]> = signal([]);
  businesses: WritableSignal<icodename[]> = signal([]);
  locations: WritableSignal<icodename[]> = signal([]);
  costCenters: WritableSignal<icodename[]> = signal([]);

  chosenOrganizationId!: number;
  isLoading: WritableSignal<boolean> = signal(false);

  organizarionSub!: Subscription;
  legalEntitySub!: Subscription;
  bankSub!: Subscription;
  bankAccountSub!: Subscription
  invoiceTypeSub!: Subscription;
  billerSub!: Subscription;
  vendorSub!: Subscription;
  vendorSiteSub!: Subscription
  expenseAccountSub!: Subscription;
  businessSub!: Subscription
  locationSub!: Subscription;
  costCenterSub!: Subscription;
  createRecordSub!: Subscription;

  ngOnInit(): void {
    this.recordForm = this._FormBuilder.group({
      organizationId: new FormControl(null, [Validators.required]),
      legalEntityId: new FormControl(null, [Validators.required ]),
      remitterBankId: new FormControl({value: null, disabled: true}, [Validators.required]),
      remitterBankAccountId: new FormControl({value: null, disabled: true}, [Validators.required ]),
      billerId: new FormControl(null, [Validators.required ]),
      vendorId: new FormControl({value: null, disabled: true}, [Validators.required ]),
      vendorSiteId: new FormControl({value: null, disabled: true}, [Validators.required ]),
      invoiceTypeId: new FormControl(null, [Validators.required ]),
      invoiceNumber: new FormControl(null, [Validators.required ]),
      subscriptionAccountNumber: new FormControl(null, [Validators.required ]),
      amount: new FormControl(null, [Validators.required ]),
      expenseAccountId: new FormControl(null, [Validators.required ]),
      businessId: new FormControl(null, [Validators.required ]),
      locationId: new FormControl(null, [Validators.required ]),
      costCenters: new FormArray([
        this._FormBuilder.group({
        costCenterId: [null, Validators.required],
        percentage: [0, [Validators.required, Validators.min(0), Validators.max(100)]],
        })
      ])
    }, {validators: this.validatePercentage} as AbstractControlOptions);

    this._OrganizationService.getOrganizations().subscribe(res => this.organizations.set(res.data as icodename[]))
    this._LegalEntityService.getLegalEntities().subscribe(res => this.legalEntities.set(res.data as icodename[]))
    this._BillerService.getBillers().subscribe(res => this.billers.set(res.data as icodename[]))
    this._InvoiceTypesService.getInvoiceTypes().subscribe(res => this.invoiceTypes.set(res.data as icodename[]))
    this._ExpenseAccountService.getExpenseAccounts().subscribe(res => this.expenseAccounts.set(res.data as icodename[]))
    this._BusinessService.getBusinesses().subscribe(res => this.businesses.set(res.data as icodename[]))
    this._LocationService.getLocations().subscribe(res => this.locations.set(res.data as icodename[]))
    this._CostCenterService.getCostCenters().subscribe(res => this.costCenters.set(res.data as icodename[]))
  }

  ngOnDestroy(): void {
    this.organizarionSub?.unsubscribe()
    this.legalEntitySub?.unsubscribe()
    this.bankSub?.unsubscribe()
    this.bankAccountSub?.unsubscribe()
    this.invoiceTypeSub?.unsubscribe()
    this.billerSub?.unsubscribe()
    this.vendorSub?.unsubscribe()
    this.vendorSiteSub?.unsubscribe()
    this.expenseAccountSub?.unsubscribe()
    this.businessSub?.unsubscribe()
    this.locationSub?.unsubscribe()
    this.costCenterSub?.unsubscribe()
  }

  onSubmit() {
    this.formSubmitted.set(true);
    console.log(this.recordForm.value)
    if(this.recordForm.valid && !this.isLoading()) {
      this.isLoading.set(true)
      this._SadadRecordService.createRecord(this.recordForm.value).subscribe({
        next: (res) => {
          this._ToastrService.success("Record created successfully", "New SADAD record")
          this.recordForm.reset()
          this.isLoading.set(false)
          this.formSubmitted.set(false)
        },
        error: (err) => {
          this.isLoading.set(false)
          this.formSubmitted.set(false)
        }
      })
    }
  }

  onSelectChange(controlName: string, value: any) {
    switch(controlName) {
      case 'organizationId':
        this.recordForm.get('remitterBankId')?.enable();
        this.chosenOrganizationId = value;
        this.bankSub = this._BankService.getBanksByOrganizationId(value).subscribe(res => this.remitterBanks.set(res.data as icodename[]));
        break;
      case 'remitterBankId':  
        if (this.chosenOrganizationId) {
            this.recordForm.get('remitterBankAccountId')?.enable();
            this.bankAccountSub = this._BankAccountService.getBankAccountsByBankIdAndOrganizationId(value, this.chosenOrganizationId).subscribe(res => {
              this.remitterBankAccounts.set(res.data as ibankAccount[]) 
              console.log(res)
            });
        }
        break;
      case 'billerId':
        this.recordForm.get('vendorId')?.enable();
        this.vendorSub = this._VendorService.getVendorsByBillerId(value).subscribe(res => this.vendors.set(res.data as icodename[]));
        break;
      case 'vendorId':
        this.recordForm.get('vendorSiteId')?.enable();
        this.vendorSiteSub = this._VendorSiteService.getVendorSitesByVendorId(value).subscribe(res => this.vendorSites.set(res.data as icodename[]));
        break;
      default:
        break;
    }
  }

  getFilledName(controlName: string, index?: number): string {
    let value = -1;
    if ((controlName != 'costCenterId' && this.recordForm.get(controlName)?.value == null) || (controlName == 'costCenterId' && this.recordForm.get('costCenters')?.value[index!].costCenterId == null)) {
      return ''
    }
    switch(controlName) {
      case 'organizationId':
        value = this.recordForm.get(controlName)?.value
        return this.organizations().filter(o => o.id === value)[0].name!;
      case 'remitterBankId':  
        value = this.recordForm.get(controlName)?.value
        return this.remitterBanks().filter(o => o.id === value)[0].name!;
      case 'remitterBankAccountId':  
        value = this.recordForm.get(controlName)?.value
        return this.remitterBankAccounts().filter(o => o.id === value)[0].accountName!;
      case 'billerId':
        value = this.recordForm.get(controlName)?.value
        return this.billers().filter(o => o.id === value)[0].name!;
      case 'vendorId':
        value = this.recordForm.get(controlName)?.value
        return this.vendors().filter(o => o.id === value)[0].name!;
      case 'expenseAccountId':
        value = this.recordForm.get(controlName)?.value
        return this.expenseAccounts().filter(o => o.id === value)[0].name!;
      case 'businessId':
        value = this.recordForm.get(controlName)?.value
        return this.businesses().filter(o => o.id === value)[0].name!;
      case 'locationId':
        value = this.recordForm.get(controlName)?.value
        return this.locations().filter(o => o.id === value)[0].name!;
      case 'costCenterId':
        value = this.recordForm.get('costCenters')?.value[index!].costCenterId
        return this.costCenters().filter(o => o.id === value)[0].name!;
      default:
        break;
    }
    return ''
  }

  validatePercentage(g: AbstractControl) {
    const formArray = g.get('costCenters') as FormArray;
    const total = formArray.controls.reduce((sum, group) => {
      const percentage = group.get('percentage')?.value || 0;
      return sum + percentage;
    }, 0); 
    return total === 100 ? null : { invalidPercentage: true };
  } 

  isInvalid(controlName: string) {
      const control = this.recordForm.get(controlName);
      return control?.invalid && (control.touched || this.formSubmitted());
  }

  isNullOrEmpty(controlName: string) {
    const control = this.recordForm.get(controlName);
    return control?.value === null || control?.value === '';
  }

  isInvalidCostCenter(index: number, controlName: string) {
    const control = this.costCentersFormArray.at(index).get(controlName);
    return control?.invalid && (control.touched || this.formSubmitted());
  }

  get costCentersFormArray(): FormArray {
    return this.recordForm.get('costCenters') as FormArray;
  }

  createCostCenter(): FormGroup {
    return this._FormBuilder.group({
      costCenterId: [null, Validators.required],
      percentage: [0, [Validators.required, Validators.min(1), Validators.max(100)]],
    });
  }

  addCostCenter(): void {
    this.costCentersFormArray.push(this.createCostCenter());
    console.log(this.costCentersFormArray.length);
  }

  removeCostCenter(index: number): void {
    this.costCentersFormArray.removeAt(index);
  }

  disableSubmit(): boolean {
    if(this.isLoading() || this.recordForm.invalid) {
      return true;
    }
    return false;
  }
}
