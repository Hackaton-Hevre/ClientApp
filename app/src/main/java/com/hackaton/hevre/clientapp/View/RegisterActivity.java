package com.hackaton.hevre.clientapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapp.Control.RESTService.GetResponseCallback;
import com.hackaton.hevre.clientapp.Control.RESTService.HypotheticalApi;
import com.hackaton.hevre.clientapp.Control.LOGIN_STATUS;
import com.hackaton.hevre.clientapp.R;


public class RegisterActivity extends ActionBarActivity {

    private HypotheticalApi mApi;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mApi = HypotheticalApi.getInstance();

        mRegisterButton = (Button) findViewById(R.id.Registerbutton_text_re);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((TextView) findViewById(R.id.username_text_re)).getText().toString();
                String email = ((TextView) findViewById(R.id.Email_text_re)).getText().toString();
                String password = ((TextView) findViewById(R.id.password_text_re)).getText().toString();
                String repassword = ((TextView) findViewById(R.id.repassword_text_re)).getText().toString();

                if(checkRegisterInput(username,email,password,repassword)){
                    mApi.Register(username, email, password, email, new GetResponseCallback<String>() {
                        @Override
                        public void onDataReceived(String result) {
                            try
                            {
                                int res = Integer.parseInt(result);
                                switch (res) {
                                case LOGIN_STATUS.NAME_ALREADY_EXISTS:
                                    Toast.makeText(getBaseContext(), "User name is already exist", Toast.LENGTH_SHORT).show();
                                    break;
                                case LOGIN_STATUS.MAIL_AREADY_EXIST:
                                    Toast.makeText(getBaseContext(), "Mail is already exist", Toast.LENGTH_SHORT).show();
                                    break;
                                case LOGIN_STATUS.SUCCESS:
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                                    break;
                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getBaseContext(), "Some error occured, try again later", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    });
                }
            }
            private boolean checkRegisterInput(String username, String email, String password, String repassword ) {
                StringBuilder errorMsg = new StringBuilder();
                boolean flag1 = false, flag2 = false;

                if (username.matches("^.*[^a-zA-Z0-9 ].*$") || username.isEmpty()) {
                    errorMsg.append("- Username invalid\n");
                }
                if (!email.contains("@") || email.isEmpty()) {
                    errorMsg.append("- Email address is invalid\n");
                }

                if (password.isEmpty()) {
                    errorMsg.append("- Password is empty\n");
                    flag1 = true;
                }
                if (repassword.isEmpty()) {
                    errorMsg.append("- Re checking password is empty\n");
                    flag2 = true;
                }
                if (flag1 == false && flag2 == false) {
                    if (!password.equals(repassword))
                        errorMsg.append("- Passwords are NOT equal");
                }
                String eMsg = errorMsg.toString();
                if (!eMsg.isEmpty())
                {
                    eMsg = eMsg + "PLEASE INSERT CORRECT DETAILS";
                    Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
