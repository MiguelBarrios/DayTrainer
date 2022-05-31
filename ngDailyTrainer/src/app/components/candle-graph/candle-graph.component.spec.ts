import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandleGraphComponent } from './candle-graph.component';

describe('CandleGraphComponent', () => {
  let component: CandleGraphComponent;
  let fixture: ComponentFixture<CandleGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CandleGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CandleGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
