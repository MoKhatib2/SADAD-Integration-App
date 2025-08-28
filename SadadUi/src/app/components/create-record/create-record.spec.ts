import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRecord } from './create-record';

describe('CreateRecord', () => {
  let component: CreateRecord;
  let fixture: ComponentFixture<CreateRecord>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateRecord]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateRecord);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
