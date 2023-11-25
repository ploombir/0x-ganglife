package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class setFrakLager extends QueryFactory {

    public setFrakLager(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Boolean> setFrakLagerFrak(String inventory, Integer frak) {
        return builder().query("UPDATE factions SET storageInventory = ? WHERE id = ?")
                .parameter(stmt -> stmt
                        .setString(inventory)
                        .setInt(frak))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getFrakLager(Integer frak) {
        return builder(String.class)
                .query("SELECT storageInventory FROM factions WHERE id = ?")
                .parameter(stmt -> stmt.setInt(frak))
                .readRow(row -> row.getString(frak))
                .first();
    }
}
