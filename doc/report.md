# Report
## Part 0

### 2.8 Assertions
-	
The invariant method checks to see that the board does not contain null squares

    - The test fails with:
        > java.lang.AssertionError: Initial grid cannot contain null squares

    - When the `-ea` flag is removed, 

    - It looks as if DevHub does make use of assertions, since the build log indicates that the point of failure was the point where the assertion of the invariant was violated.

### 2.9 Coverage Analysis in Maven

    -	nl.tudelft.jpacman.level has the most untested lines
    -	CollisionInteractionMap andf DefaultPlayerInteractionMap have no test coverage and MapParser and Level are missing a bunch of tests as well
    -	sorting on the number of missed lines gives different results than sorting on the number of missed instructions. It appears that the missed lines looks strictly at the missed lines of code, while the missed instructions appears to also look at lines that are referenced.
    -	the branch coverage of Board.withinBorders(x,y) is 50%
    -	the missed branches are mostly assert statements, which obscure the accuracy of the branch coverage. 