package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class setHousing extends QueryFactory {
    public setHousing(DataSource dataSource) {
        super(dataSource);
    }
    public CompletableFuture<Boolean> buyHouse(String UUID, Integer houseNumber, String Inventory, String Location) {
        return builder().query("INSERT INTO housing(ownerUUID, houseNumber, houseInventory, houseLocation)" +
                        "VALUES (?, ?, ?, ?)")
                .parameter(stmt -> stmt
                        .setString(UUID)
                        .setInt(houseNumber)
                        .setString(Inventory)
                        .setString(Location))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public CompletableFuture<Optional<String>> getHouseInventory(Integer houseNumber) {
        return builder(String.class)
                .query("SELECT houseInventory FROM housing WHERE houseNumber = ?")
                .parameter(stmt -> stmt.setInt(houseNumber))
                .readRow(row -> row.getString("houseInventory"))
                .first();
    }

    public CompletableFuture<Boolean> setHouseInventory(Integer houseNumber, String Inventory) {
        return builder().query("UPDATE housing SET houseInventory = ? WHERE houseNumber = ?")
                .parameter(stmt -> stmt
                        .setString(Inventory)
                        .setInt(houseNumber))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public CompletableFuture<Boolean> deleteHouse(String ownerUUID, Integer houseNumber) {
        return builder().query("DELETE FROM housing WHERE ownerUUID = ? AND houseNumber = ?")
                .parameter(stmt -> stmt
                        .setString(ownerUUID)
                        .setInt(houseNumber))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}

