package com.gachon.caregiver.userInform.signUpPage;

public class UserInformation {

        private String username;
        private String birth;
        private String gender;
        private String phoneNumber;

    public UserInformation(String username, String birth, String gender, String phoneNumber) {
        this.username = username;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getBirth() {
        return birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }


        public String getPhoneNumber() {
            return phoneNumber;
        }
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

}
