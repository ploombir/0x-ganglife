package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class getLastID extends QueryFactory {

    public getLastID(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getLastId() {
        return builder(Integer.class)
                .query("SELECT id FROM accounts ORDER BY id DESC LIMIT 1")
                .emptyParams()
                .readRow(row -> row.getInt("id"))
                .first();
    }
    public CompletableFuture<Boolean> setPhoneNumber(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET phoneNumber = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }
}
