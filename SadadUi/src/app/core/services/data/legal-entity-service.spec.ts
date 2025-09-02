import { TestBed } from '@angular/core/testing';

import { LegalEntityService } from './legal-entity-service';

describe('LegalEntityService', () => {
  let service: LegalEntityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LegalEntityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
