import { DictionaryItem } from './error.item.type';

function wordMatches(wordDefinition, kotae) {
  return kotae.split(/,|;/)
    .filter((word) => wordMatchesElementary(wordDefinition, word.trim()))
    .length > 0;
}

function wordMatchesElementary(wordDefinition, kotae) {
	return (wordDefinition.indexOf(kotae) >= 0) && (kotae.length > 1);
}

function findWordInDefinition(word, definition) {
	let findMatchingDefinition = (def) => wordMatches(def, word)
	return definition
		.split(/,|;/)
		.filter(findMatchingDefinition)[0]
		.trim();
}

function filterAlreadyDone(source: Array<DictionaryItem>, whitelist: Array<DictionaryItem>) {
	return source.filter(function (sourceItem: DictionaryItem){
		return !whitelist.some((whiteItem: DictionaryItem) => itemsMatches(sourceItem, whiteItem));
	});
}

function itemsMatches(sourceItem: DictionaryItem, whiteItem: DictionaryItem) {
	return wordMatches(sourceItem.value, whiteItem.value) && sourceItem.key == whiteItem.key;
}

function removeDuplicates(list) {
  const filtered: Array<DictionaryItem> = [];
  list.forEach(function (item) {
    const shouldPut = !listContainsExact(filtered, item);
    if (shouldPut) {
        filtered.push(item);
    }
  });
  return filtered;
}

function listContainsExact(list: Array<DictionaryItem>, item: DictionaryItem): boolean {
  return list.filter(function (listItem) {
    return matchesExact(listItem, item);
  }).length > 0;
}

function matchesExact(first: DictionaryItem, second: DictionaryItem) {
  return first.key == second.key && first.value == second.value;
}

function filterJunkText(source: Array<DictionaryItem>): Array<DictionaryItem> {
  return source.map(function(item: DictionaryItem) {
    return {
      key: filterText(item.key),
      value: filterText(item.value)
    };
  });
}

const banned = ['\'', '"'];

function filterText(text) {
  let filtered = text;
  banned.forEach(function (bannedChar) {
    filtered = filtered.replace(new RegExp(bannedChar, 'ig'), "");
  });
  return filtered;
}

export class Filters {
	public wordMatches = wordMatches;
	public findWordInDefinition = findWordInDefinition;
	public filterAlreadyDone = filterAlreadyDone;
  public removeDuplicates = removeDuplicates;
  public filterJunkText = filterJunkText;
  public itemsMatches = itemsMatches;
};
