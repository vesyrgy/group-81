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

The trace reveals: 

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

##3.2.6
For the second `Given` statement in S2.4 it is hard to realise proper timing of movements,
in order for the player to be next to a ghost.
For the `When` statement in S2.5 it is hard to realise exact timing of movements, so that
pacman has eaten all but one pellets, without colliding with a ghost.

##3.2.8

All of the scenarios in User Story 3 pertain to ghosts, which are automatically moved around.
Because of this, it is not possible to test the moves using `game.move()` as before. Moreover, 
Ghost itself is an abstract class, which means we would have to test the individual ghosts, thereby 
creating additional test cases. Furthermore, the Ghost classes do not have built-in methods to return the 
current square in the way that Player does. The situation is further complicated by the fact that the 
different Ghosts not only move differently from each other, but also differently depending on certain 
scenarios in the game. 



#3.3 Boundary Testing 

##3.3.9 

See 3.3.9_domain_matrix.png

#3.4

##3.4.11

##3.4.12

##3.4.13

The difference between `assertTrue(a == 1)` and `assertEquals(a, 1)` is that
the `assertEquals` gives a comparison of the expected value with the actual value, 
whereas `assertTrue` does not. So `assertEquals` is more useful, as it provides information
that can be used debug a failing test. 

##3.4.14

If a class contains large complex private methods, it often means that there is a design problem 
(i.e. that the class violates the Single Responsibility Principle). Usually, this means the code should
be refactored so that it uses public methods, which then can be tested just like the other public methods. 
So, in the case of `MapParser`, however, such refactoring might not be necessary, since it does
appear to be a more or less single-purpose class. Either way, it would be a good idea to test the methods, 
because an error in one of these methods could result in squares that are improperly assigned, which would
be difficult to debug in any other test. 