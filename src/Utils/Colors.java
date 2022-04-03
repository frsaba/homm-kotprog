package Utils;

import java.awt.*;


/**
 * Színkonstansokat tároló interfész
 */
public interface Colors {
    Color grayTeamAccent = Color.lightGray;
    Color blueTeamAccent = Color.getHSBColor(0.55f, 1, 0.9f);
    Color redTeamAccent = Color.getHSBColor(0.1f, 1, 0.9f);

//    Color boardBase = Color.getHSBColor(0.25f, 0.4f, 0.7f);
//    Color boardAlt = Color.getHSBColor(0.2f, 0.5f, 0.7f);

    Color boardBase = Color.getHSBColor(0.25f, 0.1f, 0.1f);
    Color boardAlt = Color.getHSBColor(0.15f, 0.1f, 0.2f);
    Color boardMarkers = Color.getHSBColor(0.5f, 0.1f, 0.5f);

    Color defaultBackground = Color.getHSBColor(0,0,0.05f);

    Color tileSelected = Color.getHSBColor(0f, 0.7f, 0.9f);
    Color tileHighlighted = Color.getHSBColor(0.7f, 0.5f, 0.9f);
}
