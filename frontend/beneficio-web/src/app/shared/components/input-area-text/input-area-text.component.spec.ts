
import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputAreaTextComponent } from './input-area-text.component';

describe('InputAreaTextComponent', () => {
  let component: InputAreaTextComponent;
  let fixture: ComponentFixture<InputAreaTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputAreaTextComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InputAreaTextComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
