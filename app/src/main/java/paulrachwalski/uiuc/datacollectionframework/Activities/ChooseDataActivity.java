package paulrachwalski.uiuc.datacollectionframework.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Adapters.DataTypeArrayAdapter;
import paulrachwalski.uiuc.datacollectionframework.Data.DataType;
import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

public class ChooseDataActivity extends Activity {

    private ActionBar actionBar;
    private ListView dataTypesList;

    private DataTypeArrayAdapter dataTypeArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_data);

        actionBar = getActionBar();
        actionBar.setTitle(R.string.title_activity_choose_data);

        dataTypeArrayAdapter = new DataTypeArrayAdapter(this);
        dataTypesList = (ListView) findViewById(R.id.data_types_list);

        dataTypesList.setAdapter(dataTypeArrayAdapter);
        dataTypesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataType dataType = ProjectManager.getInstance().getDataType(position);
                dataType.setUsing(!dataType.isUsing());
                dataTypeArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_next) {
            Intent i = new Intent(ChooseDataActivity.this, AddLabelsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
