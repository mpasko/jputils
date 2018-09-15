import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const wordsUrl = '/api.v2/wordspreview';

@Injectable({
  providedIn: 'root'
})
export class WordsPreviewService {

  constructor(private http: HttpClient) { }

  getPreview(id) {
    return this.http.get(wordsUrl, {params:{id}});
  }
}

export class Word {
  kanji: string;
  writing: string;
  english: string;
}
