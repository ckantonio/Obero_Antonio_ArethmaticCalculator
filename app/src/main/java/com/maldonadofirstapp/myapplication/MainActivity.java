package com.maldonadofirstapp.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayTextView;
    private StringBuilder inputStringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayTextView = findViewById(R.id.textview_display);

        inputStringBuilder = new StringBuilder();

        // Set click listeners for all buttons
        Button button0 = findViewById(R.id.button_0);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonSubtract = findViewById(R.id.button_subtract);
        Button buttonMultiply = findViewById(R.id.button_multiply);
        Button buttonDivide = findViewById(R.id.button_divide);
        Button buttonDecimal = findViewById(R.id.button_decimal);
        Button buttonEqual = findViewById(R.id.button_equal);
        Button buttonOpenParenthesis = findViewById(R.id.button_open_parenthesis);
        Button buttonCloseParenthesis = findViewById(R.id.button_close_parenthesis);
        Button buttonPercentage = findViewById(R.id.button_percentage);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonDecimal.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonOpenParenthesis.setOnClickListener(this);
        buttonCloseParenthesis.setOnClickListener(this);
        buttonPercentage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_decimal:
                // Append the corresponding number or decimal point to the input string
                Button button = (Button) view;
                inputStringBuilder.append(button.getText().toString());
                displayTextView.setText(inputStringBuilder.toString());
                break;
            case R.id.button_add:
            case R.id.button_subtract:
            case R.id.button_multiply:
            case R.id.button_divide:
            case R.id.button_open_parenthesis:
            case R.id.button_close_parenthesis:
            case R.id.button_percentage:
                // Append the corresponding operator or symbol to the input string
                Button operatorButton = (Button) view;
                inputStringBuilder.append(" ").append(operatorButton.getText().toString()).append(" ");
                displayTextView.setText(inputStringBuilder.toString());
                break;
            case R.id.button_equal:
                // Perform the calculation and display the result
                String input = inputStringBuilder.toString();
                double result = calculateResult(input);
                displayTextView.setText(formatResult(result));

                // Clear the input string
                inputStringBuilder.setLength(0);
                break;
        }
    }

    private double calculateResult(String input) {
        // Handle parentheses first
        while (input.contains("(")) {
            int openParenthesisIndex = input.lastIndexOf("(");
            int closeParenthesisIndex = input.indexOf(")", openParenthesisIndex);
            String subExpression = input.substring(openParenthesisIndex + 1, closeParenthesisIndex);
            double subResult = calculateResult(subExpression);
            input = input.replace("(" + subExpression + ")", String.valueOf(subResult));
        }

        // Handle percentages
        while (input.contains("%")) {
            int percentageIndex = input.indexOf("%");
            int startIndex = percentageIndex;
            while (startIndex > 0 && Character.isDigit(input.charAt(startIndex - 1))) {
                startIndex--;
            }
            String numberStr = input.substring(startIndex, percentageIndex);
            double number = Double.parseDouble(numberStr);
            double percentage = number / 100.0;
            input = input.replace(numberStr + "%", String.valueOf(percentage));
        }

        // Evaluate the expression
        String[] tokens = input.split(" ");
        double result = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length - 1; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    result /= operand;
                    break;
            }
        }
        return result;
    }

    private String formatResult(double result) {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        return decimalFormat.format(result);
    }
}
