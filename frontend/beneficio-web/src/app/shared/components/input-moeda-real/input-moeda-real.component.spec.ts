import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputMoedaRealComponent } from './input-moeda-real.component';
import { beforeEach, describe, expect, it } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { provideNgxMask } from 'ngx-mask';

describe('InputMoedaRealComponent', () => {
  let component: InputMoedaRealComponent;
  let fixture: ComponentFixture<InputMoedaRealComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputMoedaRealComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideNgxMask() // <-- Adicione isso para resolver o erro do InjectionToken
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(InputMoedaRealComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
