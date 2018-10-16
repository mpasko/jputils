import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Asset } from './asset.type';

const editUrl = '/api.v2/editor';

@Injectable({
  providedIn: 'root'
})
export class EditorService {
  constructor(private http: HttpClient) { }

  getCurrent(id) {
    return this.http.get(editUrl, {params:{id}});
  }

  save(asset: Asset) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    return this.http.put<Asset>(editUrl, asset, httpOptions)
      .subscribe(
        data => {
            console.log("PUT Request is successful");
        },
        error => {
            console.log("Error", error);
        }
      );
  }
}
