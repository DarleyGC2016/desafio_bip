import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioDetailComponent } from './beneficio-detail.component';

describe('BeneficioDetailComponent', () => {
  let component: BeneficioDetailComponent;
  let fixture: ComponentFixture<BeneficioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioDetailComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioDetailComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
