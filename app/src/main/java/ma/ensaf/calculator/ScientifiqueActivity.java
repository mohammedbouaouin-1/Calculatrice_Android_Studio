package ma.ensaf.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class ScientifiqueActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket, buttonDevised;
    MaterialButton button7, button8, button9, buttonMultiply;
    MaterialButton button4, button5, button6, buttonMinus;
    MaterialButton button1, button2, button3, buttonPlus;
    MaterialButton buttonAc, button0, buttonDot, buttonEqual;

    MaterialButton buttonDegRad, buttonSin, buttonCos, buttonTan;
    MaterialButton buttonLn, buttonLog, buttonSqrt, buttonPower;
    MaterialButton buttonPi, buttonE;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private boolean isDegreeMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientifique);

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
                Toast.makeText(this, "Mode Standard", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_scientifique) {
                Toast.makeText(this, "Vous êtes déjà sur Scientifique", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_historique) {
                Toast.makeText(this, "Historique", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScientifiqueActivity.this, HistoriqueActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_about) {
                Toast.makeText(this, "À propos", Toast.LENGTH_SHORT).show();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

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

        buttonDegRad = findViewById(R.id.button_deg_rad);
        buttonDegRad.setOnClickListener(this);

        buttonSin = findViewById(R.id.button_sin);
        buttonSin.setOnClickListener(this);

        buttonCos = findViewById(R.id.button_cos);
        buttonCos.setOnClickListener(this);

        buttonTan = findViewById(R.id.button_tan);
        buttonTan.setOnClickListener(this);

        buttonLn = findViewById(R.id.button_ln);
        buttonLn.setOnClickListener(this);

        buttonLog = findViewById(R.id.button_log);
        buttonLog.setOnClickListener(this);

        buttonSqrt = findViewById(R.id.button_sqrt);
        buttonSqrt.setOnClickListener(this);

        buttonPower = findViewById(R.id.button_power);
        buttonPower.setOnClickListener(this);

        buttonPi = findViewById(R.id.button_pi);
        buttonPi.setOnClickListener(this);

        buttonE = findViewById(R.id.button_e);
        buttonE.setOnClickListener(this);




        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        int buttonId = v.getId();

        if (buttonId == R.id.button_deg_rad) {
            isDegreeMode = !isDegreeMode;
            buttonDegRad.setText(isDegreeMode ? "DEG" : "RAD");
            Toast.makeText(this, "Mode: " + (isDegreeMode ? "Degrés" : "Radians"), Toast.LENGTH_SHORT).show();

            if (!dataToCalculate.isEmpty()) {
                String finalResult = getResult(dataToCalculate);
                if (!finalResult.equals("Err")) {
                    resultTv.setText(finalResult);
                }
            }
            return;
        }

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            String result = resultTv.getText().toString();
            String calculation = solutionTv.getText().toString();

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
        } else if (buttonText.equals("sin")) {
            dataToCalculate = dataToCalculate + "sin(";
        } else if (buttonText.equals("cos")) {
            dataToCalculate = dataToCalculate + "cos(";
        } else if (buttonText.equals("tan")) {
            dataToCalculate = dataToCalculate + "tan(";
        } else if (buttonText.equals("ln")) {
            dataToCalculate = dataToCalculate + "ln(";
        } else if (buttonText.equals("log")) {
            dataToCalculate = dataToCalculate + "log(";
        } else if (buttonText.equals("√")) {
            dataToCalculate = dataToCalculate + "√(";
        } else if (buttonText.equals("x^y")) {
            dataToCalculate = dataToCalculate + "^";
        } else if (buttonText.equals("π")) {
            dataToCalculate = dataToCalculate + "π";
        } else if (buttonText.equals("e")) {
            dataToCalculate = dataToCalculate + "e";

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
        if (data.isEmpty()) return "0";

        Context context = null;
        try {
            context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();

            String processedData = preprocessExpression(data);

            String finalResult = context.evaluateString(
                    scriptable,
                    processedData,
                    "Javascript",
                    1,
                    null
            ).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }

            if (finalResult.equals("Infinity") || finalResult.equals("NaN")) {
                return "Err";
            }

            return finalResult;

        } catch (Exception e) {
            return "Err";
        } finally {
            if (context != null) {
                Context.exit();
            }
        }
    }

    private String preprocessExpression(String data) {
        String processed = data;

        processed = processed.replace("π", String.valueOf(Math.PI));
        processed = processed.replace("e", String.valueOf(Math.E));

        if (isDegreeMode) {
            processed = processed.replaceAll("sin\\(([^)]+)\\)", "Math.sin(($1)*Math.PI/180)");
            processed = processed.replaceAll("cos\\(([^)]+)\\)", "Math.cos(($1)*Math.PI/180)");
            processed = processed.replaceAll("tan\\(([^)]+)\\)", "Math.tan(($1)*Math.PI/180)");
        } else {
            processed = processed.replaceAll("sin\\(", "Math.sin(");
            processed = processed.replaceAll("cos\\(", "Math.cos(");
            processed = processed.replaceAll("tan\\(", "Math.tan(");
        }

        processed = processed.replaceAll("ln\\(", "Math.log(");
        processed = processed.replaceAll("log\\(", "Math.log10(");
        processed = processed.replaceAll("√\\(", "Math.sqrt(");
        processed = processed.replace("^", "**");


        return processed;
    }



    void saveToHistory(String calculation, String result) {
        SharedPreferences prefs = getSharedPreferences("CalculatorHistory", MODE_PRIVATE);
        String history = prefs.getString("history", "");
        String newEntry = calculation + " = " + result + "\n";
        history = newEntry + history;
        prefs.edit().putString("history", history).apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}