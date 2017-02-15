package thanksmatrix.tmcontact;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.TextView;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Search extends AppCompatActivity {

    private ConnectionClass connectionClass;


    private TextView fname;
    private TextView lname;
    private TextView company;
    private TextView phone;
    private TextView workPhone;
    private TextView email;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        connectionClass = new ConnectionClass();
        fname = (TextView) findViewById(R.id.fnameValue);
        lname = (TextView) findViewById(R.id.lnameValue);
        company = (TextView) findViewById(R.id.companyValue);
        phone = (TextView) findViewById(R.id.phoneValue);
        workPhone = (TextView) findViewById(R.id.wPhoneValue);
        email = (TextView) findViewById(R.id.emailValue);
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


