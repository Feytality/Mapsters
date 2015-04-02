package delta.soen390.mapsters.GeometricOverlays;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

import delta.soen390.mapsters.Activities.MapsActivity;
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

    private LatLng mSouthWest, mNorthEast;

    private LatLng mCenterPoint;
    protected PolygonOverlayManager mPolygonManager;
    protected GoogleMapCamera mMapCamera;

    public PolygonOverlay(MapsActivity activity) {
        mGoogleMap = activity.getGoogleMap();
        mPolygonManager = activity.getPolygonOverlayManager();
        mMapCamera = activity.getGoogleMapCamera();
    }

    //Create the Polygon Options which will be used to instantiate the actual google map Polygon UI element
    public boolean createPolygon(ArrayList<LatLng> boundingPoints)
    {


        PolygonOptions polygonOptions = new PolygonOptions();

        //Null guard + make sure there's at least one element in the bounding points
        if(boundingPoints == null)
            return false;
        if(boundingPoints.isEmpty())
            return false;

        LatLng initialPoint = boundingPoints.get(0);

        double minLat = initialPoint.latitude,minLng = initialPoint.longitude,
                maxLat = initialPoint.latitude,maxLng = initialPoint.longitude;


        //Load in the vertices!
        for(int i = 0; i < boundingPoints.size(); ++i)
        {

            LatLng point = boundingPoints.get(i);

            //find the most southwest point
            minLat = Math.min(minLat,point.latitude);
            minLng = Math.min(minLng,point.longitude);

            //find the most northeast point
            maxLat = Math.max(maxLat,point.latitude);
            maxLng = Math.max(maxLng,point.longitude);

            polygonOptions.add(point);
        }

        mNorthEast = new LatLng(maxLat,maxLng);
        mSouthWest = new LatLng(minLat,minLng);

        mCenterPoint = new LatLng(
                (mNorthEast.latitude + mSouthWest.latitude) / 2,
                (mNorthEast.longitude + mSouthWest.longitude) / 2);


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

    public void focus()
    {
        mPolygonManager.focusOverlay(this);
        setFillColor(mFocusedColor);
        LatLngBounds bounds;

    }


    public LatLng getSouthWest()
    {
        return mSouthWest;
    }

    public LatLng getNorthEast()
    {
        return mNorthEast;
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

    public boolean hasAttributes()
    {
        return !mAttributeList.isEmpty();
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
