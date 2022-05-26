import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleStockViewComponent } from './single-stock-view.component';

describe('SingleStockViewComponent', () => {
  let component: SingleStockViewComponent;
  let fixture: ComponentFixture<SingleStockViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleStockViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleStockViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
