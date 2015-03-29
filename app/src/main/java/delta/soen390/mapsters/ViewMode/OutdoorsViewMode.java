package delta.soen390.mapsters.ViewMode;

import java.util.ArrayList;
import java.util.Collection;

import delta.soen390.mapsters.Buildings.BuildingPolygonOverlay;
import delta.soen390.mapsters.GeometricOverlays.PolygonOverlay;
import delta.soen390.mapsters.IndoorDirectory.BuildingFloor;

/**
 * Created by Mathieu on 3/29/2015.
 */
public class OutdoorsViewMode extends  ViewMode {

    public OutdoorsViewMode(Collection<BuildingPolygonOverlay> buildingOverlays)
    {
        mOverlays = buildingOverlays;
    }

    @Override
    public void setup() {

    }

    @Override
    public void cleanup() {

    }
}
