import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const insertUrl = "/api.v2/insert";

@Injectable({
  providedIn: 'root'
})
export class InsertService {

  constructor(private http: HttpClient) { }

  insert(path) {
    return this.http.get(insertUrl, {params:{path}});
  }
}
