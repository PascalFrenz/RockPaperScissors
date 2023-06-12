import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { HandComponent } from './hand/hand.component';
import { ScoreboardComponent } from './scoreboard/scoreboard.component';

@NgModule({
  imports:      [ BrowserModule, FormsModule, HttpClientModule ],
  declarations: [ AppComponent, HandComponent, ScoreboardComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }

