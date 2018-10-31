import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const quizUrl = '/api.v2/quizdownload';

@Injectable({
  providedIn: 'root'
})
export class DownloadService {

  constructor(private http: HttpClient) { }

  getQuizData(id, activity, phase): Observable<string> {
    return this.http.get(`${quizUrl}/${id}/${activity}/${phase}`)
      .pipe(map(data => data['sampleText'])) as Observable<string>;
  }
}
