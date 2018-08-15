import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes }  from '@angular/router';

import { PreviewComponent } from '../preview/preview.component';
import { MainComponent } from '../main/main.component';

const routes = [
  {
    path: 'preview/:id', // :id means that id will be a URL parameter
    component: PreviewComponent
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
