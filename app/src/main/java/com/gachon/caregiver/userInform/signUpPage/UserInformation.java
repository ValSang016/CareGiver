package com.gachon.caregiver.userInform.signUpPage;

public class UserInformation {

            private String email;
            private String password;
            private String username;
            private String birth;
            private String gender;
            private String phoneNumber;
            private String userTP;

            public UserInformation(String email, String password, String username, String birth, String gender, String phoneNumber, String userTP) {
            this.email = email;
            this.password = password;
            this.username = username;
            this.birth = birth;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
            this.userTP = userTP;
        }

    public UserInformation() {
    }

    public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserTP() {
            return userTP;
        }

        public void setUserTP(String userTP) {
            this.userTP = userTP;
        }
    }
