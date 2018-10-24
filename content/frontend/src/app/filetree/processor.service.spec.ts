import { TestBed, inject } from '@angular/core/testing';

import { ProcessorService } from './processor.service';
import { FileTree } from './file-tree.type';

describe('ProcessorService', () => {
  beforeEach(() => {
  });

  const customTree = {
    name: 'root',
    subnodes: [
      {
        name: 'parent1',
        subnodes: [],
        subleafs: [
          {
            name: 'child1',
            subnodes: [],
            subleafs: []
          }, {
            name: 'child2',
            subnodes: [],
            subleafs: []
          }]
      },
      {
        name: 'parent2',
        subnodes: [],
        subleafs: []
      }
    ],
    subleafs: []
  } as FileTree;

  it('should process tree', () => {
    const testable = new ProcessorService();
    const result = testable.process(customTree);
    expect(result.length).toBe(1);
    expect(result[0].children).toBeDefined();
    expect(result[0].children.length).toBe(2);
  });

  it('should not duplicate keys', function () {
    const testable = new ProcessorService();
    const result = testable.process(customTree);
    const root = result[0];
    const parent2 = root.children[1];
    expect(root.id).not.toEqual(parent2.id);
  });

  describe('findNode', function () {
    function successfullRoutine(keyword) {
      const testableProcessor = new ProcessorService();
      const result = testableProcessor.findNode(customTree, keyword);
      expect(result).not.toBe(null, `for keyword ${keyword}`);
      expect(result.name).toBe(keyword);
    }

    function unsuccessfullRoutine(keyword) {
      const testableProcessor = new ProcessorService();
      const result = testableProcessor.findNode(customTree, keyword);
      expect(result).toBe(null);
    }

    it('should find existing', function () {
      successfullRoutine('root');
      successfullRoutine('parent1');
      successfullRoutine('parent2');
      successfullRoutine('child1');
    });

    it('should not find nonexisting', function () {
      unsuccessfullRoutine('fit');
      unsuccessfullRoutine('');
      unsuccessfullRoutine('parent3');
    });
  });
});
