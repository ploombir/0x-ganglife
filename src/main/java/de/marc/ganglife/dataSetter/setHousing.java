package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
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
}

