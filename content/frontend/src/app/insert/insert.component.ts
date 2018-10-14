import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { EditorService } from './editor.service';
import { Asset } from './asset.type';

@Component({
  selector: 'app-insert',
  templateUrl: './insert.component.html',
  styleUrls: ['./insert.component.css']
})
export class InsertComponent implements OnInit {

  public data: Asset;
  public directory: String;
  public name: String;

  constructor(private route: ActivatedRoute,
              private editor: EditorService) {
    this.data = {
      english: '',
      japanese: '',
      name: ''
    } as Asset;
    this.directory = '';
    this.name = '';
  }

  setData(data: Asset) {
    this.data = data;
    const path = data.name.split('/');
    this.directory = path.splice(0, path.length-1).join('/');
    this.name = path[0];
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params['params'].id;
      console.log(id);
      if (id) {
        this.editor.getCurrent(id)
        .subscribe(this.setData.bind(this));
      }
    });
  }

  save() {

  }
}
