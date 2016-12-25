package com.example.anshraj.fblog;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity  {
    DBHelper dbHelper=null;
    String id;
    private TextView tx;
    public final static String TAG="Mainasa";
    public final static String rTAG="MyApplication";

    LoginButton loginButton;


       CallbackManager callbackManager; //store all the process of login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext()); // facebook software dev tol kit initialized

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText=(EditText)findViewById(R.id.editText);
        final EditText editText2=(EditText)findViewById(R.id.editText2);;

        callbackManager= CallbackManager.Factory.create(); //callback manager initialized
        Button b=(Button)findViewById(R.id.button);

        // geting Amazon Credentials



   b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   final String h=editText.getText().toString();
                Log.i("hey",h);
                new AsyncTask<Void, Void, UserCredAwsMapping>() {
                    @Override
                    protected UserCredAwsMapping doInBackground(Void... params) {
                        final DynamoDBMapper mapper=getAWScred();
                        UserCredAwsMapping  userCredAwsMapping = mapper.load(UserCredAwsMapping.class,h);
        return userCredAwsMapping;
                    }
                     @Override
                    protected void onPostExecute(UserCredAwsMapping aVoid) {
                       if(aVoid.getPassword().equalsIgnoreCase(editText2.getText().toString()))
                        {
                            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                            intent.putExtra("email",editText.getText().toString());
       startActivity(intent);
               }
            }
                }.execute();
      }
        });

        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final UserCredAwsMapping userCredAwsMapping=new UserCredAwsMapping();
                userCredAwsMapping.setUsername(editText.getText().toString());
                userCredAwsMapping.setPassword(editText2.getText().toString());

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        final DynamoDBMapper mapper=getAWScred();
                        mapper.save(userCredAwsMapping);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra("username",editText.getText().toString());
                        startActivity(intent);
  }
                }.execute();

               ;//sending the content to the second activity
            }
        });


        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        //accessing permission fr login button

        Log.i(TAG,"login manager and loginbutton intialized");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {   //we need to register login button to facebook call
                                                                                              // back event handling
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
              getUserInfo(loginResult);

                Log.i(TAG,"come pick me her");
            }

           @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
                onErrr();

            }
        });

    }





    private DynamoDBMapper getAWScred() {

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:a4619197-1259-4604-8e8b-ab8865b38202", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);

        final DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);
        return mapper;

    }






    public void onErrr()
    {
        Toast.makeText(this,"no internet",Toast.LENGTH_LONG).show();
     }

    protected void getUserInfo(LoginResult login_result){

        GraphRequest data_request = GraphRequest.newMeRequest( //grapgh object dat will store info of user profile
                login_result.getAccessToken(), //access token
                new GraphRequest.GraphJSONObjectCallback() { //annonymus class implmenting interface GraphJSONObjectCallback
                                                               //whose method is onCompleted of user login this method iz callback method
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {

                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra("jsondata",json_object.toString()); //storing the jsaondata in intent
                        startActivity(intent);
                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }


      @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) { //differentiate intents and der request
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"rolling_back");
        Log.e("data",data.toString());

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

}
