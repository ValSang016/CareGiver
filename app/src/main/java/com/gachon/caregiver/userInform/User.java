package com.gachon.caregiver.userInform;

public class User {
        private String name;
        private String email;
        private String phone;
        private String password;

        public User() {
            // 기본 생성자는 Firebase Realtime Database에서 데이터를 읽고 쓸 때 필요합니다.
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public User(String name, String email, String phone, String password) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

}
