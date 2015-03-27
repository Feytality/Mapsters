package delta.soen390.mapsters.GeometricOverlays;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONObject;

import java.util.ArrayList;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 3/21/2015.
 */
public class PolygonOverlay {

    //Used to test for touch input
    protected BoundingBox2D mBoundingBox2D;
    protected GoogleMap mGoogleMap;
    protected Polygon mPolygon;

    private int mFocusedColor;
    private int mUnfocusedColor;

    private LatLng mCenterPoint;
    protected PolygonOverlayManager mPolygonManager;

    public PolygonOverlay(MapsActivity activity) {
        mGoogleMap = activity.getGoogleMap();
        mPolygonManager = activity.getPolygonOverlayManager();
    }


    public boolean createPolygon(ArrayList<LatLng> boundingPoints)
    {
//Create the Polygon Options which will be used to instantiate the actual google map Polygon UI element
        PolygonOptions polygonOptions = new PolygonOptions();

        double totalLatitude = 0,totalLongitude = 0;
        //Load in the vertices!
        for(LatLng point : boundingPoints)
        {
            totalLatitude += point.latitude;
            totalLongitude += point.longitude;
            polygonOptions.add(point);
        }

        mCenterPoint = new LatLng(totalLatitude/boundingPoints.size(),totalLongitude/boundingPoints.size());
        //

        //add first vertex at the end in order to create a complete polygon loop
        if(boundingPoints.size() > 0) {
            polygonOptions.add(boundingPoints.get(0));
        }


        mBoundingBox2D = new BoundingBox2D(boundingPoints);

        mPolygon = mGoogleMap.addPolygon(polygonOptions);

        return mPolygon != null;
    }

    public void setVisibility(boolean isVisible)
    {
        mPolygon.setVisible(isVisible);
    }


    public boolean isPointInsidePolygon(LatLng point)
    {
        //Test if point is inside box
        if(mBoundingBox2D == null)
            return false;
        return mBoundingBox2D.isPointInsideBoundingBox(new Vector2D(point.latitude, point.longitude));
    }

    public void setBorderWidth(float width)
    {
        if(mPolygon != null) {
            mPolygon.setStrokeWidth(width);
        }
    }

    public LatLng getCenterPoint() { return mCenterPoint;}

    public void setFillColor(int color)
    {
        if(mPolygon != null) {
            mPolygon.setFillColor(color);
        }
    }

    //returns whether this overlay can be rendered on the google map
    public boolean isValidOverlay()
    {
        return mPolygon != null;
    }

    public void setFocusColor(int color)
    {
        mFocusedColor = color;
    }
    public void setUnfocusedColor(int color)
    {
        mUnfocusedColor = color;
    }

    public void focus()
    {
        mPolygonManager.focusOverlay(this);
        setFillColor(mFocusedColor);
    }

    public void unfocus()
    {
        setFillColor(mUnfocusedColor);
    }

}
