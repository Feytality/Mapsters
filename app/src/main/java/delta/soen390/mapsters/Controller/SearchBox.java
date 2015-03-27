package delta.soen390.mapsters.Controller;

import android.app.Activity;

import com.github.amlcurran.showcaseview.ShowcaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cat on 3/25/2015.
 */
public class SearchBox {
    private List<String> mSearchableList;
    protected SearchBox(List<String> list){
        mSearchableList=list;
    }


    public static class Builder {
        private List<String> mSearchableList;

        public Builder(Activity activity){
                    mSearchableList= new ArrayList<String>();

        }

        public Builder addList(List<String> list){
            mSearchableList.addAll(list);
            return this;
        }

        public ShowcaseView build(){
            return null;
        }
    }



}
