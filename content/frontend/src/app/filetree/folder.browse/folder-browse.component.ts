import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { FileTreeService } from '../file-tree.service';
import { ProcessorService } from '../processor.service';

import { FileTree } from '../file-tree.type';

@Component({
  selector: 'app-folder-browse',
  templateUrl: './folder-browse.component.html',
  styleUrls: ['./folder-browse.component.css']
})
export class FolderBrowseComponent implements OnInit {

  private resourceId: string;
  public currentNode: FileTree = {name:"", subnodes: [], subleafs:[]};

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private treeDataSource: FileTreeService,
    private processor: ProcessorService
  ) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.resourceId = params['params'].id;
      this.treeDataSource.getStructure().subscribe(this.acceptData.bind(this));
    });
  }

  private acceptData(tree: FileTree) {
    this.currentNode = this.processor.findNode(tree, this.resourceId);
  }

  public gotoFile(name: string) {
    this.router.navigate(['textpreview', name]);
  }

  public gotoDirectory(name: string) {
    this.router.navigate(['browse', name]);
  }

  public newFile() {
    this.router.navigate(['insert', this.resourceId]);
  }

  public newDirectory() {

  }
}
