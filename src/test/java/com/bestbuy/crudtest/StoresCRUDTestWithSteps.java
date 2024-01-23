package com.bestbuy.crudtest;

import com.bestbuy.productsandstoresinfo.StoresSteps;
import com.bestbuy.testbase.StoreTestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class StoresCRUDTestWithSteps  extends StoreTestBase
{
    static String name = "Maplewood";
    static String type = "BigBox";
    static String address = "1795 County Rd D E";
    static String address2 = "West";
    static String city = "Maplewood";
    static String state = "MN";
    static String zip = "55109";
    static double lat = 45.036556;
    static double lng = -93.025986;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;

    @Steps
    StoresSteps steps;

    @Title("This will create new stores")
    @Test
    public void test001(){

        HashMap<Object, Object> services = new HashMap<>();

        ValidatableResponse response =  steps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, services).statusCode(201);
        storeId = response.extract().jsonPath().getInt("id");
        System.out.println(storeId);
    }

    @Title("Verify the store added to the application")
    @Test
    public void test002(){

        String storeMap = steps.getStoreInfoByName(storeId);

        Assert.assertEquals(name, storeMap);

    }

    @Title("Update and verify the store information ")
    @Test
    public void test003(){
        name = name + "_updated";
        HashMap<Object, Object> services = new HashMap<>();
        steps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng, hours, services).statusCode(200);

        String storeMap = steps.getStoreInfoByName(storeId);
        Assert.assertEquals(name, storeMap);
    }

    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){
        steps.deleteStore(storeId).statusCode(200);
        steps.getStoreById(storeId).statusCode(404);
    }
}
