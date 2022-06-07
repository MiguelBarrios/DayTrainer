import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoggedInNavComponent } from './logged-in-nav.component';

describe('LoggedInNavComponent', () => {
  let component: LoggedInNavComponent;
  let fixture: ComponentFixture<LoggedInNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoggedInNavComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoggedInNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
