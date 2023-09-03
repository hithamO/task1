# task1
Implement a solution to the given problem:

Given a text file, which shows a set of anime that Omar has watched, and his rating for those anime in order, your job is to write a Java code to read the given file, then accept user input with the following rules:

1- If the user input is a number, print the anime which have a rating of that number or higher.

2- If the user input is an anime name, print the rating of that anime, if the anime does not exist, ask the user to to input a rating float number between 0 and 10.

3- If the user input is "man of a culture", stop the program.

4- Keep a log file of user inputs and the program's answers called LOG.txt

5- You should look an anime from the text file only when it is asked for for the first time, second time and beyond you should have that specific anime data stored in your running program, in other words cache it.

6- Whatever data structure or container you use for caching requested anime, it should not containe more than 10 anime at a time, if you want to insert a new anime but the cache is already full, remove some other anime in order to make space. Think of a good best strategy for which anime to remove.

7- Anime name and its rating should be represented as objects, in other words you will have an anime class.

Notes to consider:
1- Letter cases in anime names.
2- Proper user input validation, for example an anime rating cannot contain characters, or numbers out of the range [0, 10].

Hints:
Look up the following in Java:
1- BufferedFileReader
2- BufferedFileWriter
3- How to read or write to a file in Java.
4- Comparator.
5- LRU Cache.
6- Map and its types.
7- String Splitting or String Tokenization.

Constraints:
1- Use a JVM Langauge (e.g. Java, Kotlin or Scala) 
