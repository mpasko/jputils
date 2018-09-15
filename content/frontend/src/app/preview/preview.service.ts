import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

const fileContentUrl = '/api.v2/preview/file';

export interface IPreviewService {
  getPreview(id):Observable<string>;
}

@Injectable({
  providedIn: 'root'
})
export class PreviewService implements IPreviewService {
  constructor(private http: HttpClient) { }

  getPreview(id) {
    return this.http.get(fileContentUrl, {params:{id}})
      .pipe(map(data => data['sampleText'])) as Observable<string>;
  }
}
