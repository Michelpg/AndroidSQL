package bts2.snir.sqlapli;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.AlertDialog.Builder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener  {

    public static Connection conn = null;
    private EditText pseudo, motdepas, confirmermdp, commentaire;
    private RadioGroup niveaux;
    private  RadioButton debutant, intermediaire, pro;
    private CheckBox lundi, mardi, mercredi, jeudi, vendredi;
    private Button envoyer;
    private Spinner association;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        MysqlConnexion();


        pseudo = (EditText) findViewById(R.id.editTextTextPersonName);
        motdepas = (EditText) findViewById(R.id.editTextTextPassword);
        confirmermdp = (EditText) findViewById(R.id.editTextTextPassword2);
        commentaire = (EditText) findViewById(R.id.editTextTextPersonName3);

        niveaux = (RadioGroup) findViewById(R.id.radioGroup);

        debutant = (RadioButton) findViewById(R.id.radioButton7);
        intermediaire = (RadioButton) findViewById(R.id.radioButton11);
        pro = (RadioButton) findViewById(R.id.radioButton12);


        lundi = (CheckBox) findViewById(R.id.checkBox3);
        mardi = (CheckBox) findViewById(R.id.checkBox5);
        mercredi = (CheckBox) findViewById(R.id.checkBox6);
        jeudi = (CheckBox) findViewById(R.id.checkBox4);
        vendredi = (CheckBox) findViewById(R.id.checkBox7);

        envoyer = (Button) findViewById(R.id.button);
        envoyer.setOnClickListener(this);

        association = (Spinner) findViewById(R.id.spinner);

        RequeteAssociation();
      


    }

    public void RequeteAssociation() {
        try {
            String req = "SELECT spinner FROM spinner";
            Statement pstm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rst1 = pstm.executeQuery(req);
            List<String> list = new ArrayList<>();;
            while (rst1.next()) {
                list.add(rst1.getString(1));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            association.setAdapter(adapter);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void MysqlConnexion() {
        String jdbcURL = "jdbc:mysql://10.4.253.124:3306/android";
        String user = "username";
        String passwd = "password";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, user, passwd);
            Toast.makeText(MainActivity.this, "Connexion reussi.", Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            Toast.makeText(MainActivity.this, "Driver manquant." + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        } catch (java.sql.SQLException ex) {
            Toast.makeText(MainActivity.this, "Connexion au serveur impossible." + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.d("error", "SQLException: " + ex.getMessage());
            Log.d("error", "SQLState: " + ex.getSQLState());
            Log.d("error", "VendorError: " + ex.getErrorCode());
        }

    }

    @Override
    public void onClick(View view) {
        if (view == envoyer)
        {

            if (motdepas.getText().toString().equals(confirmermdp.getText().toString()))

                {
                    try {
                        String sqlins = "insert into information (pseudo,motdepas,confirmermdp,association,semaine,niveau,commentaire ) values (?,?,?,?,?,?,?)";
                        PreparedStatement pstmins = conn.prepareStatement(sqlins);
                        pstmins.setString(1, pseudo.getText().toString());
                        pstmins.setString(2, motdepas.getText().toString());
                        pstmins.setString(3, confirmermdp.getText().toString());

                        pstmins.setString(4, association.getSelectedItem().toString());


               /* if (lundi.isChecked() ){
                    pstmins.setString(5,lundi.getText().toString());
                }
                if (mardi.isChecked()){
                    pstmins.setString(5,mardi.getText().toString());
                }
                if (mercredi.isChecked()){
                    pstmins.setString(5,mercredi.getText().toString());
                }
                if (jeudi.isChecked()){
                    pstmins.setString(5,jeudi.getText().toString());
                }
                if (vendredi.isChecked()){
                    pstmins.setString(5,vendredi.getText().toString());
                }
                */

                        String lun = null;
                        String mar = null;
                        String mer = null;
                        String jeu = null;
                        String ven = null;
                        if (lundi.isChecked()){
                            lun = lundi.getText().toString();
                        }
                        if (mardi.isChecked()){
                            mar = mardi.getText().toString();
                        }
                        if (mercredi.isChecked()){
                            mer = mercredi.getText().toString();
                        }
                        if (jeudi.isChecked()){
                            jeu = jeudi.getText().toString();
                        }
                        if (vendredi.isChecked()){
                            ven = vendredi.toString();
                        }

                        String semaine = lun + " " + mar + " " + mer + " " + jeu + " " + ven;

                        pstmins.setString(5,semaine);

                        if (debutant.isChecked() ){
                            pstmins.setString(6,debutant.getText().toString());
                        }
                        if (intermediaire.isChecked()){
                            pstmins.setString(6,intermediaire.getText().toString());
                        }
                        if (pro.isChecked()){
                            pstmins.setString(6,pro.getText().toString());
                        }



                        pstmins.setString(7, commentaire.getText().toString());

                        pstmins.executeUpdate();


                        videTexte();

                    } catch (SQLException seinst) {
                        Toast.makeText(MainActivity.this, "liste." + seinst.toString(), Toast.LENGTH_LONG).show();
                        Log.d("MainActivity", seinst.getMessage());
                    }
                }
            else
            {
                motdepas.setText("");
                confirmermdp.setText("");

                Toast.makeText(MainActivity.this, "Mot de passe differente ." , Toast.LENGTH_LONG).show();
            }
            }


    }

    public void videTexte() {
        pseudo.setText("");
        motdepas.setText("");
        confirmermdp.setText("");
        commentaire.setText("");
        debutant.setChecked(false);
        intermediaire.setChecked(false);
        pro.setChecked(false);

        lundi.setChecked(false);
        mardi.setChecked(false);
        mercredi.setChecked(false);
        jeudi.setChecked(false);
        vendredi.setChecked(false);
    }


}
