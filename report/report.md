Report
======

#3.1 Smoke Testing

##3.1.1
The overall line coverage was found to be 82%. The `CollisionInteractionMap`
and the `DefaultPlayerInteractionMap` both have 0% coverage. This is because
`CollisionInteractionMap` is only used by `DefaultPlayerInteractionMap`, which,
in turn, is never referred to by any classes that are instantiated during the 
smoke test.

We added the following error to the game: we replaced the direction in the
`Game.move()` method to `Direction.NORTH`. The smoke test captured this with 
a failing test. 

##3.1.2
The `move()` method is covered. After commenting out the last line of the `move()` 
method, the smoke test generated the error:

`org.junit.ComparisonFailure: expected:<[1]0> but was:<[]0>
 Expected :10
 Actual   :0`

The trace reveeals: 

`at nl.tudelft.jpacman.LauncherSmokeTest.smokeTest(LauncherSmokeTest.java:69)`

which indicates that the test failed on line 69, which contains the code:

`assertThat(player.getScore()).isEqualTo(10);`

We can conclude that something must have gone wrong between the previous assertion
(on line 65) and this one. The only line in between these two assertions in the smoke
test is line 68:

`game.move(player, Direction.EAST);`

So, the smoke test tells us that we should investigate this method call further.

##3.1.3 
When we changed the `board.Direction.getDeltaX()` method, the smoke test failed in
exactly the same way as in the previous exercise. So, while the smoke test _is_ able
to tell us that we should investigate the call to `game.move(player, Direction.EAST);`,
it does not tell us whether the problem resides with the method itself or with the 
objects that are being passed _to_ the method.


#3.2 Feature-Based Testing
