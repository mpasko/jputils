import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FiletreeComponent } from './filetree.component';
import { TreeModule } from 'angular-tree-component';
import { HttpClientModule } from '@angular/common/http';
import { FolderBrowseComponent } from './folder.browse/folder-browse.component';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    TreeModule.forRoot()
  ],
  declarations: [
    FiletreeComponent,
    FolderBrowseComponent
  ],
  exports: [
    FiletreeComponent
  ]
})
export class FiletreeModule { }
