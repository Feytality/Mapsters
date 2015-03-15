package delta.soen390.mapsters.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import delta.soen390.mapsters.R;


public class SearchBarFragment extends Fragment {

    private ImageButton mSearchButton;
    private AutoCompleteTextView mTextView;
    private InputMethodManager mImm;

    SearchBarListener activityCommander;

    public interface SearchBarListener{
        public void searchForRoom(String input);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander = (SearchBarListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View searchBarView = inflater.inflate(R.layout.search_bar_fragment,container, false);

        mSearchButton = (ImageButton)searchBarView.findViewById(R.id.search_button);
        mTextView = (AutoCompleteTextView) searchBarView.findViewById(R.id.search_text_input);
        mImm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SearchButton","The cancel button has been clicked");
                mTextView.setText("");
            }
        });

        mTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("SearchButton","search clicked");
                searchButtonClicked(v);
                mImm.hideSoftInputFromWindow(mTextView.getWindowToken(), 0);
                return false;
            }
        });

        return searchBarView;
    }

    public void searchButtonClicked(View v){
        activityCommander.searchForRoom(mTextView.getText().toString());
    }
}
