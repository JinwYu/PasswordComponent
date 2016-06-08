package com.example.jinwoo.password;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * PasswordComponent is a class that makes a password component that shows a progress bar that
 * shows the password strength by growing and changing color of the progress bar. The way the
 * password strength is calculated can be changed by writing a new class called "PasswordStrength".
 * In that class the user must declare an int variable called "maxStrengthLevel" that represents
 * the max strength a password can have. This variable is used when calculating the percentage
 * that is used for updating the progress bar.
 *
 * @author Jinwoo Yu
 * @version 2016.06.08
 */
public class PasswordComponent extends LinearLayout {

    private EditText passField;
    private ProgressBar progressBar;
    private TextView passMessageField;
    private PasswordStrength passwordStrength;
    private Button registerButton;
    private PasswordComponentListener listener;
    private boolean savePassword = false;

    // Contructors.
    public PasswordComponent(Context context) {
        super(context);
        init(null, 0);
    }

    public PasswordComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PasswordComponent(Context context, AttributeSet attrs, int defStyle) {
        super(context);
        init(attrs, defStyle);
    }

    public interface PasswordComponentListener {
        // When the password can be saved
        public void onPasswordSaved(String password);
    }

    /**
     * Create the password component.
     * @param attributeSet
     * @param defStyle
     */
    private void init(AttributeSet attributeSet, int defStyle){

        this.listener = null;

        // Layoutinflater makes so everything in the xml-file "password_layout.xml" is available here.
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.password_layout, this);

        // Object of the replaceable class "PasswordStrength".
        passwordStrength = new PasswordStrength();

        // The different components from the file "password_layout.xml".
        passField = (EditText)findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        passMessageField = (TextView) findViewById(R.id.passMessage);
        registerButton = (Button) findViewById(R.id.button);

        // The progress bar is split into 4 parts.
        progressBar.setMax(4);

        // A listener for the password field.
        passField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final String passwordSeguence = s.toString();

                // If the password text field is empty, reset everything.
                if (s.toString().length() == 0) {
                    progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_ATOP);
                    progressBar.setProgress(0);
                    passMessageField.setText("Please, enter your password.");
                    passMessageField.setTextColor(Color.parseColor("#000000"));
                    savePassword = false;
                } else {

                    float passStrength = ((float) passwordStrength.calculatePasswordStrength(s.toString()));
                    float passPercentage = passStrength / passwordStrength.getMaxStrengthLevel();

                    // Update the progress bar depending on the password strength percentage.
                    if (passPercentage < 0.20f) {
                        progressBar.setProgress(0);
                        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#fdc68a"), android.graphics.PorterDuff.Mode.SRC_ATOP);
                        passMessageField.setText("Too short Password");
                        passMessageField.setTextColor(Color.parseColor("#fdc68a"));
                        savePassword = false;
                    } else if (0.20f <= passPercentage && passPercentage < 0.40f) {
                        progressBar.setProgress(1);
                        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#ebf209"), android.graphics.PorterDuff.Mode.SRC_ATOP);
                        passMessageField.setText("Weak Password");
                        passMessageField.setTextColor(Color.parseColor("#ebf209"));
                        savePassword = false;
                    } else if (0.40f <= passPercentage && passPercentage < 0.60f) {
                        progressBar.setProgress(2);
                        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#42b48e"), android.graphics.PorterDuff.Mode.SRC_ATOP);
                        passMessageField.setText("Okay Password");
                        passMessageField.setTextColor(Color.parseColor("#42b48e"));
                        savePassword = true;
                    } else if (0.60f <= passPercentage && passPercentage < 0.80f) {
                        progressBar.setProgress(3);
                        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#b3fe6a"), android.graphics.PorterDuff.Mode.SRC_ATOP);
                        passMessageField.setText("Strong Password");
                        passMessageField.setTextColor(Color.parseColor("#b3fe6a"));
                        savePassword = true;
                    } else if (0.80f <= passPercentage) {
                        progressBar.setProgress(4);
                        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#6ecff6"), android.graphics.PorterDuff.Mode.SRC_ATOP);
                        passMessageField.setText("Very Strong Password");
                        passMessageField.setTextColor(Color.parseColor("#6ecff6"));
                        savePassword = true;
                    }
                }

                // Listener for the register button.
                registerButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Fire the listener if the password is strong enough.
                        if (savePassword && listener != null) {
                            listener.onPasswordSaved(passwordSeguence);
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Assign the listener implementing events interface that will receive the events
    public void setPasswordComponentListener(PasswordComponentListener listener) {this.listener = listener;}
}
