import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateRecord } from './update-record';

describe('UpdateRecord', () => {
  let component: UpdateRecord;
  let fixture: ComponentFixture<UpdateRecord>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateRecord]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateRecord);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
