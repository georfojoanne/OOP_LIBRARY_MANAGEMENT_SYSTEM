import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PlaceholderComboBox<E> extends JComboBox<E> {
    private String placeholder;
    private boolean placeholderVisible;

    public PlaceholderComboBox(String placeholder, E[] categories) {
        super(categories);
        this.placeholder = placeholder;
        this.placeholderVisible = true;
        setForeground(Color.GRAY);

        // Add a focus listener to handle the placeholder behavior
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (placeholderVisible) {
                    // Clear the placeholder text when the combo box gains focus
                    setSelectedIndex(-1);
                    placeholderVisible = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getSelectedIndex() == -1) {
                    // Show the placeholder text if no item is selected
                    setPlaceholder();
                }
            }
        });

        // Show the placeholder text initially
        setPlaceholder();
    }

    private void setPlaceholder() {
        setSelectedIndex(0); // Select the placeholder item
        setForeground(Color.GRAY);
        placeholderVisible = true;
    }

    @Override
    public void setSelectedIndex(int index) {
        // Override setSelectedIndex to prevent selecting the placeholder as the item
        if (index != 0) {
            super.setSelectedIndex(index);
        }
    }

    public boolean isPlaceholderVisible() {
        return placeholderVisible;
    }
}