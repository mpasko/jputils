import { QuizBuilder } from './quiz';

const commonSource = [{
	key: 'word1',
	value: 'alalala,lalac;lala'
},{
	key: 'word2',
	value: 'blablabla'
}];

let quizFactory: QuizBuilder;

beforeEach(function () {
  quizFactory = new QuizBuilder();
  quizFactory.randomizer = {
    randomize: list => list,
    removeAt: () => {},
    selectRandom: () => ({chosen: '', rest: ''})
  };
});

describe('Quiz Engine', function() {
  describe('#stepNext()', function() {
    it('should move pivot to next word', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
	    quizTestable.stepNext('');
	    expect(quizTestable.getCurrentWord()).toBe('word2');
    });

    it('should finish at the last word', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('');
      quizTestable.stepNext('');
      expect(quizTestable.shouldFinish()).toEqual(true);
    });

    it('should not finish after first word', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('');
      expect(quizTestable.shouldFinish()).toEqual(false);
    });

    it('should put wrong word into list', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('blab');
	    expect(quizTestable.getResults().length).toEqual(1, 'expected getResults to be of size 1');
    });

    it('should not put correct word into list', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('alal');
      expect(quizTestable.getResults().length).toEqual(0, 'expected getResults to be empty');
    });

    it('should return results in reverse order', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('');
      quizTestable.stepNext('');
      const results = quizTestable.getResults();
      expect(results.length).toEqual(2);
      expect(results[0].value).toEqual('blablabla');
      expect(results[1].value).toEqual('alalala,lalac;lala');
    });
  });

  describe('#revertWord()', function() {
    it('should reverse bad typed word', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('');
      quizTestable.stepNext('');
      quizTestable.revertWord('word2', 'blablabla');
      const results = quizTestable.getResults();
      expect(results.length).toEqual(1);
      expect(results[0].key).toEqual('word1');
    });

    it('should reverse bad typed word with partial definition', function() {
      const quizTestable = quizFactory.buildQuiz(commonSource, []);
      quizTestable.stepNext('');
      quizTestable.stepNext('');
      quizTestable.revertWord('word1', 'lalac');
      const results = quizTestable.getResults();
      expect(results.length).toEqual(1);
      expect(results[0].key).toEqual('word2');
    });
  });
});
