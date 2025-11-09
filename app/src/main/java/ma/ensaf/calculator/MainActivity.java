package ma.ensaf.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import androidx.activity.OnBackPressedCallback;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket, buttonDevised;
    MaterialButton button7, button8, button9, buttonMultiply;
    MaterialButton button4, button5, button6, buttonMinus;
    MaterialButton button1, button2, button3, buttonPlus;
    MaterialButton buttonAc, button0, buttonDot, buttonEqual;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration du Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_standard) {
                Toast.makeText(this, "Vous êtes déjà sur Standard", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_scientifique) {
                Toast.makeText(this, "Mode Scientifique", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScientifiqueActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_historique) {
                Toast.makeText(this, "Historique", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HistoriqueActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_about) {
                Toast.makeText(this, "À propos", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Initialisation des TextViews et boutons
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        buttonC = findViewById(R.id.button_c);
        buttonC.setOnClickListener(this);

        buttonOpenBracket = findViewById(R.id.button_open_bracket);
        buttonOpenBracket.setOnClickListener(this);

        buttonCloseBracket = findViewById(R.id.button_close_bracket);
        buttonCloseBracket.setOnClickListener(this);

        buttonDevised = findViewById(R.id.button_devised);
        buttonDevised.setOnClickListener(this);

        button7 = findViewById(R.id.button_7);
        button7.setOnClickListener(this);

        button8 = findViewById(R.id.button_8);
        button8.setOnClickListener(this);

        button9 = findViewById(R.id.button_9);
        button9.setOnClickListener(this);

        buttonMultiply = findViewById(R.id.button_multiply);
        buttonMultiply.setOnClickListener(this);

        button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(this);

        button5 = findViewById(R.id.button_5);
        button5.setOnClickListener(this);

        button6 = findViewById(R.id.button_6);
        button6.setOnClickListener(this);

        buttonMinus = findViewById(R.id.button_minus);
        buttonMinus.setOnClickListener(this);

        button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(this);

        button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(this);

        button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(this);

        buttonPlus = findViewById(R.id.button_plus);
        buttonPlus.setOnClickListener(this);

        buttonAc = findViewById(R.id.button_ac);
        buttonAc.setOnClickListener(this);

        button0 = findViewById(R.id.button_0);
        button0.setOnClickListener(this);

        buttonDot = findViewById(R.id.button_dot);
        buttonDot.setOnClickListener(this);

        buttonEqual = findViewById(R.id.button_equal);
        buttonEqual.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            String result = resultTv.getText().toString();
            String calculation = solutionTv.getText().toString();

            // Sauvegarder dans l'historique
            if (!result.equals("0") && !result.equals("Err")) {
                saveToHistory(calculation, result);
            }

            solutionTv.setText(result);
            return;

        }
        if (buttonText.equals("C")) {
            if (dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }



        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }

    void saveToHistory(String calculation, String result) {
        SharedPreferences prefs = getSharedPreferences("CalculatorHistory", MODE_PRIVATE);


        String history = prefs.getString("history", "");


        String newEntry = calculation + " = " + result + "\n";
        history = newEntry + history; // Ajoute au début


        prefs.edit().putString("history", history).apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);}

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}