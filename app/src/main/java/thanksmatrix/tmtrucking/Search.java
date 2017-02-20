package thanksmatrix.tmtrucking;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private ConnectionClass connectionClass;
    private ListView list;
    private ArrayList<String> names = new ArrayList<>();
    private String username = "";
    private String selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        list = (ListView) findViewById(R.id.lV);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        list.setLongClickable(true);


        connectionClass = new ConnectionClass();

        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        username = prefs.getString("UN", "UNKNOWN");

        Connection con = connectionClass.CONN();
        String query = "select Personf, Personl, company from dbo.customers where Merchant='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                names.add(rs.getString(1) + " " + rs.getString(2));
                if (rs.getString(1).equals("") || rs.getString(2).equals("")) {
                    names.add(rs.getString(3));
                }

            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedContact = names.get(i);
            }
        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select the action");
        menu.add(0, v.getId(), 0, "Update");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Update"){

            final String title = "Update";
            final String [] nameArray = selectedContact.split(" ");
            final String f = nameArray[0] + " " + nameArray[1];
            final String l = nameArray[2] + " " + nameArray[3];
            final EditText fName = new EditText(this);
            final EditText lName = new EditText(this);
            final EditText company = new EditText(this);
            final EditText phone = new EditText(this);
            final EditText wPhone = new EditText(this);
            final EditText email = new EditText(this);

            Connection con = connectionClass.CONN();
            String query = "select Personf, Personl, email, company, phone1, phone2 from dbo.customers where Merchant='" + username + "' and Personf = '" + f + "' and Personl = '" + l + "';";
            ResultSet rs;
            try {
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    fName.setHint(rs.getString(1));
                    lName.setHint(rs.getString(2));
                    email.setHint(rs.getString(3));
                    company.setHint(rs.getString(4));
                    phone.setHint(rs.getString(5));
                    wPhone.setHint(rs.getString(6));
                }
                con.close();
            } catch (Exception ex) {
                ex.getMessage();
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(title);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(fName);
            layout.addView(lName);
            layout.addView(company);
            layout.addView(phone);
            layout.addView(wPhone);
            layout.addView(email);
            alert.setView(layout);

            alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    String uFName = fName.getText().toString();
                    String uLName = lName.getText().toString();
                    String uComp = company.getText().toString();
                    String uPhone = phone.getText().toString();
                    String uWPhone = wPhone.getText().toString();
                    String uEmail = email.getText().toString();

                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            Toast.makeText(getBaseContext(), "Error in connection with SQL server!",  Toast.LENGTH_SHORT).show();
                        } else {
                            String query = "update dbo.customers set Personl = '" + uLName + "'," +
                                    " Personf = '" + uFName + "', company = '" + uComp + "', phone1 = '" + uPhone + "', phone2 = '" + uWPhone + "', " +
                                    " email = '" + uEmail + "' where Merchant = '" + username + "' and Personf = '" + f + "' and Personl = '" + l + "';";
                            Statement stmt = con.createStatement();
                            Toast.makeText(getBaseContext(), "Updated successfully!",  Toast.LENGTH_SHORT).show();
                            stmt.executeUpdate(query);

                        }
                    }
                    catch (Exception ex) {
                        Toast.makeText(getBaseContext(), "Error in SQL query!",  Toast.LENGTH_SHORT).show();

                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            alert.show();
       }
        else if(item.getTitle()=="Delete"){
            final String [] nameArray = selectedContact.split(" ");
            final String f = nameArray[0];
            final String l = nameArray[1];
            final String title = "Are you sure to delete?";
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle(title);

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    try {
                        Connection con = connectionClass.CONN();
                        if (con == null) {
                            Toast.makeText(getBaseContext(), "Error in connection with SQL server!",  Toast.LENGTH_SHORT).show();
                        } else {
                            String query = "delete from dbo.customers where Personl = '" + l + "' and Personf = '" + f + "' and Merchant = '" + username + "';";
                            Statement stmt = con.createStatement();
                            Toast.makeText(getBaseContext(), "Deleted",  Toast.LENGTH_SHORT).show();
                            stmt.executeUpdate(query);
                        }
                    }
                    catch (Exception ex) {
                        Toast.makeText(getBaseContext(), "Error in SQL query!",  Toast.LENGTH_SHORT).show();

                    }
                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            alert.show();
        }else{
            return false;
        }
        return true;
    }

}


