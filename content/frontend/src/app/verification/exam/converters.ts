import { DictionaryItem, ErrorItem } from '../examinator/error.item.type';
import { Mistake } from './mistake.type';
import { Question } from './question.type';

export function convertToNode(data: Array<Question>): Array<DictionaryItem> {
    return data.map(function (item) {
        return {
            key: item.question,
            value: item.corectAnswer
        };
    });
};

export function convertFromNode(data: Array<ErrorItem>) : Array<Mistake> {
    return data.map(function (item) {
        return {
            word:item.key,
            expected:item.value,
            actual:item.actual
        };
    });
};
