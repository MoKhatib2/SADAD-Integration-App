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
import { forkJoin, Subscription } from 'rxjs';
import { InvoiceTypeService } from '../../core/services/data/invoice-type-service';
import { ibankAccount } from '../../core/interfaces/ibankAccount';
import { SadadRecordService } from '../../core/services/sadad-record-service';
import { ToastrService } from 'ngx-toastr';
import { irecord } from '../../core/interfaces/irecord';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-record',
  imports: [ReactiveFormsModule, Select, Message, InputTextModule, ButtonModule, IftaLabelModule, InputNumber],
  templateUrl: './update-record.html',
  styleUrl: './update-record.scss'
})
export class UpdateRecord implements OnInit, OnDestroy{
  private readonly _FormBuilder = inject(FormBuilder);
  private readonly _ActivatedRoute = inject(ActivatedRoute);
  private readonly _Router = inject(Router);
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

  sadadRecord: WritableSignal<irecord> = signal({} as irecord);

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
  getRecordSub!: Subscription;
  updateRecordSub!: Subscription;

  ngOnInit(): void {
    this.recordForm = this._FormBuilder.group({
      organizationId: new FormControl(null, [Validators.required]),
      legalEntityId: new FormControl(null, [Validators.required ]),
      remitterBankId: new FormControl(null, [Validators.required]),
      remitterBankAccountId: new FormControl( null, [Validators.required ]),
      billerId: new FormControl(null, [Validators.required ]),
      vendorId: new FormControl(null, [Validators.required ]),
      vendorSiteId: new FormControl(null, [Validators.required ]),
      invoiceTypeId: new FormControl(null, [Validators.required ]),
      invoiceNumber: new FormControl(null, [Validators.required ]),
      subscriptionAccountNumber: new FormControl(null, [Validators.required ]),
      amount: new FormControl(null, [Validators.required ]),
      expenseAccountId: new FormControl(null, [Validators.required ]),
      businessId: new FormControl(null, [Validators.required ]),
      locationId: new FormControl(null, [Validators.required ]),
      costCenters: new FormArray([])
    }, {validators: this.validatePercentage} as AbstractControlOptions);

    this._ActivatedRoute.params.subscribe(params => {
      const id = params['id'];

      this._SadadRecordService.getRecordById(id).subscribe({
        next: (res) => {
          this.sadadRecord.set(res.data);
          this.chosenOrganizationId = this.sadadRecord().organization.id;
          forkJoin({
            remitterBanks: this._BankService.getBanksByOrganizationId(res.data.organization.id),
            remitterBankAccounts: this._BankAccountService.getBankAccountsByBankIdAndOrganizationId(res.data.remitterBank.id, res.data.organization.id),
            vendors: this._VendorService.getVendorsByBillerId(res.data.biller.id),
            vendorSites: this._VendorSiteService.getVendorSitesByVendorId(res.data.vendorSite.id),

            organizations: this._OrganizationService.getOrganizations(),
            legalEntities: this._LegalEntityService.getLegalEntities(),
            billers: this._BillerService.getBillers(),
            invoiceTypes: this._InvoiceTypesService.getInvoiceTypes(),
            expenseAccounts: this._ExpenseAccountService.getExpenseAccounts(),
            businesses: this._BusinessService.getBusinesses(),
            locations: this._LocationService.getLocations(),
            costCenters: this._CostCenterService.getCostCenters()
          }).subscribe({
            next: (all) => {
              this.remitterBanks.set(all.remitterBanks.data as icodename[]);
              this.remitterBankAccounts.set(all.remitterBankAccounts.data as ibankAccount[]);
              this.vendors.set(all.vendors.data as icodename[]);
              this.vendorSites.set(all.vendorSites.data as icodename[]);
              this.organizations.set(all.organizations.data as icodename[]);
              this.legalEntities.set(all.legalEntities.data as icodename[]);
              this.billers.set(all.billers.data as icodename[]);
              this.invoiceTypes.set(all.invoiceTypes.data as icodename[]);
              this.expenseAccounts.set(all.expenseAccounts.data as icodename[]);
              this.businesses.set(all.businesses.data as icodename[]);
              this.locations.set(all.locations.data as icodename[]);
              this.costCenters.set(all.costCenters.data as icodename[]);

              this.patchForm(this.sadadRecord());
            },
            error: (err) => {
              console.error('Error loading lookups:', err);
            }
          });
        }
      });
    });

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
    this.getRecordSub?.unsubscribe()
    this.updateRecordSub?.unsubscribe()
  }

