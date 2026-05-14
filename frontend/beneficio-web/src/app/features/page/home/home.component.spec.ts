import { describe, it, expect, beforeEach, vi, afterEach } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    vi.useFakeTimers();
    await fixture.whenStable();
  });

  afterEach(() => {
    if (component.intervalId){
       clearInterval(component.intervalId);
    }
    vi.useRealTimers();
    vi.clearAllTimers();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve intervalo letreiro', () => {
    component.frases.set(["frase 1", "frase 2", "fase 3"]);
    component.index.set(0);

    component.intervaloLetreiro();

    expect(component.index()).toBe(0);

    vi.advanceTimersByTime(5000);
    expect(component.index()).toBe(2);

    vi.advanceTimersByTime(5000);
    expect(component.index()).toBe(1);

    vi.advanceTimersByTime(5000);
    expect(component.index()).toBe(0);
  });
});
