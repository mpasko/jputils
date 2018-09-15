import { Component, OnInit, Input } from '@angular/core';
import { Word } from '../words-preview.service';

@Component({
  selector: 'app-word-item',
  templateUrl: './word-item.component.html',
  styleUrls: ['./word-item.component.css']
})
export class WordItemComponent implements OnInit {

  @Input() words: Array<Word>;

  constructor() { }

  ngOnInit() {
  }

}
