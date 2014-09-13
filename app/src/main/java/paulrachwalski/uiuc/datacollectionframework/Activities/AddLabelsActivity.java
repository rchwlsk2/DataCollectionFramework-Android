package paulrachwalski.uiuc.datacollectionframework.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import paulrachwalski.uiuc.datacollectionframework.Adapters.LabelArrayAdapter;
import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

public class AddLabelsActivity extends Activity {

    private TextView noLabelsTextView;
    private Button addLabelButton;
    private ListView labelsListView;

    private LabelArrayAdapter labelArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labels);

        labelArrayAdapter = new LabelArrayAdapter(this);

        noLabelsTextView = (TextView) findViewById(R.id.no_labels_text);
        addLabelButton = (Button) findViewById(R.id.add_label_button);
        labelsListView = (ListView) findViewById(R.id.label_list);

        labelsListView.setAdapter(labelArrayAdapter);
        adjustVisibility();
        registerListeners();
    }

    private void adjustVisibility() {
        if (ProjectManager.getInstance().getNumberOfLabels() == 0) {
            labelsListView.setVisibility(View.GONE);
            noLabelsTextView.setVisibility(View.VISIBLE);
            addLabelButton.setVisibility(View.VISIBLE);
        }

        else {
            noLabelsTextView.setVisibility(View.GONE);
            addLabelButton.setVisibility(View.GONE);
            labelsListView.setVisibility(View.VISIBLE);
        }
    }

    private void registerListeners() {
        addLabelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLabelDialog();
            }
        });

        labelsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AddLabelsActivity.this);

                alert.setTitle("Remove Label");
                alert.setMessage("Are you sure you want to delete this label?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProjectManager.getInstance().removeLabel(position);
                        labelArrayAdapter.notifyDataSetChanged();
                        adjustVisibility();
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            }
        });
    }

    private void addLabelDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Enter Label Name");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String labelName = input.getText().toString();
                ProjectManager.getInstance().addLabel(labelName);
                labelArrayAdapter.notifyDataSetChanged();
                adjustVisibility();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        labelArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_labels, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            addLabelDialog();
            return true;
        } else if (id == R.id.action_next) {
            Intent i = new Intent(AddLabelsActivity.this, CollectionActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
