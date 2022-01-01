package com.pmarek.memoryGame;

import com.pmarek.memoryGame.utils.DatabaseConnection;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;


public class TestDatabaseConnection {

    private DatabaseConnection databaseConnection;

    @BeforeClass
    public void init(){
        databaseConnection = new DatabaseConnection();
    }

    @After public void deleteDatabase(){
        File file = new File("results.db");
        file.delete();
    }

    @Test
    public void shouldReturnTrueWhenDatabaseIsCreated(){
        Assert.assertTrue(databaseConnection.isConnectionEstabilished());
    }

    @Test
    public void asdasd(){

    }
}
