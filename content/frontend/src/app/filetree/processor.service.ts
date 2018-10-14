import { Injectable } from '@angular/core';
import { FileTree } from './file-tree.type';

function mergeSafe<T>(array1: Array<T>, array2: Array<T>): Array<T> {
  return (array1 || []).concat(array2 || []);
}

@Injectable({
  providedIn: 'root'
})
export class ProcessorService {

  constructor() { }

  processData(data: FileTree, enumerator) {
    const valueForCurrent = enumerator.value++;
    const mergedSubordinates: Array<FileTree>
      = mergeSafe(data.subnodes , data.subleafs);
    const processedChildren
      = mergedSubordinates.map(array => this.processData(array, enumerator));
    return {
      id : valueForCurrent,
      name : data.name || 'root',
      children : (processedChildren.length == 0 ? undefined : processedChildren)
    };
  }

  process(data: FileTree) {
    return [this.processData(data, {value: 0})];
  }
}
