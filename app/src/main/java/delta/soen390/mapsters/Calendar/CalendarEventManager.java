package delta.soen390.mapsters.Calendar;


import android.content.Context;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Mathieu on 2/20/2015.
 */
public class CalendarEventManager {
    private CalendarEventSerializer mSerializer;

    private LinkedList<CalendarEvent> mCalendarEventQueue;

    private int mQueueSize = 5;

    public CalendarEventManager(Context context)
    {
        mCalendarEventQueue = new LinkedList<CalendarEvent>();
        mSerializer = new CalendarEventSerializer(context);
    }


    public void updateEventQueue()
    {
        mCalendarEventQueue.clear();
        ArrayList<CalendarEvent> calendarEvents = mSerializer.getUpcomingEvents(mQueueSize);
        if(calendarEvents == null)
            return;
        mCalendarEventQueue.addAll(calendarEvents);

    }

    public void setQueueSize(int queueSize)
    {
        if(mQueueSize == queueSize)
            return;
        mQueueSize = queueSize;
        updateEventQueue();
    }

    public int getQueueSize()
    {
        return mCalendarEventQueue.size();
    }

    public CalendarEvent getNextEvent()
    {
        return mCalendarEventQueue.getFirst();
    }
}
