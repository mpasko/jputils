(function(){function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s}return e})()({1:[function(require,module,exports){
function wordMatches(wordDefinition, kotae) {
    return kotae.split(/,|;/)
    .filter((word) => wordMatchesElementary(wordDefinition, word.trim()))
    .length > 0;
}

function wordMatchesElementary(wordDefinition, kotae) {
	return (wordDefinition.indexOf(kotae) >= 0) && (kotae.length > 1);
}

function findWordInDefinition(word, definition) {
	var findMatchingDefinition = (def) => wordMatches(def, word)
	return definition
		.split(/,|;/)
		.filter(findMatchingDefinition)[0]
		.trim();
}

function filterAlreadyDone(source, whitelist) {
	return source.filter(function (sourceItem){
		return !whitelist.some((whiteItem) => itemsMatches(sourceItem, whiteItem));
	});
}

function itemsMatches(sourceItem, whiteItem) {
	return wordMatches(sourceItem.value, whiteItem.value) && sourceItem.key == whiteItem.key;
}

function removeDuplicates(list) {
    var filtered = [];
    list.forEach(function (item) {
        var shouldPut = !listContainsExact(filtered, item);
        if (shouldPut) {
            filtered.push(item);
        }
    });
    return filtered;
}

function listContainsExact(list, item) {
    return list.filter(function (listItem) {
        return matchesExact(listItem, item);
    }).length > 0;
}

function matchesExact(first, second) {
    return first.key == second.key && first.value == second.value;
}

function filterJunkText(source) {
    return source.map(function(item) {
        return {
            key: filterText(item.key),
            value: filterText(item.value)
        };
    });
}

var banned = ['\'', '"'];

function filterText(text) {
    var filtered = text;
    banned.forEach(function (bannedChar) {
        filtered = filtered.replace(new RegExp(bannedChar, 'ig'), "");
    });
    return filtered;
}

module.exports = {
	'wordMatches' : wordMatches,
	'findWordInDefinition' : findWordInDefinition,
	'filterAlreadyDone':filterAlreadyDone,
    'removeDuplicates':removeDuplicates,
    'filterJunkText': filterJunkText,
    'itemsMatches': itemsMatches
};
},{}],2:[function(require,module,exports){

filters = require('./filters');

class Quiz {
	constructor() {
		this.current = 0;
		this.errors = [];
		this.whitelist = [];
	}
	
	setSource(source) {
		this.words = source;
		this.current = 0;
	}
	
	stepNext(kotae) {
		if (typeof (kotae) != 'string') {
			throw new Error();
		}
		this.putIntoProperList(kotae);
		if (!this.shouldFinish()) {
			++this.current;
		}
	}
	
	putIntoProperList(kotae) {
		var current = this.getCurrentWord();
		var definition = this.getCurrentDefinition();
		var match = filters.wordMatches(definition, kotae);
		if (!match) {
			this.putAtTheBeginningOfErrors({'key':current, 'value':definition, 'actual': kotae});
		} else {
			var accurateDefinition = filters.findWordInDefinition(kotae, definition);
			this.whitelist.push(current+'-'+accurateDefinition);
		}
	}
	
	putAtTheBeginningOfErrors(item) {
			this.errors = [item].concat(this.errors);
	}
	
	shouldFinish() {
		return this.current == this.words.length;
	}
	
	getCurrentWord() {
		return this.words[this.current].key;
	}
	
	getCurrentDefinition() {
		return this.words[this.current].value;
	}
	
	getResults() {
		return this.errors;
	}
	
	convertResultsIntoTest() {
		return this.errors.map(function (item) {
			return item.key+"-"+item.value;
		}).join("\n");
	}
	
	getWhitelistAsText() {
		return this.whitelist.join('\n');
	}
	
	clearResults() {
		this.errors = [];
		this.whitelist = [];
	}
	
	revertWord(word, expected) {
		this.errors = this.errors.filter(function (errorItem) {
			return !filters.itemsMatches(errorItem, {'key':word, 'value':expected});
		});
		this.whitelist.push(word+'-'+expected);
	}
    
    getStats() {
        return {
            'errors': this.errors.length,
            'correct': this.whitelist.length,
            'total': this.words.length
        };
    }
}

function prepareSource(source, whitelists) {
    var filteredSource = source;
    whitelists.forEach(function(whitelist){
        filteredSource = filters.filterAlreadyDone(filteredSource, whitelist);
    });
    filteredSource = filters.removeDuplicates(filteredSource);
    filteredSource = filters.filterJunkText(filteredSource);
    return filteredSource
}

module.exports = {
	'buildQuiz' : function(source, whitelists) { 
		var quiz = new Quiz();
		quiz.setSource(prepareSource(source, whitelists));
		return quiz;
	}
};
},{"./filters":1}],3:[function(require,module,exports){
var quiz = require('./backend/quizEngine');

window.buildQuiz = quiz.buildQuiz;
},{"./backend/quizEngine":2}]},{},[3]);
