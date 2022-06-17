package util;

import java.awt.*;

public enum Theme {
    LIGHT(Color.WHITE, Color.BLACK), Dark(Color.DARK_GRAY, Color.BLACK);

    final Color backgroundColor;
    final Color letterColor;

    Theme(Color backgroundColor, Color letterColor) {
        this.backgroundColor = backgroundColor;
        this.letterColor = letterColor;
    }

    public Color getBackgroundColor(){
        return backgroundColor;
    }
    public Color getLetterColor(){
        return letterColor;
    }
}
