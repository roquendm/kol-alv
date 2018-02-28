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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This is an immutable list implementation that only holds a single element.
 * This allows for less overhead in cases where it is known that there is only
 * one element than when using ArrayList or LinkedList.
 * <p>
 * Note that this list implementation does not allow null values as elements.
 */
public final class SingleElementList<E> implements List<E> {
  private final E element;

  /**
   * Creates the {@link SingleElementList}.
   *
   * @param element
   *            The element making up this {@link SingleElementList}.
   * @throws NullPointerException
   *             if the given element is {@code null}
   */
  public SingleElementList(
      final E element) {
    if (element == null)
      throw new NullPointerException("SingleElementLists do not support null references.");

    this.element = element;
  }

  /**
   * @see List#contains(Object)
   */
  @Override
  public boolean contains(
      Object o) {
    return element.equals(o);
  }

  /**
   * @see List#containsAll(Collection)
   */
  @Override
  public boolean containsAll(
      Collection<?> c) {
    if (c.size() != 1)
      return false;

    final Object other = c.iterator().next();

    return contains(other);
  }

  /**
   * @see List#indexOf(Object)
   */
  @Override
  public int indexOf(
      Object o) {
    return contains(o) ? 0 : -1;
  }

  /**
   * @see List#lastIndexOf(Object)
   */
  @Override
  public int lastIndexOf(
      Object o) {
    return indexOf(o);
  }

  /**
   * @see List#get(int)
   */
  @Override
  public E get(
      int index) {
    if (index != 0)
      throw new IndexOutOfBoundsException();

    return element;
  }

  /**
   * @see List#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return false;
  }

  /**
   * @see List#size()
   */
  @Override
  public int size() {
    return 1;
  }

  /**
   * @see List#subList(int, int)
   */
  @Override
  public List<E> subList(
      int fromIndex, int toIndex) {
    if (fromIndex == 0 && toIndex == 1)
      return this;

    if (fromIndex == 0 && toIndex == 0)
      return Collections.emptyList();

    throw new IndexOutOfBoundsException();
  }

  /**
   * @see List#toArray()
   */
  @Override
  public Object[] toArray() {
    return new Object[] { element };
  }

  /**
   * @see List#toArray(T[])
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(
      T[] a) {
    // If using reflection is good enough for LinkedList in the standard
    // library, then it is good enough here.
    if (a.length < 1)
      a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 1);

    if (a.length == 1)
      a[0] = (T) element;
    else if (a.length > 1) {
      a[0] = (T) element;
      a[1] = null;
    }

    return a;
  }

  /**
   * @see List#iterator()
   */
  @Override
  public Iterator<E> iterator() {
    return new SingleElementIterator<E>(element);
  }

  /**
   * @see List#listIterator()
   */
  @Override
  public ListIterator<E> listIterator() {
    return new SingleElementIterator<E>(element);
  }

  /**
   * @see List#listIterator(int)
   */
  @Override
  public ListIterator<E> listIterator(
      int index) {
    if (index != 0)
      throw new IndexOutOfBoundsException();

    return new SingleElementIterator<E>(element);
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean add(
      E e) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public void add(
      int index, E element) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean addAll(
      Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean addAll(
      int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public E set(
      int index, E element) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean remove(
      Object o) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public E remove(
      int index) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean removeAll(
      Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean retainAll(
      Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(
      final Object o) {
    if (o == null)
      return false;

    if (o == this)
      return true;

    if (o instanceof List<?>) {
      final List<?> other = (List<?>) o;
      return other.size() == 1 ? element.equals(other.get(0)) : false;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return element.hashCode();
  }

  private static final class SingleElementIterator<E> implements ListIterator<E> {
    private final E element;

    private boolean hasNext = true;

    SingleElementIterator(
        final E element) {
      this.element = element;
    }

    @Override
    public boolean hasNext() {
      return hasNext;
    }

    @Override
    public boolean hasPrevious() {
      return !hasNext;
    }

    @Override
    public E next() {
      if (!hasNext)
        throw new NoSuchElementException();

      hasNext = false;

      return element;
    }

    @Override
    public E previous() {
      if (hasNext)
        throw new NoSuchElementException();

      hasNext = true;

      return element;
    }

    @Override
    public int nextIndex() {
      return hasNext ? 0 : 1;
    }

    @Override
    public int previousIndex() {
      return !hasNext ? 0 : -1;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void add(
        E e) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void set(
        E e) {
      throw new UnsupportedOperationException();
    }
  }
}