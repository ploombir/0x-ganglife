package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class setFBank extends QueryFactory {
    public setFBank(DataSource dataSource) {
        super(dataSource);
    }
    public CompletableFuture<Optional<String>> getFBankSalary(String faction) {
        return builder(String.class)
                .query("SELECT salary FROM factions WHERE name = ?")
                .parameter(stmt -> stmt.setString(faction))
                .readRow(row -> row.getString("salary"))
                .first();
    }
}
