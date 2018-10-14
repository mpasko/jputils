export interface FileTree {
  name: String;
  subnodes: Array<FileTree>;
  subleafs: Array<FileTree>;
}
