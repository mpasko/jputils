import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RoutingModule } from './routing/routing.module';

import { AppComponent } from './app.component';
import { FiletreeModule } from './filetree/filetree.module';
import { PreviewComponent } from './preview/preview.component';
import { MainComponent } from './main/main.component';

@NgModule({
  declarations: [
    AppComponent,
    PreviewComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    FiletreeModule,
    RouterModule,
    RoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
