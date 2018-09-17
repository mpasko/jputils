import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const searchUrl = "/api.v2/search";

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  constructor(private http: HttpClient) { }

  search(id) {
    return this.http.get(searchUrl, {params:{id}});
  }
}
