snippets
========

Useful code snippets

# Angular UI Bootstrap Popover with HTML
I needed a popover that enables HTML, so I modified the Popover from Angular UI Bootstrap 0.7.0. I simply created a new directive, popoverHtmlPopup. The HTML in its template uses 'bind-html-unsafe' instead of 'ng-bind'.

This is just a hack to make HTML work for popovers right now.

See live example here: http://plnkr.co/edit/7O7DD094qg4wClbdBQvo?p=preview