package uk.ac.tees.cis2003.W9083319;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.ClassNotFoundException;


import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
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
            ConnectionURL = "jdbc:jtds:sqlserver://pick-up-app.database.windows.net:1433;DatabaseName=User;user=w9083319@pick-up-app;password=;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request;";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException | ClassNotFoundException se)
        {
            Log.e("error:", se.getMessage());


        }

        return connection;
    }

    public void btnclick(View view)
    {

        TextView textView = (TextView)findViewById(R.id.textView1);


        try{
            con = connectionclass();
            if (con == null)
            {
                textView.setText("con is null");
            }
            else
            {
                String query = "select * from User";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                textView.setText("Success");

            }
        }
        catch ( Exception ex)
        {
            Log.e("error:", ex.getMessage());
        }

    }

}