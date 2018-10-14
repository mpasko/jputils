import { Component, OnInit } from '@angular/core';
import { InsertService } from './insert.service';

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css']
})
export class InsertComponent implements OnInit {

  public japanese: string;
  public english: string;

  constructor(private insert : InsertService) { }

  ngOnInit() {
  }

  save() {

  }
}
