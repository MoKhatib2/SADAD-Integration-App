import { TestBed } from '@angular/core/testing';

import { VendorSiteService } from './vendor-site-service';

describe('VendorSiteService', () => {
  let service: VendorSiteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorSiteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
