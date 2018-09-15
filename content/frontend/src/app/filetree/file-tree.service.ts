import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const fileTreeUrl = '/api.v2/textpreview/dir';

@Injectable({
  providedIn: 'root'
})
export class FileTreeService {
  constructor(private http: HttpClient) { }

  getStructure() {
    return this.http.get(fileTreeUrl);
  }
}
