import { Randomizer } from './randomizer';

const size = 5;
const tableForTests = [1,2,3,4,5];

describe('Randomizer', function() {
  describe('#removeAt()', function() {
    it('should remove element at first position', function() {
      const rest = new Randomizer().removeAt(tableForTests, 0);
      expect(rest).toEqual([2,3,4,5]);
    });

    it('should remove element at last position', function() {
      const rest = new Randomizer().removeAt(tableForTests, 4);
      expect(rest).toEqual([1,2,3,4]);
    });
  });

  describe('#randomize()', function () {
    it('should be of the same size', function (){
      const random = new Randomizer().randomize(tableForTests);
      expect(random.length).toEqual(size);
    });

    it('should contain all elements from source', function (){
      const random = new Randomizer().randomize(tableForTests);
      tableForTests.forEach(function (elem) {
        expect(random.some(randomized => randomized == elem)).toBeTruthy();
      });
    });
  });

  describe('#selectRandom()', function () {
    it('should always divide array into separate parts', function () {
      const split = new Randomizer().selectRandom(tableForTests);
      expect(split.rest.length).toEqual(size - 1);
      split.rest.forEach(item => expect(item).not.toEqual(split.chosen));
    });

    it('should behave gratefully when last element', function () {
      const split = new Randomizer().selectRandom(['lastSamurai']);
      expect(split.rest).toEqual([]);
      expect(split.chosen).toEqual('lastSamurai');
    });
  });
});
