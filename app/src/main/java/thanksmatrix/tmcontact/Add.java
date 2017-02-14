package thanksmatrix.tmcontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Add extends AppCompatActivity implements OnClickListener {


    private ConnectionClass connectionClass;

    private Button addButton;
    private String username ="";
    private EditText name;
    private EditText company;
    private EditText phone;
    private EditText workPhone;
    private EditText email;
    private ProgressBar pbbar;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm.SS");
        date = sdf.format(new Date());

        connectionClass = new ConnectionClass();
        Intent intent = getIntent();
        name = (EditText) findViewById(R.id.nameValue);
        company = (EditText) findViewById(R.id.companyValue);
        company.setText(intent.getStringExtra("company"));
        phone = (EditText) findViewById(R.id.phoneValue);
        //phone.setText(intent.getStringExtra("phone"));
        workPhone = (EditText) findViewById(R.id.wPhoneValue);
        email = (EditText) findViewById(R.id.emailValue);

        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoImport doLogin = new DoImport();
                doLogin.execute("");
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
    public class DoImport extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        String username = prefs.getString("UN", "UNKNOWN");
        String uName = name.getText().toString();
        String[] nameArray = uName.split("\\s+");
        String fName = nameArray[0];
        String lName = nameArray[1];
        String uCompany = company.getText().toString();
        String uPhone = phone.getText().toString();
        String uWPhone = workPhone.getText().toString();
        String uEmail = email.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(Add.this,r,Toast.LENGTH_SHORT).show();
            if(isSuccess) {
                Intent i = new Intent(Add.this, Dashboard.class);
                startActivity(i);
                finish();
            }
        }
        @Override
        protected String doInBackground(String... params) {

            if(uName.trim().equals("") || uCompany.trim().equals("") || uWPhone.trim().equals(""))
                z = "Please enter all required fields!";
            else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "insert into dbo.customers" +
                                "(" +
                                "type, Ilevel, MerchantMasterID, Merchant, category, " +
                                "email, password, storeCredit, jobTitle, company, " +
                                "TaxID, reseller_num, DBA, business_type, Personf, " +
                                "Personl, DOB, DOBsms, phone1, phone2, " +
                                "fax, addr1, addr2, state, city," +

                                " zipcode, country, website, howfind, Note, " +
                                "event1, dateEvent1, event2, dateEvent2, event3," +
                                " dateEvent3, event4, dateEvent4, the_referrer, date_register, " +
                                "date_validate, date_lastlogin, date_firstorder, date_firstship, date_lastship," +
                                " is_valid, validate_admin, occupation, position, workphone," +

                                " companyaddr, companycity, companystate, companyzip, experience," +
                                " salary, religion, religionperiod, height, weight, " +
                                "bloodtype, militarycheck, glass, smoking, drinking," +
                                " healthcondition, character, koreanskill, hobby, specialty," +
                                " license, education_middle, education_high, education_college, education_college_major, " +

                                "education_master, education_master_major, education_doctor, education_doctor_major, asset_level, " +
                                "asset_estate, asset_realestate, asset_vehicle, marriage_type, marriage_year," +
                                " marriage_period, marriage_end, marriage_end_reason, family_status, child_status, " +
                                "wishpriority1, wishpriority2, wishpriority3, wish_religion, wish_education," +
                                " wish_age, wish_job, wish_no_job, wish_style, wish_height," +

                                " wish_weight, wish_character, wish_extra, meeting_weekday, meeting_weekday_location," +
                                " meeting_weekend, meeting_weekend_location, signup_person_relation, approve, EPersonf," +
                                " EPersonl, emergencynum, birthlocation, immigration, immigrationstatus," +
                                " organization, style, wishpriority4, wishpriority5, wishpriority6," +
                                " wishpriority7, companyaddr2, sex, familystatus, offspring," +

                                " membershipid, companycountry, date_updated, jumin1, jumin2)" +

                                " values" +
                                "(" +
                                "'G', 'X', NULL, '" + username + "', 'Client', " +
                                "'" + uEmail + "', NULL, 0.00, NULL, '" + uCompany + "', " +
                                "NULL, NULL, NULL, NULL, '" + fName + "', " +
                                "'" + lName + "', NULL, 0, '" + uPhone + "', '" + uWPhone + "', " +
                                "NULL, NULL, NULL, NULL, NULL," +

                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, '" + date + "', " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "1, NULL, NULL, NULL, NULL, " +

                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +

                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +

                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +
                                "NULL, NULL, NULL, NULL, NULL, " +

                                "NULL, NULL, NULL, NULL, NULL);";

                        Statement stmt = con.createStatement();
                        z = "Imported successfully!";
                        stmt.executeUpdate(query);
                        isSuccess = true;
                    }
                }
                catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }
}


