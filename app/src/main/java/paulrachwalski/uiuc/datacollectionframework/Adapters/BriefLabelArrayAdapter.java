package paulrachwalski.uiuc.datacollectionframework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

/**
 * Created by paulrachwalski on 9/4/14.
 */
public class BriefLabelArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> labels;

    public BriefLabelArrayAdapter(Context context) {
        super(context, R.layout.item_brief_label);
        this.context = context;
        this.labels = ProjectManager.getInstance().getLabels();
    }

    @Override
    public int getCount() {
         return this.labels.size() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_brief_label, parent, false);
        TextView labelText = (TextView) view.findViewById(R.id.text_label);

        if (position == 0) {
            labelText.setText("No Label");
        } else {
            labelText.setText(labels.get(position - 1));
        }

        if (ProjectManager.getInstance().getActiveLabel() == position) {
            view.setBackgroundColor(context.getResources().getColor(R.color.item_selected));
        } else {
            view.setBackgroundColor(0xFFFFFF);
        }

        return view;
    }
}
