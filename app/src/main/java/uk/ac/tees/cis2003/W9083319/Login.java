package uk.ac.tees.cis2003.W9083319;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login extends AppCompatActivity {

    public Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void register_btn_click(View view) {
        startActivity(new Intent(Login.this, MainActivity.class));
    }

    public void Login_btn_click(View view) {

        Login.LoginAccount loginAccount = new Login.LoginAccount();
        loginAccount.execute();
        startActivity(new Intent(Login.this, AddJobs.class));
    }
    public class LoginAccount extends AsyncTask<String, String, String> {
        String message = "";


        @Override
        protected String doInBackground(String... strings) {
            /*Registration Page */
            EditText editUsername = findViewById(R.id.UserName_txtbx);
            EditText editPassword = findViewById(R.id.Password_txtbx);


            try {
                con= connectionclass();
                if (con == null) {
                    message = "Bad connection";
                } else {
                    PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM [dbo].[User] WHERE (Username = ? OR Password = ?)");

                    selectStatement.setString(1, String.valueOf(editUsername.getText()));
                    selectStatement.setString(2, String.valueOf(editPassword.getText()));



                    selectStatement.executeUpdate();
                    message = "Login success";

                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return message;
        }
    }
}