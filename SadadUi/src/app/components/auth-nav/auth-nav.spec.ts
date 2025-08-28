import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthNav } from './auth-nav';

describe('AuthNav', () => {
  let component: AuthNav;
  let fixture: ComponentFixture<AuthNav>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthNav]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthNav);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
