import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopMoversComponent } from './top-movers.component';

describe('TopMoversComponent', () => {
  let component: TopMoversComponent;
  let fixture: ComponentFixture<TopMoversComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopMoversComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TopMoversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
