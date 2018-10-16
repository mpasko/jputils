import { Component, OnInit } from '@angular/core';
import { ControlPaneService } from './control-pane.service';
import { Panel, Category, Control } from './layout.type';

@Component({
  selector: 'app-control-pane',
  templateUrl: './control-pane.component.html',
  styleUrls: ['./control-pane.component.css']
})
export class ControlPaneComponent implements OnInit {

  public layout: Panel;

  constructor(private service: ControlPaneService) {
    this.layout = {categories: []};
  }

  onFire(command: String) {
    this.service.execute(command);
  }

  ngOnInit() {
    this.service.getLayout().subscribe(panel => this.layout = panel as Panel);
  }

}
