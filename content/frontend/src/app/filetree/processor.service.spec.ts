import { TestBed, inject } from '@angular/core/testing';

import { ProcessorService } from './processor.service';
import { FileTree, Node } from './file-tree.type';

describe('ProcessorService', () => {
  beforeEach(() => {
  });

  it('should process tree', () => {
    const testable = new ProcessorService();
    const customTree = {
      name: 'root',
      subnodes: [
        {
          name: 'parent1',
          subleafs: [{name: 'child1'}, {name: 'child2'}]
        },
        {
          name: 'parent2',
          subleafs: [];
        }
      ]
    } as FileTree;
    const result = testable.process(customTree);
    expect(result.length).toBe(1);
    expect(result[0].children).toBeDefined();
    expect(result[0].children.length).toBe(2);
  });
});
