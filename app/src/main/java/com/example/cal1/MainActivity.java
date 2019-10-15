package com.example.cal1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("dev2qa.com - Android Calculator Example.");

        // Get the edit text input box.
        resultEditText = (EditText)findViewById(R.id.result);

        // Get the root table layout object.
        TableLayout rootView = (TableLayout) findViewById(R.id.root_view);

        // Disable the soft keyboard of edittext input view component.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get table layout row count.
        int rowCount = rootView.getChildCount();

        // Loop the table row list.
        for(int i=0;i<rowCount;i++)
        {
            // Get each table row.
            View childView = rootView.getChildAt(i);

            if(childView instanceof TableRow) {
                TableRow tableRow = (TableRow)childView;

                // Get table row child view count.
                int childCount = tableRow.getChildCount();

                for(int j=0;j<childCount;j++) {

                    childView = tableRow.getChildAt(j);

                    // If the table row's child is a button then set the button to use this activity as onclick listener.
                    if (childView instanceof Button) {
                        childView.setOnClickListener(this);
                    }
                }
            }
        }
    }

    /* This method is invoked when activity screen is clicked. */
    @Override
    public void onClick(View view) {

        // If the clicked view object is a button.
        if(view instanceof Button)
        {
            // Get the button text.
            Button button = (Button)view;
            String buttonValue = button.getText().toString();

            // Check whether the button's text is a number or not.
            int buttonNumber = isInteger(buttonValue);

            if(buttonNumber != -1)
            {
                // Get current edit text box value.
                String currentValue = resultEditText.getText().toString();

                if(isInteger(currentValue) > -1)
                {
                    // If current value is a number then append new number to the end.
                    currentValue += buttonValue;
                    resultEditText.setText(currentValue);
                }else
                {
                    // Append the button number to edit text box.
                    resultEditText.setText(buttonValue);
                }

            }else
            {
                // If the button is operator button.
                if("+".equals(buttonValue) || "-".equals(buttonValue) || "*".equals(buttonValue) || "/".equals(buttonValue))
                {
                    // Get current number.
                    String currentValue = resultEditText.getText().toString();
                    if(isInteger(currentValue) > -1)
                    {
                        // Save it's value in firstNumber if the value is a number.
                        firstNumber = Double.parseDouble(currentValue);
                    }else
                    {
                        // Reset firstNumber value.
                        firstNumber = Double.MIN_VALUE;
                    }

                    // Save the operator.
                    operator = buttonValue;
                    // Show operator in edit text box.
                    resultEditText.setText(buttonValue);
                }else if("c".equals(buttonValue))
                {
                    // If user click c button then reset all value.
                    firstNumber = Double.MIN_VALUE;
                    secondNumber = Double.MIN_VALUE;
                    // Display empty string in edit text box.
                    resultEditText.setText("");
                }else if("=".equals(buttonValue))
                {
                    // Get current number.
                    String currentValue = resultEditText.getText().toString();
                    if(isInteger(currentValue) > -1)
                    {
                        // Save it's value in secondNumber if the value is a number.
                        secondNumber = Double.parseDouble(currentValue);
                    }else
                    {
                        // Reset firstNumber value.
                        secondNumber = Double.MIN_VALUE;
                    }

                    // If the button is equal button.
                    if(firstNumber > Long.MIN_VALUE && secondNumber > Long.MIN_VALUE) {

                        double resultNumber = 0;

                        // Calculate the result number.
                        if ("+".equals(operator)) {
                            resultNumber = firstNumber + secondNumber;
                        }else if("-".equals(operator))
                        {
                            resultNumber = firstNumber - secondNumber;
                        }else if("*".equals(operator))
                        {
                            resultNumber = firstNumber * secondNumber;
                        }else if("/".equals(operator))
                        {
                            resultNumber = firstNumber / secondNumber;
                        }

                        // Show the result number.
                        resultEditText.setText(String.valueOf(resultNumber));
                        // Save the result number as second result number.
                        secondNumber = resultNumber;
                        // Reset first number.
                        firstNumber = Double.MIN_VALUE;
                    }
                }
            }

        }
    }

    /* Check whether a string value is an integer or not. */
    private int isInteger(String value)
    {
        int ret = -1;
        try {
            ret = Integer.parseInt(value);
        }catch(NumberFormatException ex)
        {
            Log.e(TAG_CALCULATOR, ex.getMessage());
        }finally {
            return ret;
        }
    }
}
