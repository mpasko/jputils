import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RoutingModule } from './routing/routing.module';

import { AppComponent } from './app.component';
import { FiletreeModule } from './filetree/filetree.module';
import { MainComponent } from './main/main.component';
import { WordsPreviewComponent } from './preview/words-preview/words-preview.component';
import { TextPreviewComponent } from './preview/text-preview/text-preview.component';
import { MenuPaneComponent } from './menu-pane/menu-pane.component';
import { WordItemComponent } from './preview/words-preview/word-item/word-item.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    WordsPreviewComponent,
    TextPreviewComponent,
    MenuPaneComponent,
    WordItemComponent
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
