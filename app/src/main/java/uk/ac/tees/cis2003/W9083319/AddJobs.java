package uk.ac.tees.cis2003.W9083319;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddJobs extends AppCompatActivity {

    public Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs);
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

    public void AddJob_btn_click(View view) {
        AddJob addJob  = new AddJob();
        addJob.execute();
    }
    public class AddJob extends AsyncTask<String, String, String> {
        String message = "";


        @Override
        protected String doInBackground(String... strings) {
            /*Registration Page */
            EditText editTitle = findViewById(R.id.Title_txtbx);
            EditText editOrigin = findViewById(R.id.origin_txtbx);
            EditText editDestination = findViewById(R.id.Destination_txtbx);
            EditText editDescription = findViewById(R.id.Desc_txtbx);


            try {
                con= connectionclass();
                if (con == null) {
                    message = "Check your internet access";
                } else {
                    PreparedStatement insertStatement = con.prepareStatement("INSERT INTO [dbo].[Orders] (Title, OriginAddr, DestinationAddr, Description) VALUES (?, ?, ?, ?)");

                    insertStatement.setString(1, String.valueOf(editTitle.getText()));
                    insertStatement.setString(2, String.valueOf(editOrigin.getText()));
                    insertStatement.setString(3, String.valueOf(editDestination.getText()));
                    insertStatement.setString(4, String.valueOf(editDescription.getText()));



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