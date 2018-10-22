import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Question } from './exam/question.type';

export interface Results {
  correct: any;
  incorrect: any;
}

export interface Combinations {
  phases: Array<string>;
  activities: Array<string>;
}

@Injectable({
  providedIn: 'root'
})
export class ExamHttpService {

  constructor(private http: HttpClient) { }

  public getExam(activity, id, phase): Observable<Array<Question>> {
    const depth = 'subitem';
    return this.http.get('api/exam/'+depth+'/'+activity+'/'+id+'/'+phase) as Observable<Array<Question>>;
  };

  public getCombinations(): Observable<Combinations> {
    return this.http.get('api/exam/combinations') as Observable<Combinations>;
  };

  public saveResults(activity, name, black, white) {
    const results = {
      correct: white,
      incorrect: black
    } as Results;
    this.http.put<Results>('api/save-results/'+activity+'/'+name, results)
    .subscribe(
      data => {
          console.log("PUT Request is successful");
      },
      error => {
          console.log("Error", error);
      }
    );
  };
}
