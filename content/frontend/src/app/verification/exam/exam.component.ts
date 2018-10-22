import { Component, OnInit, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Mistake } from './mistake.type';
import { convertToNode, convertFromNode } from './converters';

import { Quiz, QuizBuilder } from '../examinator/quiz';
import { ExamHttpService } from '../exam.http.service';
import { Stats } from '../examinator/stats.type';

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.css']
})
export class ExamComponent implements OnInit {

  public stats: Stats = {
    errors: 0,
    correct: 0,
    total: 0
  };
  public mistakes: Array<Mistake> = [];
  public meaning: string = "";
  public current: string = "";
  private nodeService: Quiz = new Quiz();

  private resourceId: string;
  private activity: string;
  private phase: string;

  @ViewChild('meaningInput') private input:ElementRef;

  constructor(
    private route: ActivatedRoute,
    private http: ExamHttpService
  ) { }

  private storeConfiguration(params) {
    this.resourceId = params['params'].id;
    this.activity = params['params'].activity;
    this.phase = params['params'].phase;
  }

  private fetchData() {
    this.http.getExam(this.activity, this.resourceId, this.phase)
      .subscribe((quiz) => {
        this.nodeService = new QuizBuilder().buildQuiz(convertToNode(quiz), []);
        this.rewriteStats();
      });
  }

  ngOnInit() {
    this.input.nativeElement.focus();
    this.route.paramMap.subscribe(params => {
      this.storeConfiguration(params);
      this.fetchData();
    });
  }

  shouldFinish(): boolean{
    return this.nodeService.shouldFinish();
  }

  next() {
    if (this.meaning === undefined) {
      this.meaning = "";
    }
    this.nodeService.stepNext(this.meaning);
    this.rewriteStats();
    this.meaning = "";
  }

  saveResults() {
    const black = this.nodeService.convertResultsIntoTest();
    const white = this.nodeService.getWhitelistAsText();
    this.http.saveResults(this.activity, this.resourceId, black, white);
  }

  cancel() {

  }

  revertWord(mistake: Mistake) {
    this.nodeService.revertWord(mistake.word, mistake.expected);
    this.rewriteStats();
  }

  inputKeyPress(event) {
    if (event.code === 'Enter') {
      this.next();
    }
  }

  private rewriteStats() {
    this.stats = this.nodeService.getStats();
    this.mistakes = convertFromNode(this.nodeService.getResults());
    if (!this.nodeService.shouldFinish()) {
      this.current = this.nodeService.getCurrentWord();
    }
  };
}
