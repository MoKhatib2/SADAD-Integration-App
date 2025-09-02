import { TestBed } from '@angular/core/testing';

import { BillerService } from './biller-service';

describe('BillerService', () => {
  let service: BillerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BillerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
