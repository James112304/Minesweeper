package org.cis1200.Minesweeper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Images {

    private static Image flag;
    private static Image empty;

    private static Image blank;

    private static Image bomb;
    private static Image goodbomb;
    private static Font mFont;

    static {
        try {
            empty = ImageIO.read(new File("files/square.png"));
            flag = ImageIO.read(new File("files/flag.png"));
            blank = ImageIO.read(new File("files/blank.png"));
            bomb = ImageIO.read(new File("files/bomb.png"));
            goodbomb = ImageIO.read(new File("files/goodbomb.png"));
            mFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    new FileInputStream("files/mine-sweeper.ttf")
            )
                    .deriveFont(15.0f);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getFlag() {
        return flag;
    }
    public static Image getEmpty() {
        return empty;
    }
    public static Image getBlank() {
        return blank;
    }
    public static Image getBomb() {
        return bomb;
    }
    public static Image getGoodbomb() {
        return goodbomb;
    }
    public static Font getmFont() {
        return mFont;
    }
}
