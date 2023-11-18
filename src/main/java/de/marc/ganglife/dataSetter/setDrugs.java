package de.marc.ganglife.dataSetter;

import de.chojo.sadu.base.QueryFactory;
import de.chojo.sadu.wrapper.util.UpdateResult;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class setDrugs extends QueryFactory {

    public setDrugs(DataSource dataSource) {
        super(dataSource);
    }

    public CompletableFuture<Boolean> setCocain(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_cocain = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setWeed(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_weed = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setMeth(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_meth = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setMedizin(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_medizin = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setSchutzweste(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_schutzweste = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> setPoliceSchutzweste(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_policeschutzweste = ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addCocain(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_cocain = inv_cocain + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addWeed(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_weed = inv_weed + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addMeth(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_meth = inv_meth + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addMedizin(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_medizin = inv_medizin + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addSchutzweste(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_schutzweste = inv_schutzweste + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> addPoliceSchutzweste(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_policeschutzweste = inv_policeschutzweste + ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeCocain(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_cocain = inv_cocain - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeWeed(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_weed = inv_weed - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeMeth(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_meth = inv_meth - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeMedizin(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_medizin = inv_medizin - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removeSchutzweste(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_schutzweste = inv_schutzweste - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Boolean> removePoliceSchutzweste(UUID uniqueId, int amount) {
        return builder().query("UPDATE accounts SET inv_policeschutzweste = inv_policeschutzweste - ? WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setInt(amount)
                        .setString(uniqueId.toString()))
                .update()
                .send()
                .thenApply(UpdateResult::changed);
    }

    public CompletableFuture<Optional<Integer>> getCocain(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT inv_cocain FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("inv_cocain"))
                .first();
    }

    public CompletableFuture<Optional<Integer>> getWeed(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT inv_weed FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("inv_weed"))
                .first();
    }

    public CompletableFuture<Optional<Integer>> getMeth(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT inv_meth FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("inv_meth"))
                .first();
    }

    public CompletableFuture<Optional<Integer>> getMedizin(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT inv_medizin FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("inv_medizin"))
                .first();
    }

    public CompletableFuture<Optional<Integer>> getSchutzweste(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT inv_schutzweste FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("inv_schutzweste"))
                .first();
    }

    public CompletableFuture<Optional<Integer>> getPoliceSchutzweste(UUID uniqueId) {
        return builder(Integer.class)
                .query("SELECT inv_policeschutzweste FROM accounts WHERE uniqueid = ?")
                .parameter(stmt -> stmt.setString(uniqueId.toString()))
                .readRow(row -> row.getInt("inv_policeschutzweste"))
                .first();
    }

    public boolean hasCocain(UUID uniqueId, int amount) {
        getCocain(uniqueId)
                .thenApply(invCocain -> invCocain.isPresent() && invCocain.get() >= amount);
        return false;
    }

    public boolean hasWeed(UUID uniqueId, int amount) {
        getWeed(uniqueId)
                .thenApply(invWeed -> invWeed.isPresent() && invWeed.get() >= amount);
        return false;
    }

    public boolean hasMeth(UUID uniqueId, int amount) {
        getMeth(uniqueId)
                .thenApply(invMeth -> invMeth.isPresent() && invMeth.get() >= amount);
        return false;
    }

    public boolean hasMedizin(UUID uniqueId, int amount) {
        getMedizin(uniqueId)
                .thenApply(invMedizin -> invMedizin.isPresent() && invMedizin.get() >= amount);
        return false;
    }

    public boolean hasSchutzweste(UUID uniqueId, int amount) {
        getSchutzweste(uniqueId)
                .thenApply(invSchutzweste -> invSchutzweste.isPresent() && invSchutzweste.get() >= amount);
        return false;
    }

    public boolean hasPoliceSchutzweste(UUID uniqueId, int amount) {
        getPoliceSchutzweste(uniqueId)
                .thenApply(invPoliceSchutzweste -> invPoliceSchutzweste.isPresent() && invPoliceSchutzweste.get() >= amount);
        return false;
    }
}
