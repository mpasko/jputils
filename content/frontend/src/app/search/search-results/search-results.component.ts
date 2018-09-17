import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { SearchServiceService } from '../search-service.service';

function convertResultIntoView(result: any): Array<SearchResult> {
  return Object.keys(result).map(key => ({
    path: key,
    value: result[key]
  }));
}

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  public results: Array<SearchResult>;

  constructor(private service: SearchServiceService,
              private route: ActivatedRoute,) {
    this.route.paramMap.subscribe(params => {
      const query =  params['params'].query;
      this.service.search(query)
      .subscribe(results => {
        console.log(results);
        this.results = convertResultIntoView(results);
      });
    });
  }

  ngOnInit() {
  }
}

export class SearchResult {
  path: string;
  value: string;
}
