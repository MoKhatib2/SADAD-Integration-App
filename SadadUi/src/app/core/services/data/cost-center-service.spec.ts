import { TestBed } from '@angular/core/testing';

import { CostCenterService } from './cost-center-service';

describe('CostCenterService', () => {
  let service: CostCenterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CostCenterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
