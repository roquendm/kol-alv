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

package com.googlecode.logVisualizer.chart.turnrundownGantt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.general.AbstractSeriesDataset;

import com.googlecode.logVisualizer.logData.turn.TurnInterval;

// Annotation to shut up all those raw type warnings that appear because of not
// used Generics. Generics cannot be used in some places in this class due to
// the used interface, which was written with Java <1.5 compatibility in mind.
@SuppressWarnings("rawtypes")
public final class TurnRundownDataset extends AbstractSeriesDataset implements GanttCategoryDataset {
  /**
   *
   */
  private static final long serialVersionUID = 4175727242610150629L;

  private static final String DATASET_NAME = "Turn rundown";

  private static final List DATASET_NAMES = Arrays.asList(DATASET_NAME);

  private Map<String, AreaInterval> dataMap = new LinkedHashMap<String, AreaInterval>(100);

  public TurnRundownDataset() {
    super();
  }

  public void setDataset(
      final List<AreaInterval> data) {
    dataMap = new LinkedHashMap<String, AreaInterval>((int) (data.size() * 1.5 + 1));
    for (final AreaInterval ai : data)
      dataMap.put(ai.getName(), ai);
  }

  public Collection<AreaInterval> getDataset() {
    return dataMap.values();
  }

  public void addTurnInterval(
      final TurnInterval area, String categoryName) {
    // Default to turn interval name if no category name is specified.
    if (categoryName == null)
      categoryName = area.getAreaName();

    final AreaInterval tmp = dataMap.get(categoryName);
    if (tmp != null)
      tmp.addSubInterval(area);
    else
      dataMap.put(categoryName, new AreaInterval(area, categoryName));
  }

  @Override
  public Number getEndValue(
      final int row, final int column, final int subinterval) {
    final Comparable<?> rowKey = getRowKey(row);
    final Comparable<?> columnKey = getColumnKey(column);
    return getEndValue(rowKey, columnKey, subinterval);
  }

  @Override
  public Number getEndValue(
      final Comparable rowKey, final Comparable columnKey,
      final int subinterval) {
    final AreaInterval tmp = dataMap.get(columnKey.toString());
    final TurnInterval sub = tmp != null ? tmp.getSubInterval(subinterval) : null;

    return sub != null ? Integer.valueOf(sub.getEndTurn()) : null;
  }

  @SuppressWarnings("boxing")
  @Override
  public Number getPercentComplete(
      final int row, final int column) {
    return 0;
  }

  @SuppressWarnings("boxing")
  @Override
  public Number getPercentComplete(
      final Comparable rowKey, final Comparable columnKey) {
    return 0;
  }

  @SuppressWarnings("boxing")
  @Override
  public Number getPercentComplete(
      final int row, final int column, final int subinterval) {
    return 0;
  }

  @SuppressWarnings("boxing")
  @Override
  public Number getPercentComplete(
      final Comparable rowKey, final Comparable columnKey,
      final int subinterval) {
    return 0;
  }

  @Override
  public Number getStartValue(
      final int row, final int column, final int subinterval) {
    final Comparable<?> rowKey = getRowKey(row);
    final Comparable<?> columnKey = getColumnKey(column);
    return getStartValue(rowKey, columnKey, subinterval);
  }

  @Override
  public Number getStartValue(
      final Comparable rowKey, final Comparable columnKey,
      final int subinterval) {
    final AreaInterval tmp = dataMap.get(columnKey.toString());
    final TurnInterval sub = tmp != null ? tmp.getSubInterval(subinterval) : null;

    return sub != null ? Integer.valueOf(sub.getStartTurn()) : null;
  }

  @Override
  public int getSubIntervalCount(
      final int row, final int column) {
    final Comparable<?> rowKey = getRowKey(row);
    final Comparable<?> columnKey = getColumnKey(column);
    return getSubIntervalCount(rowKey, columnKey);
  }

  @Override
  public int getSubIntervalCount(
      final Comparable rowKey, final Comparable columnKey) {
    return dataMap.get(columnKey.toString()).getSubIntervals().size();
  }

  @Override
  public Number getEndValue(
      final int series, final int category) {
    final Comparable<?> rowKey = getRowKey(series);
    final Comparable<?> columnKey = getColumnKey(category);
    return getEndValue(rowKey, columnKey);
  }

  @Override
  public Number getEndValue(
      final Comparable series, final Comparable category) {
    final AreaInterval tmp = dataMap.get(category.toString());

    return tmp != null ? Integer.valueOf(tmp.getEndTurn()) : null;
  }

  @Override
  public Number getStartValue(
      final int series, final int category) {
    final Comparable<?> rowKey = getRowKey(series);
    final Comparable<?> columnKey = getColumnKey(category);
    return getStartValue(rowKey, columnKey);
  }

  @Override
  public Number getStartValue(
      final Comparable series, final Comparable category) {
    final AreaInterval tmp = dataMap.get(category.toString());

    return tmp != null ? Integer.valueOf(tmp.getStartTurn()) : null;
  }

  @Override
  public int getColumnIndex(
      final Comparable key) {
    final String keyName = key.toString();
    final Object[] keys = dataMap.keySet().toArray();
    for (int i = 0; i < keys.length; i++)
      if (keys[i].equals(keyName))
        return i;

    return -1;
  }

  @Override
  public Comparable<?> getColumnKey(
      final int column) {
    final Object[] keys = dataMap.keySet().toArray();
    return (Comparable<?>) keys[column];
  }

  @Override
  public List getColumnKeys() {
    return new ArrayList<String>(dataMap.keySet());
  }

  @Override
  public int getRowIndex(
      final Comparable key) {
    return 0;
  }

  @Override
  public Comparable<?> getRowKey(
      final int row) {
    return DATASET_NAME;
  }

  @Override
  public List getRowKeys() {
    return DATASET_NAMES;
  }

  @Override
  public Number getValue(
      final Comparable rowKey, final Comparable columnKey) {
    return getStartValue(rowKey, columnKey);
  }

  @Override
  public int getColumnCount() {
    return dataMap.size();
  }

  @Override
  public int getRowCount() {
    return DATASET_NAMES.size();
  }

  @Override
  public Number getValue(
      final int row, final int column) {
    return getStartValue(row, column);
  }

  @Override
  public int getSeriesCount() {
    return getRowCount();
  }

  @Override
  public Comparable<?> getSeriesKey(
      final int series) {
    return getRowKey(series);
  }
}
