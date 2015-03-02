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

    public CalendarEventManager(Context context) {
        mCalendarEventQueue = new LinkedList<>();
        mSerializer = new CalendarEventSerializer(context);
    }

    public void updateEventQueue() {
        mCalendarEventQueue.clear();
        ArrayList<CalendarEvent> calendarEvents = mSerializer.getUpcomingEvents(mQueueSize);
        if(calendarEvents == null) {
            return;
        }
        mCalendarEventQueue.addAll(calendarEvents);
    }

    public void setQueueSize(int queueSize) {
        if(mQueueSize == queueSize) {
            return;
        }
        if (mQueueSize > 0) {
            mQueueSize = queueSize;
            updateEventQueue();
        }
    }

    public int getQueueSize()
    {
        return mCalendarEventQueue.size();
    }

    public CalendarEvent getNextEvent() {
        if(mCalendarEventQueue.size() > 0) {
            return mCalendarEventQueue.getFirst();
        } else {
            return null;
        }
    }

    public LinkedList<CalendarEvent> getCalendarEventQueue() {
        return mCalendarEventQueue;
    }

    /**
     * Removes the next event in the event queue (meaning the notification for the event has shown)
     */
    public void popNextEvent() {
        if(mCalendarEventQueue != null){
            if(!mCalendarEventQueue.isEmpty()) {
                mCalendarEventQueue.removeFirst();
            } else {
                // Can't pop from empty list.
            }
        } else{
            // can't pop from a null list.
        }
    }

    public void resyncEvents() {
        // TODO
    }
}
