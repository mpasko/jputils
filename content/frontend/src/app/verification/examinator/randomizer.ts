function randomInt(notMoreThan){
	return Math.trunc(Math.random()*notMoreThan);
}

function spliceSideEfectLess(collection, from, to) {
    let backup = collection.slice();
    return backup.splice(from, to);
}

function removeAt(collection, index) {
	let length = collection.length;
	let second_part = spliceSideEfectLess(collection, index+1, length-1-index);
	let first_part = spliceSideEfectLess(collection, 0, index);
	let rest = first_part.concat(second_part);
	return rest;
}

function selectRandom(collection) {
	let index = randomInt(collection.length);
	return {
		chosen: collection[index],
		rest: removeAt(collection, index)
	};
}

function randomize(collection) {
	let actualCollection = collection;
	let newCollection = [];
	for (let i=0; i<collection.length; ++i) {
		let splitted = selectRandom(actualCollection);
		newCollection.push(splitted.chosen);
		actualCollection = splitted.rest;
	}
	return newCollection;
}

export class Randomizer {
	public randomize = randomize;
	public removeAt = removeAt;
  public selectRandom = selectRandom;
};
