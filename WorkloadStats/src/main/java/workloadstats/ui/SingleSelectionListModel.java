package workloadstats.ui;

import javax.swing.DefaultListSelectionModel;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

/**
 *
 * @author Ilkka
 */
public class SingleSelectionListModel extends DefaultListSelectionModel {

    public SingleSelectionListModel() {
        setSelectionMode(SINGLE_SELECTION);
    }

    @Override
    public void setSelectionInterval(int index0, int index1) {
        int oldIndex = getMinSelectionIndex();
        super.setSelectionInterval(index0, index1);
        int newIndex = getMinSelectionIndex();
        if (oldIndex != newIndex) {
            updateSingleSelection(oldIndex, newIndex);
        }
    }

    public void updateSingleSelection(int oldIndex, int newIndex) {
    }
}
