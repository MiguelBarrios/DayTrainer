import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeTableComponent } from './trade-table.component';

describe('TradeTableComponent', () => {
  let component: TradeTableComponent;
  let fixture: ComponentFixture<TradeTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradeTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TradeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
