package thanksmatrix.tmcontact;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private ConnectionClass connectionClass;


    private ListView list;
    private ArrayList<String> names = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        list = (ListView) findViewById(R.id.lV);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        connectionClass = new ConnectionClass();

        SharedPreferences prefs = getSharedPreferences("MA", MODE_PRIVATE);
        String username = prefs.getString("UN", "UNKNOWN");

        Connection con = connectionClass.CONN();
        String query = "select Personf, Personl from dbo.customers where Merchant='" + username + "';";
        ResultSet rs;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                names.add(rs.getString(1) + " " + rs.getString(2));

            }
            con.close();
        } catch (Exception ex) {
            ex.getMessage();
        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Update");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Update"){
            Toast.makeText(getApplicationContext(),"updating",Toast.LENGTH_LONG).show();
        }
        else if(item.getTitle()=="Delete"){
            Toast.makeText(getApplicationContext(),"deleting",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }
}


