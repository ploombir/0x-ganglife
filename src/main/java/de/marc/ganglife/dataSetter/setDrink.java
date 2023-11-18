package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setDrink extends QueryFactory {

    public setDrink(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getDrink(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT drink FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("drink"))
                .first();
    }

    public CompletableFuture<Boolean> setDrink(UUID uniqueId, int amount) {
        return builder()
                .query("UPDATE accounts SET drink = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addDrink(UUID uniqueId, int amount) {
        return builder()
                .query("UPDATE accounts SET drink = drink + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeDrink(UUID uniqueId, int amount) {
        return builder()
                .query("UPDATE accounts SET drink = drink - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasDrink(UUID uniqueId, int amount) {
        getDrink(uniqueId)
                .thenApply(pd -> pd.isPresent() && pd.get() >= amount);
        return false;
    }

    public boolean isDrink(UUID uniqueId, int amount) {
        getDrink(uniqueId)
                .thenApply(pd -> pd.isPresent() && pd.get() <= amount);
        return false;
    }
}
