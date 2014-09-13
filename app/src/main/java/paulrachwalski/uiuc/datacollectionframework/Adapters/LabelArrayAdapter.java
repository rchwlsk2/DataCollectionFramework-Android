package paulrachwalski.uiuc.datacollectionframework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

/**
 * Created by paulrachwalski on 9/4/14.
 */
public class LabelArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> labels;

    public LabelArrayAdapter(Context context) {
        super(context, R.layout.item_brief_label);
        this.context = context;
        this.labels = ProjectManager.getInstance().getLabels();
    }

    @Override
    public int getCount() {
        return this.labels.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_brief_label, parent, false);
        TextView labelText = (TextView) view.findViewById(R.id.text_label);

        labelText.setText(labels.get(position));

        return view;
    }
}
