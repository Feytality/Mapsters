package delta.soen390.mapsters.IndoorDirectory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import delta.soen390.mapsters.Activities.MapsActivity;
import delta.soen390.mapsters.GeometricOverlays.IPolygonOverlayFactory;
import delta.soen390.mapsters.R;

/**
 * Created by Mathieu on 3/25/2015.
 */
public class RoomPolygonOverlayFactory implements IPolygonOverlayFactory {

    private MapsActivity mapsActivity;
    public RoomPolygonOverlayFactory(MapsActivity activity)
    {
        mapsActivity = activity;
    }
    private class LoadingData
    {
        public String Name;
        public ArrayList<LatLng> BoundingCoordinates;
        public String Description;
    }

    public ArrayList<RoomPolygonOverlay> generatePolygonOverlay(String filename)
    {
        ArrayList<RoomPolygonOverlay> overlays = new ArrayList<RoomPolygonOverlay>();

        final String nameDescriber = mapsActivity.getResources().getString(R.string.overlay_room_name);
        final String boundingCoordinateDescriber = mapsActivity.getResources().getString(R.string.overlay_room_bounding_coordinates);
        final String descriptionDescriber = mapsActivity.getResources().getString(R.string.overlay_room_description);
        final String startingElementDescriptor = mapsActivity.getResources().getString(R.string.overlay_room_starting_element);

        final ArrayList<LoadingData> loadingData = new ArrayList<LoadingData>();


        try {


            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            InputStream inputStream = mapsActivity.getAssets().open(filename);


            DefaultHandler handler = new DefaultHandler() {
                private boolean isName = false;
                private boolean isDescription = false;
                private boolean isBoundingCoordinates = false;

                private StringBuilder boundingCoordinatesStringBuilding = new StringBuilder();

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {
                    if(qName.equals(startingElementDescriptor))
                    {
                        loadingData.add(new LoadingData());
                    }
                    isName = qName.equals(nameDescriber);
                    isDescription = qName.equals(descriptionDescriber);
                    isBoundingCoordinates = qName.endsWith(boundingCoordinateDescriber);

                    //Bebin a new string builder in order to extract all of the boundary coordinates
                    if(isBoundingCoordinates)
                    {
                        boundingCoordinatesStringBuilding = new StringBuilder();
                    }

                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {

                    if(loadingData.size() == 0) {
                        return;
                    }
                    LoadingData data = loadingData.get(loadingData.size() - 1);


                    //Create the boundary coordinates from the recuperated string
                    if(isBoundingCoordinates)
                    {
                        //Get coordinate array
                        String[] coordinateStringArray = boundingCoordinatesStringBuilding.toString().split(" |,");
                        data.BoundingCoordinates = new ArrayList<>();
                        for (int i = 0; i < coordinateStringArray.length; i += 3) {
                            double lat = Double.parseDouble(coordinateStringArray[i]);
                            double lng = Double.parseDouble(coordinateStringArray[i + 1]);

                            data.BoundingCoordinates.add(new LatLng(lng, lat));
                        }
                        isBoundingCoordinates = false;
                    }
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    if(loadingData.size() == 0) {
                        return;
                    }
                    LoadingData data = loadingData.get(loadingData.size() - 1);
                    String qName = new String(ch,start,length);

                    if(isName)
                    {

                        data.Name = qName;
                        isName =false;
                    }
                    else if(isBoundingCoordinates)
                    {
                        this.boundingCoordinatesStringBuilding.append(new String(ch,start,length));

                    }
                    else if(isDescription)
                    {
                        data.Description = qName;
                        isDescription  =false;
                    }
                }

            };

            saxParser.parse(inputStream, handler);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        for(LoadingData data : loadingData)
        {
            RoomPolygonOverlay overlay = new RoomPolygonOverlay(mapsActivity,data.Name,data.Description);
            if(overlay.createPolygon(data.BoundingCoordinates))
            {
                //TODO pass this as parameter
                overlay.setBorderWidth(4.0f);
                overlays.add(overlay);
            }

        }
        return overlays;
    }
}
