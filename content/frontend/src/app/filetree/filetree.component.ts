import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { RouterUtilService } from '../routing/router-extractor.service';
import { FileTreeService } from './file-tree.service';
import { ProcessorService } from './processor.service.js';
import { FileTree } from './file-tree.type';
import { TreeNode } from 'angular-tree-component';

@Component({
  selector: 'app-filetree',
  templateUrl: './filetree.component.html',
  styleUrls: ['./filetree.component.css']
})
export class FiletreeComponent implements OnInit {

  @ViewChild('treeComponent') treeComponent;
  public nodes;
  public options;

  constructor(private router: Router,
              private routerUtil: RouterUtilService,
              private fileTreeService: FileTreeService,
              private processor: ProcessorService) {
    this.nodes = [];
    this.options = {};
  }

  routeToSelected(node) {
    let site = this.routerUtil.getSubSite();
    if (site.indexOf('main')>=0) {
      site = 'wordspreview';
    }
    console.log('Routing to:', site, node.data.name);
    this.router.navigate([site, node.data.name]);
  }

  onActivate(event) {
    if (event.node.children === undefined) {
      this.routeToSelected(event.node);
    } else {
      event.node.expand();
    }
  }

  onDeactivate(event) {
    if (event.node.children !== undefined) {
      //event.node.collapse();
    }
  }

  ngOnInit() {
    this.fileTreeService
      .getStructure()
      .subscribe((data) => this.nodes = this.processor.process(data as FileTree));
  }

}
