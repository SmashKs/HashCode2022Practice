# HashCode2022Practice

Practice Solution for Google Hash Code 2022 Practice Problem "One Pizza".

Link - https://codingcompetitions.withgoogle.com/hashcode/round/00000000008f5ca9/00000000008f6f33

## Solution
The best solution so far is Solution F, a greedy algorithm that runs several times. The algorithm checks if putting an ingredient on the pizza results in more clients than not having that ingredient. 

A - 2 (Optimal: 2)

B - 5 (Optimal: 5)

C - 4 (Optimal: 5)

D - 1781 (Optimal: 1805)

E - 1531 (Optimal: 2085)

Total - 3323 (Optimal: 3902)

## Other Solutions
Solution A - a simple algorithm that checks if an ingredient is liked more than it is disliked. If liked >= disliked, then it is put on the pizza.

Solution B - an even simpler algorithm that gets all the ingredients and removes any ingredient that is disliked.

Solution C - brute force algorithm which gets all the possible combinations of ingredients and tests each one to find the one with the highest score.

Solution D - a variation on Solution A where two ingredients are selected at a time

Solution E - a variation on Solution A where the top 10% of clients with the most disliked ingredients is ignored. Ultimately not a useful solution since dislikes are capped at 5.

Solution F - as above.

## How to Test
Use SolutionFileTest to run the tests. (SolutionTest does not read from the files, which are too large to include in the SolutionTest.java file as input.)
