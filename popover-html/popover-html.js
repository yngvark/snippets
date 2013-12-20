/**
 * The following features are still outstanding: popup delay, animation as a
 * function, placement as a function, inside, support for more triggers than
 * just mouse enter/leave, html popovers, and selector delegatation.
 */
angular.module( 'custom.ui.bootstrap.popover', [ 'ui.bootstrap.tooltip' ] )
.directive( 'popoverHtmlPopup', function () {
  return {
    restrict: 'EA',
    replace: true,
    scope: { title: '@', content: '@', placement: '@', animation: '&', isOpen: '&' },
    templateUrl: 'application\\lib\\popover-html\\popover-html.html'
  };
})
.directive( 'popoverHtml', [ '$compile', '$timeout', '$parse', '$window', '$tooltip', function ( $compile, $timeout, $parse, $window, $tooltip ) {
  return $tooltip( 'popoverHtml', 'popover', 'click' );
}]);

