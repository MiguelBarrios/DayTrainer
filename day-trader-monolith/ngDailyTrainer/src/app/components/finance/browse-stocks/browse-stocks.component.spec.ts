import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseStocksComponent } from './browse-stocks.component';

describe('BrowseStocksComponent', () => {
  let component: BrowseStocksComponent;
  let fixture: ComponentFixture<BrowseStocksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BrowseStocksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrowseStocksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
