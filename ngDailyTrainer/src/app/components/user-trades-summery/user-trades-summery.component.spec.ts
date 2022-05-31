import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTradesSummeryComponent } from './user-trades-summery.component';

describe('UserTradesSummeryComponent', () => {
  let component: UserTradesSummeryComponent;
  let fixture: ComponentFixture<UserTradesSummeryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserTradesSummeryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTradesSummeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
