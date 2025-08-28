import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { Form, FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumber } from 'primeng/inputnumber';
import { IftaLabelModule } from 'primeng/iftalabel';
import { Message } from 'primeng/message';
import { Select } from 'primeng/select';

@Component({
  selector: 'app-create-record',
  imports: [ReactiveFormsModule, Select, Message, InputTextModule, ButtonModule, IftaLabelModule, InputNumber],
  templateUrl: './create-record.html',
  styleUrl: './create-record.scss'
})
export class CreateRecord implements OnInit{
onSubmit() {
throw new Error('Method not implemented.');
}
  private readonly _FormBuilder = inject(FormBuilder);
  
  recordForm!: FormGroup;
  
  formSubmitted: WritableSignal<boolean> = signal(false);

  organizations = [
  { id: 1, code: 'ORG001', name: 'Finance Department' },
  { id: 2, code: 'ORG002', name: 'HR Department' },
  { id: 3, code: 'ORG003', name: 'IT Department' }
];

  ngOnInit(): void {
    this.recordForm = this._FormBuilder.group({
      organizationId: new FormControl(null, [Validators.required]),
      legalEntityId: new FormControl(null, [Validators.required ]),
      remitterBankId: new FormControl(null, [Validators.required ]),
      remitterBankAccountId: new FormControl(null, [Validators.required ]),
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
      costCenters: new FormArray([]),
    });

  }

  isInvalid(controlName: string) {
        const control = this.recordForm.get(controlName);
        return control?.invalid && (control.touched || this.formSubmitted());
  }
}
