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
    console.log('menupane_id:',this.resourceId);
  }

  ngOnInit() {
  }

  public getResourceId(): string {
    this.resourceId = this.routerUtil.getResourceId();
    console.log('menupane_id:',this.resourceId);
    return this.resourceId;
  }

}
