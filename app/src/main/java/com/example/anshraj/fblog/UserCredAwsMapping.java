package com.example.anshraj.fblog;

/**
 * Created by ansh raj on 14-Dec-16.
 */

 import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

    @DynamoDBTable(tableName = "UserCredAws")
    public class UserCredAwsMapping {
        private String password;
        private String username;




        @DynamoDBAttribute(attributeName = "password")
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        
        @DynamoDBHashKey(attributeName = "username")
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

       
    }

