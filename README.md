snippets
========

Useful code snippets

# popover-html: Angular UI Bootstrap Popover with HTML
I needed a popover that enables HTML, so I modified the Popover from Angular UI Bootstrap 0.7.0. I simply created a new directive, popoverHtmlPopup. The HTML in its template uses 'bind-html-unsafe' instead of 'ng-bind'.

This is just a hack to make HTML work for popovers right now.

See live example here: http://plnkr.co/edit/7O7DD094qg4wClbdBQvo?p=preview

# SafeMessageFormatter: Format text _without_ throwing exceptions.

Example:

`String formatted = SafeMessageFormatter.format("hei {0}", "på", "deg"); // look ma, no exceptions!`

# Compare each char in a string (when IntelliJ doesn't show the diff on assert failure)
```java
private void analyzeBytes(String lastCommentBody, String expectedBody) {
        for (int i = 0; i < expectedBody.length(); i++) {
            char expected = expectedBody.charAt(i);
            char actual = lastCommentBody.charAt(i);

            System.out.print(actual);
            if (expected != actual) {
                System.out.println("\nAssert failed.");
                System.out.println("Expected:\t" + expected);
                System.out.println("Actual:\t" + actual);
                System.out.println("Expected:\t" + (byte)expected);
                System.out.println("Actual:\t\t" + (byte)actual);
                break;
                //assertEquals(expected, actual);
            }
        }
    }
```
