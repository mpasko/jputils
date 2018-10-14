import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WordsPreviewService, Word } from './words-preview.service';

@Component({
  selector: 'app-words-preview',
  templateUrl: './words-preview.component.html',
  styleUrls: ['./words-preview.component.css']
})
export class WordsPreviewComponent implements OnInit {

  public phases: Array<string> = ['unprocessed', 'black', 'white'];
  public activities: Array<string> = ['reading', 'listening'];
  public data;

  constructor(private route: ActivatedRoute,
    private preview: WordsPreviewService) { }

  getWordsOf(activity: string, phase: string): Array<Word> {
    if (this.data) {
      return this.data[activity][phase] as Array<Word>;
    }
    return [];
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id =  params['params'].id;
      if (id) {
        this.preview.getPreview(id)
        .subscribe(data => this.data = data);
      }
    });
  }

}
