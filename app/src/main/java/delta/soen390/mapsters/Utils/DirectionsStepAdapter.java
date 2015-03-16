package delta.soen390.mapsters.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import delta.soen390.mapsters.Controller.DirectionStep;
import delta.soen390.mapsters.R;

/**
 * Created by Felicia on 2015-03-14.
 */
public class DirectionsStepAdapter extends RecyclerView.Adapter<DirectionsStepAdapter.DirectionsStepViewHolder> {

    private List<DirectionStep> contactList;

    public DirectionsStepAdapter(List<DirectionStep> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(DirectionsStepViewHolder directionsStepViewHolder, int i) {
        DirectionStep ds = contactList.get(i);
        directionsStepViewHolder.vSteps.setText(ds.getSteps().toString());
        directionsStepViewHolder.vStepTitle.setText(ds.getStep());
    }

    @Override
    public DirectionsStepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.steps_card, viewGroup, false);

        return new DirectionsStepViewHolder(itemView);
    }

    /**
     * Created by Felicia on 2015-03-14.
     */
    public class DirectionsStepViewHolder extends RecyclerView.ViewHolder {
        protected TextView vSteps;
        protected TextView vStepTitle;

        public DirectionsStepViewHolder(View v) {
            super(v);
            vSteps = (TextView)  v.findViewById(R.id.stepText);
            vStepTitle = (TextView) v.findViewById(R.id.stepTitle);
        }
    }
}