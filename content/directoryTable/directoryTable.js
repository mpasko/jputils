function directoryTableController($scope, $element, $attrs) {
  var $ctrl = this;
  $ctrl.types = ['reading', 'listening'];
  $ctrl.sources = ['unprocessed', 'black'];
  $ctrl.buttons = [];
  $ctrl.types.forEach(function(type) {
      $ctrl.sources.forEach(function(source) {
          $ctrl.buttons.push({type:type, source:source});
      });
  });
}

app.component('directoryTable', {
    bindings: {
        depth: "@",
        dir: "=",
        makeExam:"=",
        previewCategory:"=",
        expand:"=",
        expanded:"="
    },
    replace: true,
    transclude: true,
    controller: directoryTableController,
    templateUrl: "directoryTable/directoryTable.html"
});