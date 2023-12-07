package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class setMobile extends QueryFactory {
    public setMobile(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Optional<String>> getPlayerFromNumber(String number) {
        return builder(String.class)
                .query("SELECT playerName FROM accounts WHERE phoneNumber = ?")
                .parameter(stmt -> stmt.setString(number))
                .readRow(row -> row.getString("playerName"))
                .first();
    }
}
