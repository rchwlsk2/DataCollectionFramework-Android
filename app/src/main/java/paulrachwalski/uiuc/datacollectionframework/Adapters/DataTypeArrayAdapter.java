package paulrachwalski.uiuc.datacollectionframework.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Data.DataType;
import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

/**
 * Created by paulrachwalski on 9/4/14.
 */
public class DataTypeArrayAdapter extends ArrayAdapter<String> {

    private Context context;
    private DataType[] dataTypes;

    public DataTypeArrayAdapter(Context context) {
        super(context, R.layout.item_brief_label);
        this.context = context;
        this.dataTypes = ProjectManager.getInstance().getDataTypes();
    }

    @Override
    public int getCount() {
        return this.dataTypes.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_data_type, parent, false);
        ImageView dataImage = (ImageView) view.findViewById(R.id.data_image);
        TextView dataText = (TextView) view.findViewById(R.id.data_text);
        CheckBox dataChecked = (CheckBox) view.findViewById(R.id.data_checked);

        dataImage.setImageResource(dataTypes[position].getDrawable());
        dataText.setText(dataTypes[position].getName());
        dataChecked.setChecked(dataTypes[position].isUsing());

        return view;
    }
}
