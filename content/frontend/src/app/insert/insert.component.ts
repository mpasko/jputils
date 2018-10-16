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

  constructor(private route: ActivatedRoute,
              private editor: EditorService) {
    this.data = new Asset();
  }

  setData(data: Asset) {
    this.data = data;
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
    this.editor.save(this.data);
  }
}
