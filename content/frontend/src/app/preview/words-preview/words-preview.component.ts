import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { WordsPreviewService, Word } from './words-preview.service';
import { ExamHttpService } from '../../verification/exam.http.service';

export interface SummaryItem {
  activity: string;
  phase: string;
  size: number;
}

@Component({
  selector: 'app-words-preview',
  templateUrl: './words-preview.component.html',
  styleUrls: ['./words-preview.component.css']
})
export class WordsPreviewComponent implements OnInit {

  public phases: Array<string> = [];
  public activities: Array<string> = [];
  public data;
  public summary: Array<SummaryItem> = [];
  public resourceId: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private preview: WordsPreviewService,
    private examHttp: ExamHttpService
  ) { }

  getWordsOf(activity: string, phase: string): Array<Word> {
    if (this.data) {
      return this.data[activity][phase] as Array<Word>;
    }
    return [];
  }

  ngOnInit() {
    this.examHttp.getCombinations().subscribe((combinations) => {
        this.phases = combinations.phases;
        this.activities = combinations.activities;
      });
    this.route.paramMap.subscribe(params => {
      this.resourceId =  params['params'].id;
      if (this.resourceId) {
        this.preview.getPreview(this.resourceId)
        .subscribe(data => {
          this.data = data;
          this.generateSummary();
        });
      }
    });
  }

  makeExam(activity, resourceId, phase) {
    this.router.navigate(['exam', resourceId, activity, phase]);
  }

  private generateSummary() {
    this.summary = [];
    this.activities.forEach(activity => {
      this.phases.forEach(phase => {
        this.summary.push({
          phase,
          activity,
          size: this.data[activity][phase].length
        } as SummaryItem);
      });
    });
  }
}
