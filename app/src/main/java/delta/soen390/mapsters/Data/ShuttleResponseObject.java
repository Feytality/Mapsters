package delta.soen390.mapsters.Data;

import java.util.Calendar;
import java.util.Date;

public class ShuttleResponseObject {
    private Campus mCampus;
    private Calendar mShuttleDepart;
    private Calendar mShuttleArrive;
    private boolean mPreferable;

    public ShuttleResponseObject(boolean mPreferable, Calendar mShuttleArrive, Calendar mShuttleDepart, Campus mCampus) {
        this.mPreferable = mPreferable;
        this.mShuttleArrive = mShuttleArrive;
        this.mShuttleDepart = mShuttleDepart;
        this.mCampus = mCampus;
    }

    public Campus getmCampus() {
        return mCampus;
    }

    public Calendar getmShuttleDepart() {
        return mShuttleDepart;
    }

    public Calendar getmShuttleArrive() {
        return mShuttleArrive;
    }

    public boolean ismPreferable() {
        return mPreferable;
    }
}
