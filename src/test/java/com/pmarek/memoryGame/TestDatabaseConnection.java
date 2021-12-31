package com.pmarek.memoryGame;

import com.pmarek.memoryGame.utils.DatabaseConnection;
import org.junit.Assert;
import org.junit.Test;


public class TestDatabaseConnection {

    @Test
    public void shouldReturnTrueWhenDatabaseIsCreated(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Assert.assertNotNull(DatabaseConnection.getConn());
    }
}
