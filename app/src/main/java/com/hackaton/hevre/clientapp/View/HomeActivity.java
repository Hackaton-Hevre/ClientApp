package com.hackaton.hevre.clientapp.View;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapp.Control.RESTService.GetResponseCallback;
import com.hackaton.hevre.clientapp.Control.RESTService.HypotheticalApi;
import com.hackaton.hevre.clientapp.Control.RESTService.PostCallback;
import com.hackaton.hevre.clientapp.R;


public class HomeActivity extends ActionBarActivity {

    private Button mSearchButon;
    private HypotheticalApi mApi = HypotheticalApi.getInstance();
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle b = getIntent().getExtras();
        if(b != null) {
            mUserName = b.getString("user");
        }
        initAllElement();
    }



    private void initAllElement() {
        initButtons();
    }

    private void initButtons()
    {
        final SearchView src = (SearchView) findViewById(R.id.ItemForLocate);
        mSearchButon = (Button) findViewById(R.id.search_button);

        mSearchButon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final String Prod = src.getQuery().toString();
                mApi.findProduct(Prod, new GetResponseCallback<String>() {
                    @Override
                    public void onDataReceived(String answer) {

                        if (answer.equals("1")) {
                            askDialogQuestion(getString(R.string.confirmation_title), getString(R.string.reminder_question) + Prod + "?", Prod);
                        } else {
                            Toast.makeText(getBaseContext(), Prod + " not found", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }


        });
    }

    private void askDialogQuestion(String title, String question, final String product) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle(title);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.title_question);
        text.setText(question);

        Button dialogButtonYes = (Button) dialog.findViewById(R.id.dialogButtonYes);
        // if button is clicked, close the custom dialog
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.saveReminder(mUserName, product, new PostCallback() {
                    @Override
                    public void onPostSuccess(String result) {
                        if (result.equals("1"))
                        {
                            Toast.makeText(getBaseContext(), product + " added successfully", Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                        }
                    }
                });
                dialog.dismiss();
            }
        });

        Button dialogButtonNo = (Button) dialog.findViewById(R.id.dialogButtonNo);
        // if button is clicked, close the custom dialog
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean getProuduct() {
        return true;
    }

}
