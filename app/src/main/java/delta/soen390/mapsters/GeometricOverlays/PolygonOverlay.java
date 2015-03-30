package delta.soen390.mapsters.GeometricOverlays;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONObject;

import java.util.ArrayList;

import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.R;
import delta.soen390.mapsters.Utils.GoogleMapCamera;

/**
 * Created by Mathieu on 3/21/2015.
 */
public class PolygonOverlay {

    //Used to test for touch input
    protected BoundingBox2D mBoundingBox2D;
    protected GoogleMap mGoogleMap;
    protected Polygon mPolygon;
    private     ArrayList<String> mAttributeList = new ArrayList<String>();
    private int mFocusedColor;
    private int mUnfocusedColor;

    private LatLng mCenterPoint;
    protected PolygonOverlayManager mPolygonManager;
    protected GoogleMapCamera mMapCamera;

    public PolygonOverlay(MapsActivity activity) {
        mGoogleMap = activity.getGoogleMap();
        mPolygonManager = activity.getPolygonOverlayManager();
        mMapCamera = activity.getGoogleMapCamera();
    }


    public boolean createPolygon(ArrayList<LatLng> boundingPoints)
    {
//Create the Polygon Options which will be used to instantiate the actual google map Polygon UI element

        PolygonOptions polygonOptions = new PolygonOptions();

        double totalLatitude = 0,totalLongitude = 0;
        //Load in the vertices!
        for(int i = 0; i < boundingPoints.size(); ++i)
        {
            LatLng point = boundingPoints.get(i);
            if(i != boundingPoints.size() - 1)
            {
                totalLatitude += point.latitude;
                totalLongitude += point.longitude;
            }
            polygonOptions.add(point);
        }

        int numberOfValues = boundingPoints.size() - 1;
        mCenterPoint = new LatLng(totalLatitude/numberOfValues,totalLongitude/numberOfValues);


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

    public void activate()
    {
        mPolygon.setVisible(true);
    }

    public void deactivate()
    {
        mPolygon.setVisible(false);
    }

    public void highlight(int color)
    {
        mPolygon.setFillColor(color);
    }
    public void unhighlight()
    {
        mPolygon.setFillColor(mUnfocusedColor);
    }
    public void focus()
    {
        mPolygonManager.focusOverlay(this);
        setFillColor(mFocusedColor);
    }

    public void addAttribute(String attribute)
    {
        mAttributeList.add(attribute);
    }
    public void removeAttribute(String attribute)
    {
        for(String str : mAttributeList)
        {
            if(str.equals(attribute))
            {
                mAttributeList.remove(str);
            }
        }
    }

    public boolean containsAttribute(String attribute)
    {
        for(String str : mAttributeList)
        {
            if(str.equals(attribute))
                return true;
        }
        return false;
    }

    public void clearAttributes()
    {
        mAttributeList.clear();
    }

    public void unfocus()
    {
        setFillColor(mUnfocusedColor);
    }

}
