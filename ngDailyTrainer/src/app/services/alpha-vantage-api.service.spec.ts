import { TestBed } from '@angular/core/testing';

import { AlphaVantageAPIService } from './alpha-vantage-api.service';

describe('AlphaVantageAPIService', () => {
  let service: AlphaVantageAPIService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlphaVantageAPIService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
