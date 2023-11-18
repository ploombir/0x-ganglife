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

    public CompletableFuture<Boolean> setFrakLagerFrak(String inventory, String frak) {
        return builder().query("UPDATE server_datas SET " + frak + " = ?")
                .parameter(stmt -> stmt.setString(inventory))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<String>> getFrakLager(String frak) {
        return builder(String.class)
                .query("SELECT " + frak + " FROM server_datas")
                .parameter(stmt -> stmt.setString(frak))
                .readRow(row -> row.getString(frak))
                .first();
    }
}
