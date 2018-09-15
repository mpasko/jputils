import { Component, OnInit } from '@angular/core';

import { RouterUtilService } from '../routing/router-extractor.service';

@Component({
  selector: 'app-menu-pane',
  templateUrl: './menu-pane.component.html',
  styleUrls: ['./menu-pane.component.css']
})
export class MenuPaneComponent implements OnInit {
  public resourceId;

  constructor(private routerUtil: RouterUtilService) {
    this.resourceId = this.routerUtil.getResourceId();
  }

  ngOnInit() {
  }

}
