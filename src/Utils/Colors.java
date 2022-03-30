package Utils;

import java.awt.*;

public interface Colors {
    Color grayTeamAccent = Color.lightGray;
    Color blueTeamAccent = Color.getHSBColor(0.5f, 1, 0.9f);
    Color redTeamAccent = Color.getHSBColor(0.1f, 1, 0.9f);

    Color boardBase = Color.getHSBColor(0.25f, 0.4f, 0.7f);
    Color boardAlt = Color.getHSBColor(0.2f, 0.5f, 0.7f);

    Color tileSelected = Color.getHSBColor(0.7f, 0.7f, 0.9f);
    Color tileHighlighted = Color.getHSBColor(0.7f, 0.5f, 0.9f);
}