import { Filters } from './filters';
import { Randomizer } from './randomizer';
import { DictionaryItem, ErrorItem } from './error.item.type';
import { Stats } from './stats.type';

export class Quiz {
  public current: number = 0;
  public errors: Array<ErrorItem> = [];
  public whitelist : Array<string> = [];
  public words : Array<any> = [];

	public constructor() {
		this.current = 0;
		this.errors = [];
		this.whitelist = [];
	}

	public setSource(source) {
		this.words = source;
		this.current = 0;
	}

	public stepNext(kotae: string) {
		this.putIntoProperList(kotae);
		if (!this.shouldFinish()) {
			++this.current;
		}
	}

	public putIntoProperList(kotae) {
		const current = this.getCurrentWord();
		const definition = this.getCurrentDefinition();
		const match: boolean = new Filters().wordMatches(definition, kotae);
		if (!match) {
			this.putAtTheBeginningOfErrors({'key':current, 'value':definition, 'actual': kotae});
		} else {
			let accurateDefinition = new Filters().findWordInDefinition(kotae, definition);
			this.whitelist.push(current+'-'+accurateDefinition);
		}
	}

	public putAtTheBeginningOfErrors(item : ErrorItem) {
	  this.errors.unshift(item);
	}

	public shouldFinish() {
		return this.current == this.words.length;
	}

	public getCurrentWord() {
		return this.words[this.current].key;
	}

	public getCurrentDefinition() {
		return this.words[this.current].value;
	}

	public getResults(): Array<ErrorItem> {
		return this.errors;
	}

	public convertResultsIntoTest(): string {
		return this.errors.map(function (item) {
			return item.key+"-"+item.value;
		}).join("\n");
	}

	public getWhitelistAsText() {
		return this.whitelist.join('\n');
	}

	public clearResults() {
		this.errors = [];
		this.whitelist = [];
	}

	public revertWord(word, expected) {
		this.errors = this.errors.filter(function (errorItem) {
			return !new Filters().itemsMatches(errorItem, {'key':word, 'value':expected});
		});
		this.whitelist.push(word+'-'+expected);
	}

  public getStats(): Stats {
    return {
      errors: this.errors.length,
      correct: this.whitelist.length,
      total: this.words.length
    };
  }

  public getDisplayedText() {
    if (!this.shouldFinish()) {
      return this.getCurrentWord();
    } else {
      return "Congrats! You processed all the input. Please save results."
    }
  }
}

export class QuizBuilder {
  public filters = new Filters();
  public randomizer = new Randomizer();

	public buildQuiz(source, whitelists): Quiz {
		let quiz = new Quiz();
		quiz.setSource(this.prepareSource(source, whitelists));
		return quiz;
	}

  private prepareSource(source, whitelists) {
    let filteredSource = source;
    whitelists.forEach(function(whitelist){
        filteredSource = this.filters.filterAlreadyDone(filteredSource, whitelist);
    });
    filteredSource = this.filters.removeDuplicates(filteredSource);
    filteredSource = this.filters.filterJunkText(filteredSource);
		filteredSource = this.randomizer.randomize(source);
    return filteredSource
  }
}