  patchForm(record: irecord) {
    const costCentersFormArray = this.recordForm.get('costCenters') as FormArray;
    costCentersFormArray.clear();

    record.costCenters.forEach(cc => {
      costCentersFormArray.push(
        this._FormBuilder.group({
          costCenterId: cc.id,
          percentage: cc.percentage
        })
      );
    });

    this.recordForm.patchValue({
      organizationId: record.organization.id,
      legalEntityId: record.legalEntity.id,
      remitterBankId: record.remitterBank.id,
      remitterBankAccountId: record.remitterBankAccount.id,
      billerId: record.biller.id,
      vendorId: record.vendor.id,
      vendorSiteId: record.vendorSite.id,
      invoiceTypeId: record.invoiceType.id,
      invoiceNumber: record.invoiceNumber,
      subscriptionAccountNumber: record.subscriptionAccountNumber,
      amount: record.amount,
      expenseAccountId: record.expenseAccount.id,
      businessId: record.business.id,
      locationId: record.location.id,
    })
  }

  onSubmit() {
    this.formSubmitted.set(true);
    console.log(this.recordForm.value)
    if(this.recordForm.valid && !this.isLoading()) {
      this.isLoading.set(true)
      this._SadadRecordService.updateRecord(this.recordForm.value, this.sadadRecord().id).subscribe({
        next: (res) => {
          this._ToastrService.success("Record update successfully", "Update SADAD record")
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
        this.chosenOrganizationId = value
        this.recordForm.get('remitterBankId')?.reset();
        this.recordForm.get('remitterBankAccountId')?.reset();
        this.recordForm.get('remitterBankAccountId')?.disable();
        this.bankSub?.unsubscribe();
        this.bankSub = this._BankService.getBanksByOrganizationId(value).subscribe(res => this.remitterBanks.set(res.data as icodename[]));
        break;
      case 'remitterBankId':
        this.recordForm.get('remitterBankAccountId')?.enable();
        this.recordForm.get('remitterBankAccountId')?.reset();
        this.bankAccountSub?.unsubscribe()
        this.bankAccountSub = this._BankAccountService.getBankAccountsByBankIdAndOrganizationId(value, this.chosenOrganizationId).subscribe(res => {
          this.remitterBankAccounts.set(res.data as ibankAccount[]) 
          console.log(res)
        });
        break;
      case 'billerId':
        this.recordForm.get('vendorId')?.reset();
        this.recordForm.get('vendorSiteId')?.enable();
        this.vendorSub?.unsubscribe()
        this.vendorSub = this._VendorService.getVendorsByBillerId(value).subscribe(res => this.vendors.set(res.data as icodename[]));
        break;
      case 'vendorId':
        this.recordForm.get('vendorSiteId')?.enable();
        this.vendorSiteSub?.unsubscribe()
        this.vendorSiteSub = this._VendorSiteService.getVendorSitesByVendorId(value).subscribe(res => this.vendorSites.set(res.data as icodename[]));
        break;
      default:
        break;
    }
  }

  onCancelUpdate() {
    this._Router.navigate(['/home'])
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
        return this.businesses().filter(o => o.id === value)[0].name!;
      case 'businessId':
        value = this.recordForm.get(controlName)?.value
        return this.expenseAccounts().filter(o => o.id === value)[0].name!;
      case 'locationId':
        value = this.recordForm.get(controlName)?.value
        return this.locations().filter(o => o.id === value)[0].name!;
      case 'costCenterId':
        value = this.recordForm.get('costCenters')?.value[index!].costCenterId
        if (this.costCenters().length > 0) {
          return this.costCenters().filter(o => o.id === value)[0].name!;
        }
        break;
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
