package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setPremium extends QueryFactory {

    public setPremium(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<Integer>> getPremium(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT premiumAccount FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("premiumAccount"))
                .first();
    }
}
