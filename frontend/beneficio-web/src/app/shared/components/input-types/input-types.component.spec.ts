import { beforeEach, describe, expect, it } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InputTypesComponent } from './input-types.component';


describe('InputTypesComponent', () => {
  let component: InputTypesComponent;
  let fixture: ComponentFixture<InputTypesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputTypesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InputTypesComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
