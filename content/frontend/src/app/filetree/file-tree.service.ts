import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileTree } from './file-tree.type';

const fileTreeUrl = '/api.v2/textpreview/dir';

@Injectable({
  providedIn: 'root'
})
export class FileTreeService {
  constructor(private http: HttpClient) { }

  getStructure(): Observable<FileTree> {
    return this.http.get(fileTreeUrl) as Observable<FileTree>;
  }
}
