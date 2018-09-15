import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot } from '@angular/router';

function extractFragments(uri) {
  return uri.split('/')
    .filter(fragment => fragment.length > 0)
    .filter(fragment => fragment.indexOf('#') < 0);
}

function extractParameter(uri, parameterIndex) {
  const fragments = extractFragments(uri);
  const expectedSize = parameterIndex+1;
  return (fragments.length < expectedSize) ? "" : fragments[parameterIndex];
}

@Injectable({
  providedIn: 'root'
})
export class RouterUtilService {

  router: Router;

  constructor(router : Router) {
    console.log('injecting:', router);
    this.router = router;
  }

  getCurrentUri(): string {
    return this.router.routerState.snapshot.url;
  }

  getSubSite(): string {
    const subSite = extractParameter(this.getCurrentUri(), 0);
    const expectedRoutes = ['textpreview', 'wordspreview'];
    const contains = expectedRoutes.some(route => subSite.indexOf(route)>=0)
    return contains ? subSite : expectedRoutes[0];
  }

  getResourceId(): string {
    return extractParameter(this.getCurrentUri(), 1);
  }
}
