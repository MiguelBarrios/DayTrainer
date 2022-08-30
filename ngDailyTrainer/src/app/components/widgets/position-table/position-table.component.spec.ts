import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PositionTableComponent } from './position-table.component';

describe('PositionTableComponent', () => {
  let component: PositionTableComponent;
  let fixture: ComponentFixture<PositionTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PositionTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PositionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
