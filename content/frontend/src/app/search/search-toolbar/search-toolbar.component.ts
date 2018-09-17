import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-toolbar',
  templateUrl: './search-toolbar.component.html',
  styleUrls: ['./search-toolbar.component.css']
})
export class SearchToolbarComponent implements OnInit {

  private query: string;

  constructor(private router : Router) { }

  ngOnInit() {
  }

  onKey(event) {
    this.query = event.target.value;
  }

  public performSearch(event) {
    this.router.navigate(['search', this.query]);
  }
}
