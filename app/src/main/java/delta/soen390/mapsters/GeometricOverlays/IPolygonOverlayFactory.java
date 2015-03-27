package delta.soen390.mapsters.GeometricOverlays;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mathieu on 3/25/2015.
 */
 public interface IPolygonOverlayFactory {

    //Takes in the file path of the polygon resource file and generates an array of polygon overlays
    public abstract ArrayList<? extends PolygonOverlay> generatePolygonOverlay(String filename);
}
