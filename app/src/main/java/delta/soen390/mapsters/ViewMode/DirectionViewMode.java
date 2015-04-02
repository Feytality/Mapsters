package delta.soen390.mapsters.ViewMode;

import delta.soen390.mapsters.Activities.MapsActivity;

/**
 * Created by Mathieu on 4/2/2015.
 */
public class DirectionViewMode extends ViewMode{

    @Override
    public void setup(MapsActivity activity, ViewMode previousViewMode) {
        //When direction is activated, hide the search bar
        activity.setSearchComboVisible(false);
        activity.setLocationButtonVisible(false);
    }

    @Override
    public void cleanup(MapsActivity activity) {
        activity.setSearchComboVisible(true);
        activity.setLocationButtonVisible(true);
    }
}
