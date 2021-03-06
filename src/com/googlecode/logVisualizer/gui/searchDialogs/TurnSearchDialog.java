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

package com.googlecode.logVisualizer.gui.searchDialogs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.googlecode.logVisualizer.gui.MultiLineCellRenderer;
import com.googlecode.logVisualizer.gui.TurnDataPane;
import com.googlecode.logVisualizer.logData.LogDataHolder;
import com.googlecode.logVisualizer.logData.turn.Encounter;
import com.googlecode.logVisualizer.util.Lists;

/**
 * A dialog displaying all single turns of the given log data with a text field
 * to search for specific turns by a given criteria.
 */
final class TurnSearchDialog extends TurnEntitySearchDialog {
  /**
   *
   */
  private static final long serialVersionUID = 6911352804300163604L;

  @SuppressWarnings("rawtypes")
  JList turnResultsList;

  TurnDataPane dataPane;

  TurnSearchDialog(
      final JFrame owner, final LogDataHolder logData) {
    super(owner,
        logData,
        "Search For Specific Turns",
        SearchStringMatchers.ENCOUNTER_NAME_MATCHER);

    turnResultsList.addMouseListener(new MouseAdapter() {
      @SuppressWarnings("rawtypes")
      @Override
      public void mouseClicked(
          final MouseEvent e) {
        if (e.getClickCount() >= 2 && !turnResultsList.isSelectionEmpty()) {
          final DefaultListModel model = (DefaultListModel) turnResultsList.getModel();
          setSelectedTurn(((TurnContainer) model.getElementAt(turnResultsList.getSelectedIndex())).getTurn());
          dispose();
        }
      }
    });
    turnResultsList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(
          ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
          if (turnResultsList.getSelectedIndex() >= 0)
            dataPane.displayTurnEntityInfo(((TurnContainer) turnResultsList.getSelectedValue()).getTurn());
          else
            dataPane.setText("");
        }
      }
    });

    setVisible(true);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected JComponent createResultsPane() {
    final JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    turnResultsList = new JList(new DefaultListModel());
    dataPane = new TurnDataPane();

    turnResultsList.setToolTipText("Double-click turn to jump to it in the proper ascension log");
    turnResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    turnResultsList.setCellRenderer(new MultiLineCellRenderer());
    for (final TurnContainer tc : getTurnsList())
      addResult(tc);

    splitter.setLeftComponent(new JScrollPane(turnResultsList,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    splitter.setRightComponent(new JScrollPane(dataPane,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    splitter.setDividerLocation(450);

    return splitter;
  }

  @Override
  protected List<TurnContainer> createTurnList(
      final LogDataHolder logData) {
    final List<TurnContainer> result = Lists.newArrayList(logData.getTurnsSpent().size());

    for (final Encounter e : logData.getTurnsSpent())
      result.add(new TurnContainer(e, e.toString()));

    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected void clearResults() {
    ((DefaultListModel) turnResultsList.getModel()).clear();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected void addResult(
      final TurnContainer tc) {
    ((DefaultListModel) turnResultsList.getModel()).addElement(tc);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected void addResults(
      final Collection<TurnContainer> results) {
    // Default models already in use fire a lot of events when elements get
    // added, to the point that there are very noticeable slow-downs.
    // We can side-step this issue by simply using a new model in case there
    // are a lot of elements to be added.
    // A cleaner approach would probably be to implement a ListModel that
    // doesn't fire events when they are not needed, but this is adequate
    // enough for now.
    if (results.size() <= 25)
      for (final TurnContainer tc : results)
        addResult(tc);
    else {
      final DefaultListModel newModel = new DefaultListModel();
      final DefaultListModel currentModel = (DefaultListModel) turnResultsList.getModel();
      if (!currentModel.isEmpty())
        for (int i = 0; i < currentModel.size(); i++)
          newModel.addElement(currentModel.get(i));

      for (final TurnContainer tc : results)
        newModel.addElement(tc);

      turnResultsList.setModel(newModel);
    }
  }
}