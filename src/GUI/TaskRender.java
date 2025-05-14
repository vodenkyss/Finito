package GUI;

import model.Task;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TaskRender extends JCheckBox implements  ListCellRenderer<Task>{
    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {

        String text = value.getDescription();
        if (value.getDeadline() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            text += " (📅 " + value.getDeadline().format(formatter) + ")";
        }

        setText(text);
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
