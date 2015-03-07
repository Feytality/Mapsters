package delta.soen390.mapsters.BuildingTests;
import android.test.AndroidTestCase;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import delta.soen390.mapsters.Geometry.BoundingBox2D;
import delta.soen390.mapsters.Geometry.Vector2D;

/**
 * Created by Patrick on 15-02-22.
 *
 */

public class BuildingPolygonTests extends AndroidTestCase {

    private BoundingBox2D mBoundingBox2D;
    private ArrayList<LatLng> mBoundingPoints;

    @Override
    protected void setUp() throws Exception {
        mBoundingPoints = new ArrayList<>();

        LatLng point1 = new LatLng(0,0);
        LatLng point2 = new LatLng(0,2);
        LatLng point3 = new LatLng(2,2);
        LatLng point4 = new LatLng(2,0);

        mBoundingPoints.add(point1);
        mBoundingPoints.add(point2);
        mBoundingPoints.add(point3);
        mBoundingPoints.add(point4);
    }

    public void testIsPointInsidePolygon() throws Exception {
        LatLng testPoint = new LatLng(1,1);
        mBoundingBox2D = new BoundingBox2D(mBoundingPoints);
        assertTrue(mBoundingBox2D.isPointInsideBoundingBox(new Vector2D(testPoint.latitude, testPoint.longitude)));
    }

}
