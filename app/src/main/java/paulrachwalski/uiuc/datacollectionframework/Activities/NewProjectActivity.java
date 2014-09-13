package paulrachwalski.uiuc.datacollectionframework.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import paulrachwalski.uiuc.datacollectionframework.Managers.ProjectManager;
import paulrachwalski.uiuc.datacollectionframework.R;

public class NewProjectActivity extends Activity {

    private EditText projectNameEditText;
    private EditText userIdEditText;
    private Button createProjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        projectNameEditText = (EditText) findViewById(R.id.project_name_editText);
        userIdEditText = (EditText) findViewById(R.id.user_id_editText);
        createProjectButton = (Button) findViewById(R.id.create_project_button);

        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean goodFields = true;

                // Check and set project name
                String projectName = projectNameEditText.getText().toString();
                if (projectName != null && !projectName.equals("")) {
                    ProjectManager.getInstance().setName(projectName);
                    goodFields = true;
                }
                else {
                    projectNameEditText.setError("Invalid Project Name");
                    goodFields = false;
                }

                // Check and set user id
                String userId = userIdEditText.getText().toString();
                if (userId != null && !userId.equals("")) {
                    ProjectManager.getInstance().setUserId(userId);
                    goodFields = true;
                }
                else {
                    userIdEditText.setError("Invalid User ID");
                    goodFields = false;
                }

                // If checks pass, continue
                if (goodFields) {
                    Intent i = new Intent(NewProjectActivity.this, ChooseDataActivity.class);
                    startActivity(i);
                }
            }
        });
    }

}
