import { Filters } from './filters';

const commonSource = [{
	key: 'word1',
	value: 'alalala,lalac;lala'
},{
	key: 'word2',
	value: 'blablabla'
}];

describe('wordMatches', function() {
  it('should match when contains substring', function() {
	  const testedFunction = new Filters().wordMatches;
	  const result = testedFunction('ala ma kota', 'kota');
	  expect(result).toEqual(true);
  });
  //Disable for the sake of phonetic tests
  /*
  it('should not match too easy substring', function() {
	  const testedFunction = new Filters().wordMatches;
	  const result = testedFunction('ala ma kota', 'ma');
	  expect(result).toEqual(false);
  });
  */
  it('should match one of coma separated values', function() {
	  const testedFunction = new Filters().wordMatches;
	  const result = testedFunction('ala ma kota', 'ta,ma,na');
	  expect(result).toEqual(true);
  });
});

describe('findWordInDefinition', function() {
  it('should correctly extract word', function() {
	  const testedFunction = new Filters().findWordInDefinition;
	  const result = testedFunction('kota','sierotka marysia, ala ma kota');
	  expect(result).toEqual('ala ma kota');
  });
});

describe('filterAlreadyDone', function() {
  it('should not filter not matching', function() {
	  const testedFunction = new Filters().filterAlreadyDone;
	  const query = [{key:'word2', value:'lalac'}];
	  const results = testedFunction(commonSource,query);
	  expect(results.length).toEqual(2);
  });

  it('should filter matching exact', function() {
	  const testedFunction = new Filters().filterAlreadyDone;
	  const query = [{key:'word1', value:'alalala,lalac;lala'}];
	  const results = testedFunction(commonSource,query);
	  expect(results.length).toEqual(1);
	  expect(results[0].key).toEqual('word2');
  });

  it('should filter matching subset', function() {
	  const testedFunction = new Filters().filterAlreadyDone;
	  const query = [{key:'word1', value:'lalac'}];
	  const results = testedFunction(commonSource,query);
	  expect(results.length).toEqual(1);
	  expect(results[0].key).toEqual('word2');
  });
});

describe('removeDuplicates', function() {
  it("should preserve list without dups", function() {
    const testedFunction = new Filters().removeDuplicates;
    const results = testedFunction(commonSource);
    expect(results).toEqual(commonSource);
  });

  it("should remove dups multiple times", function() {
    const testedFunction = new Filters().removeDuplicates;
    const array = [];
    array.push(commonSource[0]);
    array.push(commonSource[1]);
    array.push(commonSource[1]);
    array.push(commonSource[0]);
    const results = testedFunction(array);
    expect(results).toEqual(commonSource);
  });
});

describe('filterJunkText', function() {
  it("should preserve list without changes", function() {
    const testedFunction = new Filters().filterJunkText;
    const results = testedFunction(commonSource);
    expect(results).toEqual(commonSource);
  });

  it("should filter bizarre characters", function() {
    const testedFunction = new Filters().filterJunkText;
    const results = testedFunction([{key:'ala', value:'"ala" ma \'kota\''}]);
    expect(results[0].value).toEqual('ala ma kota');
  });
});
