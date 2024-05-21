
package userinterface;

import javax.swing.*;

public class PlaceholderComboBox extends JComboBox<String> {
    private boolean placeholderVisible;
    private String placeholder;

    public PlaceholderComboBox(String placeholder, String[] items) {
        super(items);
        this.placeholder = placeholder;
        this.placeholderVisible = true;
        setSelectedItem(placeholder);

        // Add a listener to hide the placeholder when an item is selected
        addActionListener(e -> {
            if (placeholderVisible) {
                placeholderVisible = false;
                removeItem(placeholder);
            }
        });
    }

    @Override
    public String getSelectedItem() {
        String selectedItem = (String) super.getSelectedItem();
        if (placeholderVisible) {
            return placeholder; // Return placeholder if it is visible
        }
        return selectedItem;
    }

    public void setPlaceholderVisible(boolean placeholderVisible) {
        this.placeholderVisible = placeholderVisible;
        if (placeholderVisible) {
            insertItemAt(placeholder, 0);
            setSelectedItem(placeholder);
        } else {
            removeItem(placeholder);
        }
    }
}