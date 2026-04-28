import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputMoedaRealComponent } from './input-moeda-real.component';

describe('InputMoedaRealComponent', () => {
  let component: InputMoedaRealComponent;
  let fixture: ComponentFixture<InputMoedaRealComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputMoedaRealComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InputMoedaRealComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
