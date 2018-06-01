package edu.cnm.deepdive.diceware;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Class for creating random arrays of strings or random words
 * utilizing a random number generator and lists of short words.
 *
 *
 */
public class Generator {

  private static final String NULL_RNG_MESSAGE = "Must include random number generator.";
  private static final String NULL_WORDS_MESSAGE = "Must include array of words.";
  private static final String EMPTY_WORDS_MESSAGE = "Must have words included in array.";
  private static final String NEGATIVE_NUMBER_WORDS_MESSAGE = "Number of word to be selected must no be negative.";
  private static final String INSUFFICIENT_WORDS_MESSAGE = "Number of distinct words requested must not exceed number of words in pool.";

  private String[] words;
  private Random rng;

  /**
   * Receives a list of words, a random number generator and size of array necessary as inputs then
   * generates a randomized array of said length and returns.
   * <p>
   * @param words String of words to submit into generator.
   * @param rng Random number generator.
   * @throws NullPointerException if you don't provide a random number generator or a words list.
   * @throws IllegalArgumentException if you haven't included words in word list.
   */
  public Generator(String[] words, Random rng)
      throws NullPointerException, IllegalArgumentException {
    if (rng == null) {
      throw new NullPointerException(NULL_RNG_MESSAGE);
    }
    if (words == null) {
      throw new NullPointerException(NULL_WORDS_MESSAGE);
    }
    if (words.length == 0) {
      throw new IllegalArgumentException(EMPTY_WORDS_MESSAGE);
    }
    Set<String> pool = new HashSet<>();
    for (String word : words) {
      word = word.toLowerCase();
      if (!pool.contains(word)) {
        pool.add(word);
      }
    }
    this.words = pool.toArray(new String[pool.size()]);
    this.rng = rng;
  }

  /**
   *This method generates a random word from a list.
   * <p>
   * @return Returns a random word from list.
   */
  public String next() {
    return words[rng.nextInt(words.length)];
  }

  /**
   *This method allows user to decide whether or not to include duplicate word from list when randomizing
   * the array. It uses the same concept as the first method. User inputs list of words, rng, size of array,
   * and includes true or false for duplicates.
   * <p>
   * @param numWords String of words to submit for randomizing.
   * @param duplicatesAllowed indicates words can be duplicated from list.
   * @return Returns randomized array generated by rng.
   * @throws NegativeArraySizeException if attempting to generate an array of negative value.
   * @throws IllegalArgumentException if requesting an array that is of higher value than words in list and no duplicates are allowed.
   */
  public String[] next(int numWords, boolean duplicatesAllowed)
      throws NegativeArraySizeException, IllegalArgumentException {
    if (numWords < 0) {
      throw new NegativeArraySizeException(NEGATIVE_NUMBER_WORDS_MESSAGE);
    }
    if (!duplicatesAllowed && numWords > words.length) {
      throw new IllegalArgumentException(INSUFFICIENT_WORDS_MESSAGE);
    }
    List<String> selection = new LinkedList<>();
    while (selection.size() < numWords) {
      String pick = next();
      if (duplicatesAllowed || !selection.contains(pick)) {
        selection.add(pick);
      }
   }
    return selection.toArray(new String[selection.size()]);
  }

  public String[] next(int numWords)
      throws NegativeArraySizeException {
    return next(numWords, true);
  }
}
