import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferirFormComponent } from './transferir-form.component';

describe('TransferirFormComponent', () => {
  let component: TransferirFormComponent;
  let fixture: ComponentFixture<TransferirFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferirFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TransferirFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
