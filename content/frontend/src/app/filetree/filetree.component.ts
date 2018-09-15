import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { RouterUtilService } from '../routing/router-extractor.service';
import { FileTreeService } from './file-tree.service';

const mergeSafe = (array1, array2) => (array1 || []).concat(array2 || []);

function processData(data, enumerator) {
  enumerator.value++;
  const children = mergeSafe(data['subnodes'], data['subleafs'])
    .map(array => processData(array, enumerator));
  return {
    id : enumerator.value,
    name : data.name || 'root',
    children : (children.length == 0 ? undefined : children)
  };
}

@Component({
  selector: 'app-filetree',
  templateUrl: './filetree.component.html',
  styleUrls: ['./filetree.component.css']
})
export class FiletreeComponent implements OnInit {

  public nodes;
  public options;

  constructor(private router : Router,
              private routerUtil : RouterUtilService,
              private filetreeservice : FileTreeService) {
    this.nodes = [ //id,name,children
      ];
    this.options = {};
  }

  onFocus(event) {
    if (event.node.children === undefined) {
      const site = this.routerUtil.getCurrentUri();
      console.log(site, event.node.data.name);
      this.router.navigate(['wordspreview', event.node.data.name]);
    }
  }

  ngOnInit() {
    this.filetreeservice
      .getStructure()
      .subscribe((data) => this.nodes = [processData(data, {value: 0})]);
  }

}
