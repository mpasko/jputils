import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes }  from '@angular/router';

import { TextPreviewComponent } from '../preview/text-preview/text-preview.component';
import { WordsPreviewComponent } from '../preview/words-preview/words-preview.component';
import { SearchResultsComponent } from '../search/search-results/search-results.component';
import { MainComponent } from '../main/main.component';
import { InsertComponent } from '../insert/insert.component';
import { ControlPaneComponent } from '../control-pane/control-pane.component';
import { ExamComponent } from '../verification/exam/exam.component';
import { FolderBrowseComponent } from '../filetree/folder.browse/folder-browse.component'

const routes = [
  {
    path: 'textpreview/:id',
    component: TextPreviewComponent
  },
  {
    path: 'textpreview',
    component: TextPreviewComponent
  },
  {
    path: 'wordspreview/:id',
    component: WordsPreviewComponent
  },
  {
    path: 'wordspreview',
    component: WordsPreviewComponent
  },
  {
    path: 'insert/:id',
    component: InsertComponent
  },
  {
    path: 'insert',
    component: InsertComponent
  },
  {
    path: 'exam/:id/:activity/:phase',
    component: ExamComponent
  },
  {
    path: 'search/:query',
    component: SearchResultsComponent
  },
  {
    path: 'panel',
    component: ControlPaneComponent
  },
  {
    path: 'browse/:id',
    component: FolderBrowseComponent
  },
  {
    path: '',
    redirectTo: '/wordspreview',
    pathMatch: 'full' //for redirectios this parameter is mandatory
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  declarations: []
})
export class RoutingModule { }
