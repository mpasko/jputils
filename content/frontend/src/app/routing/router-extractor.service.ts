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

  getCurrentUri(): String {
    return this.router.routerState.snapshot.url;
  }

  extract(index): String {
    const uri = this.getCurrentUri();
    console.log(`Extractor: uri = ${uri}`);
    const parameter = extractParameter(uri, index);
    return parameter;
  }

  getSubSite(): String {
    const subSite = this.extract(0);
    const expectedRoutes = ['textpreview', 'wordspreview', 'insert', 'search'];
    const contains = expectedRoutes.some(route => subSite.indexOf(route)>=0)
    const subSiteOrDefault = contains ? subSite : expectedRoutes[0];
    console.log(`Extractor: subSite = ${subSite}`);
    return subSiteOrDefault;
  }

  getResourceId(): String {
    return extractParameter(this.getCurrentUri(), 1);
  }
}
