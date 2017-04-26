# Report
## Part 0

### 2.8 Assertions
-	
The invariant method checks to see that the board does not contain null squares

    - The test fails with:
        > java.lang.AssertionError: Initial grid cannot contain null squares

    - When the `-ea` flag is removed, 

    - It looks as if DevHub does make use of assertions, since the build log indicates that the point of failure was the point where the assertion of the invariant was violated.

