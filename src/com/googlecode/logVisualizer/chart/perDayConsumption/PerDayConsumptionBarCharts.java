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

package com.googlecode.logVisualizer.chart.perDayConsumption;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.googlecode.logVisualizer.logData.LogDataHolder;
import com.googlecode.logVisualizer.logData.logSummary.ConsumptionSummary.ConsumptionDayStats;

public final class PerDayConsumptionBarCharts extends JTabbedPane {
  /**
   *
   */
  private static final long serialVersionUID = -3360187492483930819L;

  public PerDayConsumptionBarCharts(
      final LogDataHolder logData) {
    super(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

    for (final ConsumptionDayStats consumptionStats : logData.getLogSummary()
        .getConsumptionSummary()
        .getDayStatistics())
      addTab("Day " + consumptionStats.getDayNumber(),
          new PerDayConsumptionBarChart(consumptionStats));
  }
}
