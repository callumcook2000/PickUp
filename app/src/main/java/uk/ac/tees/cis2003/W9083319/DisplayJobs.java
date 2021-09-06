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

public class DisplayJobs extends AppCompatActivity {

    public Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_jobs);
        DisplayJob displayJob  = new DisplayJob();
        displayJob.execute();
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



    public class DisplayJob extends AsyncTask<String, String, String> {
        String message = "";


        @Override
        protected String doInBackground(String... strings) {

            try {
                con= connectionclass();
                if (con == null) {
                    message = "Bad connection";
                } else {
                    PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM [dbo].[User]");

                    selectStatement.executeUpdate();
                    message = "success";

                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return message;
        }
    }
}