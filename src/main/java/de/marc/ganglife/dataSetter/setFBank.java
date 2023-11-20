package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setFBank extends QueryFactory {

    public setFBank(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getFBank(String faction) {
        return builder(Integer.class)
                .query("SELECT fbank FROM faction_datas WHERE faction = ?")
                .parameter(stmt -> stmt.setString(faction))
                .readRow(row -> row.getInt("fbank"))
                .first();
    }

    public CompletableFuture<Boolean> addFBank(String faction, int amount) {
        return builder().query("UPDATE faction_datas SET fbank = fbank + ? WHERE faction = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(faction))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeFBank(String faction, int amount) {
        return builder().query("UPDATE faction_datas SET fbank = fbank - ? WHERE faction = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(faction))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setFBank(String faction, int amount) {
        return builder().query("UPDATE faction_datas SET fbank = ? WHERE faction = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(faction))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
    public boolean hasEnoughFBank(String faction, int requiredAmount) {
        CompletableFuture<Optional<Integer>> moneyFuture = getFBank(faction);

        Optional<Integer> money = moneyFuture.join();

        return money.filter(actualMoney -> actualMoney >= requiredAmount).isPresent();
    }
}
