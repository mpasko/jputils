import { TestBed, inject } from '@angular/core/testing';
import { Router } from '@angular/router';

import { RouterUtilService } from './router-extractor.service';

const routerMock = {
  routerState: {
    snapshot:{
      url:""
    }
  }
};

function reconfigureRouterMock(newUrl: string) {
  routerMock.routerState.snapshot.url=newUrl;
}

describe('RouterUtilService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RouterUtilService,
      {provide: Router, use: routerMock}]
    });
  });

  it('should be created', inject([RouterUtilService], (service: RouterUtilService) => {
    expect(service).toBeTruthy();
  }));

  describe('getSubSite', function () {
    it('should return identifier', inject([RouterUtilService], (service: RouterUtilService) => {
      reconfigureRouterMock('/textpreview/12');
      const testable = new RouterUtilService(routerMock as Router);
      expect(testable.getResourceId()).toBe('12');
    }));

    it('should handle nulls', inject([RouterUtilService], (service: RouterUtilService) => {
      reconfigureRouterMock('/textpreview');
      const testable = new RouterUtilService(routerMock as Router);
      expect(testable.getResourceId()).toBe("");
    }));
  });

  describe('getSubSite', function () {
    it('should have default value', function () {
      reconfigureRouterMock('');
      const testable = new RouterUtilService(routerMock as Router);
      expect(testable.getSubSite()).toBe("textpreview");
    });
  });
});
