export interface Panel {
  categories: Array<Category>;
}

export interface Category {
  name: String;
  controls: Array<Control>;
}

export interface Control {
  name: String;
  description: String;
}
