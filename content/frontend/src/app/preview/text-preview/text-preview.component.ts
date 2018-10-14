import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PreviewService } from './text-preview.service';

@Component({
  selector: 'app-preview',
  templateUrl: './text-preview.component.html',
  styleUrls: ['./text-preview.component.css']
})
export class TextPreviewComponent implements OnInit {

  sampleText = ['a','b','c'];

  constructor(private route: ActivatedRoute,
    private preview: PreviewService) { }

  processText(text) {
    this.sampleText = text.split('\n');
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id =  params['params'].id;
      this.preview.getPreview(id)
        .subscribe(text => this.processText(text));
    });
  }

}
