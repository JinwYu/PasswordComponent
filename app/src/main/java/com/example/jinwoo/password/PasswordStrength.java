package com.example.jinwoo.password;

/**
 * Calculates the strength of a password. The method "calculatePasswordStrength" calculates the
 * strength of the password by adding 1 to every strength condition that the password fulfills.
 *
 * @author Jinwoo Yu
 * @version 2016.05.19
 */
public class PasswordStrength {
    private int maxStrengthLevel;   // The maximum strength level a password can have.

    public PasswordStrength(){
        maxStrengthLevel = 5;
    }

    /**
     * Calculates the strength of the password and returns an int that represents the strength.
     * The algorithm has a maximum strength level of 5.
     * @param password The password that will be examined.
     * @return The strength level of the password from 0 (weakest) to 5 (strongest).
     */
    public int calculatePasswordStrength(String password){
        int passStrengthLevel = 0;

        // Has more than 8 characters.
        if(password.length() > 8) {passStrengthLevel++;}

        // Has more than 12 characters.
        if(password.length() > 12) {passStrengthLevel++;}

        // Contains both lowercase and uppercase letters.
        boolean hasUppercase = password.matches(".*[A-Z].*");   // ".*" is needed in front and back of the char sequence.
        boolean hasLowercase = password.matches(".*[a-z].*");
        if(hasUppercase && hasLowercase){passStrengthLevel++;}

        // Contains at least one numerical character.
        boolean hasDigits = password.matches(".*[0-9].*");
        if(hasDigits){passStrengthLevel++;}

        // Contains special characters.
        boolean hasSpecialChars = password.matches(".*[@\\$%&#_()=+*?»«<>£§€{}.\\-].*");
        if(hasSpecialChars) {passStrengthLevel++;}

        return passStrengthLevel;
    }

    /**
     * Get the max level a password can have.
     * @return An int that describes the max value a password can have with the current algorithm.
     */
    public int getMaxStrengthLevel(){return maxStrengthLevel;}

    //public void setMaxStrengthLevel(int maxLevel){maxStrengthLevel = maxLevel;}
}
