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
    this.router = router;
  }

  getCurrentUri(): string {
    return this.router.routerState.snapshot.url;
  }

  getSubSite(): string {
    const uri = this.getCurrentUri();
    console.log(`Extractor: uri = ${uri}`);
    const subSite = extractParameter(uri, 0);
    const expectedRoutes = ['textpreview', 'wordspreview'];
    const contains = expectedRoutes.some(route => subSite.indexOf(route)>=0)
    const subSiteOrDefault = contains ? subSite : expectedRoutes[0];
    console.log(`Extractor: subSite = ${subSite}`);
    return subSiteOrDefault;
  }

  getResourceId(): string {
    return extractParameter(this.getCurrentUri(), 1);
  }
}
