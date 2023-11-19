package de.marc.ganglife.dataSetter;

import com.zaxxer.hikari.HikariDataSource;
import de.chojo.sadu.databases.MySql;
import de.chojo.sadu.datasource.DataSourceCreator;

import java.sql.Connection;
import java.sql.SQLException;

public class mySQLConnection {
    private final String host, database, username, password;
    private HikariDataSource dataSource;
    private Connection connection;

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public mySQLConnection() {
        this.host = "localhost";
        this.database = "server";
        this.username = "root";
        this.password = "";
        connect();
    }

    private void connect() {
        this.dataSource = DataSourceCreator.create(MySql.get())
                .configure(config -> config.host(this.host)
                        .port(3306)
                        .user(this.username)
                        .password(this.password)
                        .database(this.database))
                .create()
                .build();

        if (isConnected()) {
            try {
                this.connection = this.dataSource.getConnection();
            } catch (SQLException e) {
            }
        }
    }

    public void disconnect() {
        this.dataSource.close();
    }

    public boolean isConnected() {
        return this.dataSource != null && this.dataSource.isRunning();
    }
}
