package com.saucedemo.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData(){
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"locked_out_user", "secret_sauce", false},
                {"wrong_user", "wrong_pass", false},
                {"standard_user", "", false},
                {"", "secret_sauce", false}
        };
    }

    @DataProvider(name = "successfulLoginData")
    public static Object[][] getSuccessfulLoginData(){
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
        };
    }

    @DataProvider(name = "wrongPasswordLoginData")
    public static Object[][] getWrongPasswordLoginData(){
        return new Object[][]{
                {"standard_user", "wrong_password", true},
        };
    }
}
