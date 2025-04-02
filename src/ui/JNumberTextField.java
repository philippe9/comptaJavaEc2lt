package ui;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class JNumberTextField extends JTextField {
    private static final long serialVersionUID = 1L;
    public JNumberTextField(int columns){
        super(columns);
    }
    @Override
    public void processKeyEvent(KeyEvent evt) {
        char c = evt.getKeyChar();

        if ((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            super.processKeyEvent(evt);
        }
        evt.consume();
        return;
    }

    /**
     * As the user is not even able to enter a dot ("."), only integers (whole numbers) may be entered.
     */
    public Long getNumber() {
        Long result = null;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Long.valueOf(text);
        }
        return result;
    }
}
