import { TestBed } from '@angular/core/testing';

import { InvoiceTypeService } from './invoice-type-service';

describe('InvoiceTypeService', () => {
  let service: InvoiceTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvoiceTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
