export class DictionaryItem {
  public key: string;
  public value: string;
}

export class ErrorItem extends DictionaryItem{
  public actual: string;
}
