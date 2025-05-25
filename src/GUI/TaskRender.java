package GUI;

import model.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TaskRender extends JCheckBox implements  ListCellRenderer<Task>{


    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task value, int index, boolean isSelected, boolean cellHasFocus) {

        String text = "[" + value.getPriority().toString() + "] " +value.getDescription();
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

        if (!value.isDone() && value.getDeadline() != null) {
            long days = ChronoUnit.DAYS.between(LocalDate.now(), value.getDeadline());
            if (days == 0) {
                setBackground(new Color(255, 200, 200));
                setForeground(Color.black);
            } else if (days == 1) {
                setBackground(new Color(255, 240, 200));
                setForeground(Color.black);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
        }

        return this;
    }
}
