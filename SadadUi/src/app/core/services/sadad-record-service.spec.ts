import { TestBed } from '@angular/core/testing';

import { SadadRecordService } from './sadad-record-service';

describe('SadadRecordService', () => {
  let service: SadadRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SadadRecordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
