import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { FileTreeService } from '../file-tree.service';


function processData(data, enumerator) {
  enumerator.value++;
  const mergeSafe = (array1, array2) => (array1 || []).concat(array2 || []);
  const children = mergeSafe(data['subnodes'], data['subleafs'])
    .map(array => processData(array, enumerator));
  const name = data.name || 'root';
  var transformed;
  if (children.length > 0) {
    transformed = {
      id : enumerator.value,
      name,
      children
    };
  } else {
    transformed = {
      id : enumerator.value,
      name
    };
  }
  return transformed;
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
              private filetreeservice : FileTreeService) {
    this.nodes = [ //id,name,children
      ];
    this.options = {};
  }

  onFocus(event) {
    if (event.node.children === undefined) {
      this.router.navigate(['/preview', event.node.data.name]);
    }
  }

  ngOnInit() {
    this.filetreeservice
      .getStructure()
      .subscribe((data) => this.nodes = [processData(data, {value: 0})]);
  }

}
