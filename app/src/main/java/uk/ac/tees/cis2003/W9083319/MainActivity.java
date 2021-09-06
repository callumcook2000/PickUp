package uk.ac.tees.cis2003.W9083319;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.ClassNotFoundException;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public Connection connectionclass()
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://pick-up-app.database.windows.net:1433;DatabaseName=User;user=w9083319@pick-up-app;password=Cal1ben2@aron345;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request;";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException | ClassNotFoundException se)
        {
            Log.e("error:", se.getMessage());


        }

        return connection;
    }

    public void register_btn_click(View view)
    {
        RegisterAccount registerAccount = new RegisterAccount();
        registerAccount.execute();
        startActivity(new Intent(MainActivity.this, Login.class));



    }

    public void Login_btn_click(View view) {
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public class RegisterAccount extends AsyncTask<String, String, String> {
        String message = "";


        @Override
        protected String doInBackground(String... strings) {
            /*Registration Page */
            EditText editFullName = findViewById(R.id.FullName_txtbx);
            EditText editAddress = findViewById(R.id.Address_txtbx);
            EditText editPostcode = findViewById(R.id.Postcode_txtbx2);
            EditText editDOB = findViewById(R.id.DOB_txtbx3);
            EditText editUsername = findViewById(R.id.Username_txtbx);
            EditText editPassword = findViewById(R.id.Password_txtbx);


            try {
                con= connectionclass();
                if (con == null) {
                    message = "Check your internet access";
                } else {
                    PreparedStatement insertStatement = con.prepareStatement("INSERT INTO [dbo].[User] (Username, FullName, Password, Address, PostCode, DOB) VALUES (?, ?, ?, ?, ?, ?)");

                    insertStatement.setString(1, String.valueOf(editUsername.getText()));
                    insertStatement.setString(2, String.valueOf(editFullName.getText()));
                    insertStatement.setString(3, String.valueOf(editPassword.getText()));
                    insertStatement.setString(4, String.valueOf(editAddress.getText()));
                    insertStatement.setString(5, String.valueOf(editPostcode.getText()));
                    insertStatement.setInt(6, Integer.parseInt(String.valueOf(editDOB.getText())));


                    insertStatement.executeUpdate();
                    message = "INSERT INTO success";

                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return message;
        }
    }

}