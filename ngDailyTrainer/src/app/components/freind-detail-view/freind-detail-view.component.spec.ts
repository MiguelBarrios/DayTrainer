import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FreindDetailViewComponent } from './freind-detail-view.component';

describe('FreindDetailViewComponent', () => {
  let component: FreindDetailViewComponent;
  let fixture: ComponentFixture<FreindDetailViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FreindDetailViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FreindDetailViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
