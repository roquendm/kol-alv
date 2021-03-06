/* Copyright (c) 2008-2011, developers of the Ascension Log Visualizer
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.googlecode.logVisualizer.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class with static helper methods for lists.
 */
public final class Lists {
  private Lists() {}

  /**
   * @return A new {@link ArrayList}.
   */
  public static <E> List<E> newArrayList() {
    return new ArrayList<E>();
  }

  /**
   * @param initialCapacity
   *            The initial capacity of the new {@link ArrayList}.
   * @return A new {@link ArrayList} with the given initial capacity.
   */
  public static <E> List<E> newArrayList(
      final int initialCapacity) {
    return new ArrayList<E>(initialCapacity);
  }

  /**
   * @param elements
   *            The elements to be added to the new {@link ArrayList}.
   * @return A new {@link ArrayList} populated with the given elements.
   */
  public static <E> List<E> newArrayList(
      final Collection<? extends E> elements) {
    return new ArrayList<E>(elements);
  }

  /**
   * @param elements
   *            The elements to be added to the new {@link ArrayList}.
   * @return A new {@link ArrayList} populated with the given elements.
   */
  public static <E> List<E> newArrayList(
      final Iterable<? extends E> elements) {
    final List<E> list = newArrayList();
    for (final E element : elements)
      list.add(element);

    return list;
  }

  /**
   * @param elements
   *            The elements to be added to the new {@link ArrayList}.
   * @return A new {@link ArrayList} populated with the given elements.
   */
  @SuppressWarnings("unchecked")
  public static <E> List<E> newArrayList(
      final E... elements) {
    final List<E> list = newArrayList(elements.length);
    for (final E element : elements)
      list.add(element);

    return list;
  }

  /**
   * @param elements
   *            The elements to be added to the new immutable list.
   * @return A new immutable list populated with the given elements.
   */
  public static <E> List<E> immutableListOf(
      final Collection<? extends E> elements) {
    if (elements.size() == 1)
      return new SingleElementList<E>(elements.iterator().next());

    return Collections.unmodifiableList(newArrayList(elements));
  }

  /**
   * @param elements
   *            The elements to be added to the new immutable list.
   * @return A new immutable list populated with the given elements.
   */
  public static <E> List<E> immutableListOf(
      final Iterable<? extends E> elements) {
    return Collections.unmodifiableList(newArrayList(elements));
  }

  /**
   * @param elements
   *            The elements to be added to the new immutable list.
   * @return A new immutable list populated with the given elements.
   */
  @SuppressWarnings("unchecked")
  public static <E> List<E> immutableListOf(
      final E... elements) {
    if (elements.length == 1)
      return new SingleElementList<E>(elements[0]);

    return Collections.unmodifiableList(newArrayList(elements));
  }

  /**
   * Reverses the given list and returns it.
   * <p>
   * This method is a convenience method that simply calls
   * {@link Collections#reverse(List)} <i>and</i> returns the given list.
   *
   * @return The given list after it was reversed.
   */
  public static <E> List<E> reverse(
      final List<E> list) {
    Collections.reverse(list);
    return list;
  }

  /**
   * Sorts the given list according to the natural ordering of its elements
   * and returns it.
   * <p>
   * This method is a convenience method that simply calls
   * {@link Collections#sort(List)} <i>and</i> returns the given list.
   *
   * @return The given list after it was sorted.
   */
  public static <E extends Comparable<? super E>> List<E> sort(
      final List<E> list) {
    Collections.sort(list);
    return list;
  }

  /**
   * Sorts the given list according to the given comparator and returns it.
   * <p>
   * This method is a convenience method that simply calls
   * {@link Collections#sort(List, Comparator)} <i>and</i> returns the given
   * list.
   *
   * @return The given list after it was sorted.
   */
  public static <E> List<E> sort(
      final List<E> list, final Comparator<? super E> comparator) {
    Collections.sort(list, comparator);
    return list;
  }
}
