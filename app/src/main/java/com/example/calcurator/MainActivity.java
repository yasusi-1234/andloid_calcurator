package com.example.calcurator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;

    Button buttonDp;

    Button buttonPlus;
    Button buttonMinus;
    Button buttonMultiply;
    Button buttonDivision;
    Button buttonSurplus;

    Button buttonEqual;

    Button leftParenthesis;
    Button rightParenthesis;

    Button buttonDelete;
    Button buttonCancel;

    Button buttonBefore;

    TextView beforeCalcText;

    TextView calcText;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 画面を縦方向で固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(realmConfig);

        button0 = (Button) findViewById(R.id.button_zero);
        button1 = (Button) findViewById(R.id.button_one);
        button2 = (Button) findViewById(R.id.button_two);
        button3 = (Button) findViewById(R.id.button_three);
        button4 = (Button) findViewById(R.id.button_four);
        button5 = (Button) findViewById(R.id.button_five);
        button6 = (Button) findViewById(R.id.button_six);
        button7 = (Button) findViewById(R.id.button_seven);
        button8 = (Button) findViewById(R.id.button_eight);
        button9 = (Button) findViewById(R.id.button_nine);

        buttonPlus = (Button) findViewById(R.id.button_plus);
        buttonMinus = (Button) findViewById(R.id.button_minus);
        buttonMultiply = (Button) findViewById(R.id.button_multiply);
        buttonDivision = (Button) findViewById(R.id.button_division);
        buttonSurplus = (Button) findViewById(R.id.button_surplus);
        buttonEqual = (Button) findViewById(R.id.button_equal);

        buttonDp = (Button) findViewById(R.id.button_dp);

        leftParenthesis = (Button) findViewById(R.id.left_parenthesis);
        rightParenthesis = (Button) findViewById(R.id.right_parenthesis);

        buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonCancel = (Button) findViewById(R.id.button_chancel);

        calcText = (TextView) findViewById(R.id.text_calc);

        buttonBefore = (Button) findViewById(R.id.past_calc_button);
        beforeCalcText = (TextView) findViewById(R.id.past_calc_text);

        // 過去の計算結果の一番最新の情報をbeforeCalcTextにセット
        Number id = realm.where(CalcModel.class).max("id");
        if(id != null){
            CalcModel calcModel= realm.where(CalcModel.class).equalTo("id",id.longValue())
                    .findFirst();
            beforeCalcText.setText(calcModel.getFormula() + calcModel.getAnswer());
        }else{
            beforeCalcText.setText("No Data");
        }

        CalcWriter calcWriter = new CalcWriter();
        // ボタン1
        button1.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "1");
            calcText.setText(result);
        });
        // ボタン2
        button2.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "2");
            calcText.setText(result);
        });
        // ボタン3
        button3.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "3");
            calcText.setText(result);
        });
        // ボタン4
        button4.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "4");
            calcText.setText(result);
        });
        // ボタン5
        button5.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "5");
            calcText.setText(result);
        });
        // ボタン6
        button6.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "6");
            calcText.setText(result);
        });
        // ボタン7
        button7.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "7");
            calcText.setText(result);
        });
        // ボタン8
        button8.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "8");
            calcText.setText(result);
        });
        // ボタン9
        button9.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "9");
            calcText.setText(result);
        });
        // ボタン0
        button0.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeNumber(textVal, "0");
            calcText.setText(result);
        });

        // ボタン(
        leftParenthesis.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeLeftParenthesis(textVal);
            calcText.setText(result);
        });
        // ボタン)
        rightParenthesis.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeRightParenthesis(textVal);
            calcText.setText(result);
        });

        // ボタン+
        buttonPlus.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeOperator(textVal, "+");
            calcText.setText(result);
        });
        // ボタン-
        buttonMinus.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeOperator(textVal, "-");
            calcText.setText(result);
        });
        // ボタン×
        buttonMultiply.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeOperator(textVal, "×");
            calcText.setText(result);
        });
        // ボタン÷
        buttonDivision.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeOperator(textVal, "÷");
            calcText.setText(result);
        });
        // ボタン%
        buttonSurplus.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeOperator(textVal, "%");
            calcText.setText(result);
        });

        // ボタンDEL
        buttonDelete.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.deleteTail(textVal);
            calcText.setText(result);
        });
        // ボタンC(キャンセル)
        buttonCancel.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            calcText.setText("");
        });

        // ボタン.(小数点)
        buttonDp.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            String result = calcWriter.writeDecimalPoint(textVal);
            calcText.setText(result);
        });

        // ボタン=
        buttonEqual.setOnClickListener(v -> {
            String textVal = calcText.getText().toString();
            // 計算できる状態かのフラグ
            boolean canCalculation = calcWriter.canCalculate(textVal);
            if (canCalculation){
                // 計算できる状態ならば
                String result = calcWriter.writeAns(textVal);
                calcText.setText(result);

                // realm処理
                Number count = realm.where(CalcModel.class).max("id");

                long newId = 0;
                if (count != null){
                    newId = count.longValue() + 1;
                }

                realm.beginTransaction();

                CalcModel calcModel = realm.createObject(CalcModel.class, newId);
                calcModel.setFormula(textVal + "=");
                calcModel.setAnswer(result);

                realm.commitTransaction();

                // 過去の計算の情報をBeforeCalcTextにセット
                beforeCalcText.setText(textVal + "=" + result);
            }



        });


        // ボタンBEF
        buttonBefore.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, PastCalculation.class);
            startActivity(intent);
        });

     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}