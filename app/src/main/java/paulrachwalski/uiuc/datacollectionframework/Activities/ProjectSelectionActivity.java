package paulrachwalski.uiuc.datacollectionframework.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import paulrachwalski.uiuc.datacollectionframework.R;

public class ProjectSelectionActivity extends Activity {

    private Button newProjectButton;
    private Button openProjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_selection);

        newProjectButton = (Button) findViewById(R.id.create_new_project_button);
        openProjectButton = (Button) findViewById(R.id.open_existing_project_button);

        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProjectSelectionActivity.this, NewProjectActivity.class);
                startActivity(i);
            }
        });
    }

}
