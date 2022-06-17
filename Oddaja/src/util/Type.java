package util;

import java.awt.*;

public enum Type {
    POSITION(Color.GREEN), LETTER(Color.orange), WRONG(Color.gray), EMPTY(Color.WHITE);

    final Color colour;

    Type(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }
}
