package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setPayDays extends QueryFactory {

    public setPayDays(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getPayDay(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT payday FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("payday"))
                .first();
    }

    public CompletableFuture<Boolean> addPayday(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET payday = payday + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setPayDay(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET payday = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .insert()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public boolean hasPayday(UUID uniqueId, int amount) {
        getPayDay(uniqueId)
                .thenApply(pd -> pd.isPresent() && pd.get() >= amount);
        return false;
    }

    public boolean hasPaydayGleich(UUID uniqueId, int amount) {
        getPayDay(uniqueId)
                .thenApply(pd -> pd.isPresent() && pd.get() == amount);
        return false;
    }
}
