package com.gachon.caregiver.userInform.signUpPage;

public class isOkSignUP {
    boolean emailGreat;
    boolean pwGreat;

    public isOkSignUP() {
        this.emailGreat = false;
        this.pwGreat = false;
    }

    public void setEmailGreat(boolean emailGreat) {
        this.emailGreat = emailGreat;
    }

    public void setPwGreat(boolean pwGreat) {
        this.pwGreat = pwGreat;
    }

    public boolean okSignUp(){
        if(this.emailGreat&&this.pwGreat){
            return true;
        }
        return false;
    }
}
