import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferirFormComponent } from './transferir-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { provideNgxMask } from 'ngx-mask';
import { beforeEach, describe, expect, it } from 'vitest';

describe('TransferirFormComponent', () => {
  let component: TransferirFormComponent;
  let fixture: ComponentFixture<TransferirFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferirFormComponent],
            providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideNgxMask() 
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TransferirFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
