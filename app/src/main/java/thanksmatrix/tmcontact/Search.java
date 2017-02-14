package thanksmatrix.tmcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Search extends AppCompatActivity {

    private ConnectionClass connectionClass;

    private Button scannerButton;
    private Button updateButton;
    private Button findButton;

    private EditText fname;
    private EditText lname;
    private EditText company;
    private EditText phone;
    private EditText workPhone;
    private EditText email;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        connectionClass = new ConnectionClass();
        fname = (EditText) findViewById(R.id.fnameValue);
        lname = (EditText) findViewById(R.id.lnameValue);
        company = (EditText) findViewById(R.id.companyValue);
        phone = (EditText) findViewById(R.id.phoneValue);
        workPhone = (EditText) findViewById(R.id.wPhoneValue);
        email = (EditText) findViewById(R.id.emailValue);
        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        String username = prefs.getString("UN", "UNKNOWN");

        Connection con = connectionClass.CONN();
        String query = "select email, company, Personf, Personl, phone1, phone2 from dbo.customers where Merchant='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                email.setText(rs.getString(1), TextView.BufferType.EDITABLE);
                company.setText(rs.getString(2), TextView.BufferType.EDITABLE);
                fname.setText(rs.getString(3), TextView.BufferType.EDITABLE);
                lname.setText(rs.getString(4), TextView.BufferType.EDITABLE);
                phone.setText(rs.getString(5), TextView.BufferType.EDITABLE);
                workPhone.setText(rs.getString(6), TextView.BufferType.EDITABLE);

            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}


