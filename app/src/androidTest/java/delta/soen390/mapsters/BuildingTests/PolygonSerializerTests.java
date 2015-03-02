package delta.soen390.mapsters.BuildingTests;

import android.test.InstrumentationTestCase;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONObject;

import java.util.ArrayList;

import delta.soen390.mapsters.Buildings.BuildingPolygon;
import delta.soen390.mapsters.Buildings.PolygonSerializer;

/**
 * Created by Patrick on 15-02-23.
 *
 */
public class PolygonSerializerTests extends InstrumentationTestCase {

    private PolygonSerializer mPolygonSerializer;
    private GoogleMap mGoogleMap;

    @Override
    protected void setUp() throws Exception {
        mPolygonSerializer = new PolygonSerializer(mGoogleMap);
    }

    public void testPolygonSerializer() throws Exception {
        // Although mGoogleMap might be null, mPolygonSerializer is not
        assertNotNull(mPolygonSerializer);
    }

    public void testCreatePolygonArray() throws Exception {
        ArrayList<BuildingPolygon> retVal = mPolygonSerializer.createPolygonArray(null);
        assertNull(retVal);
    }
}
