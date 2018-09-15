import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes }  from '@angular/router';

import { TextPreviewComponent } from '../preview/text-preview/text-preview.component';
import { WordsPreviewComponent } from '../preview/words-preview/words-preview.component';
import { MainComponent } from '../main/main.component';

const routes = [
  {
    path: 'textpreview/:id', // :id means that id will be a URL parameter
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
    path: 'main',
    component: MainComponent
  },
  {
    path: '',
    redirectTo: '/main',
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