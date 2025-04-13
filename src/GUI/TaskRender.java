package GUI;

import model.Task;

import javax.swing.*;
import java.awt.*;

public class TaskRender extends JCheckBox implements  ListCellRenderer<Task>{
    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getDescription());
        setSelected(value.isDone());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
