import { NgModule } from '@angular/core';
import { FiletreeComponent } from './filetree.component';
import { TreeModule } from 'angular-tree-component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    HttpClientModule,
    TreeModule.forRoot()
  ],
  declarations: [
    FiletreeComponent
  ],
  exports: [
    FiletreeComponent
  ]
})
export class FiletreeModule { }
