import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const panelUri = '/api.v2/panel';

@Injectable({
  providedIn: 'root'
})
export class ControlPaneService {
  constructor(private http: HttpClient) { }

  getLayout() {
    return this.http.get(panelUri);
  }

  execute(command: String) {
    this.http.post(`${panelUri}/execute/${command}`, {})
      .subscribe(data => {
             console.log("PUT Request is successful");
         },
         error => {
             console.log("Error", error);
         }
      );
  }
}
