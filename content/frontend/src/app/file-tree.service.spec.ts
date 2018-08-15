import { TestBed, inject } from '@angular/core/testing';

import { FileTreeService } from './file-tree.service';

describe('FileTreeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FileTreeService]
    });
  });

  it('should be created', inject([FileTreeService], (service: FileTreeService) => {
    expect(service).toBeTruthy();
  }));
});
