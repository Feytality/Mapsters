package delta.soen390.mapsters.Effects;

import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;

/**
 * Created by Cat on 3/30/2015.
 */
public class MonochromeOverlayEffect implements  IEffect {
    private PolygonOverlay mOverlay;
    private int mEffectColor;

    public MonochromeOverlayEffect(PolygonOverlay overlay, int color)
    {
        mEffectColor = color;
        mOverlay = overlay;
    }

    @Override
    public void onStart() {
        mOverlay.setFillColor(mEffectColor);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onEnd() {
        mOverlay.unfocus();
    }
}
