import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
//import { Observable } from 'rxjs';

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

  save(directory:String, name: String, asset: Asset) {
    //const httpParams = {params:{dir:directory}};
    return this.http.put<Asset>(editUrl, asset);
  }
}
