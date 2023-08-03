package net.sootmc.jauctionhouse.Handlers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler {
    private final boolean useExternalDatabase;
    private final String databaseHost;
    private final String databasePort;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private final File pluginFolder;
    private Connection connection;
    public DatabaseHandler(boolean useExternalDatabase, String databaseHost, String databasePort, String databaseName, String databaseUsername, String databasePassword, File pluginFolder) {
        this.useExternalDatabase = useExternalDatabase;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.pluginFolder = pluginFolder;
    }

    public void migrate() throws SQLException {
        if(this.useExternalDatabase) {
            connectExternal();
        } else {
            connectSqlite();
        }

        try {
            PreparedStatement items = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS items (id INTEGER PRIMARY KEY AUTO_INCREMENT, item TEXT, amount INTEGER, price INTEGER, seller TEXT, time INTEGER, expired INTEGER)");
            PreparedStatement logs = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS logs (id INTEGER PRIMARY KEY AUTO_INCREMENT, item TEXT, amount INTEGER, price INTEGER, seller TEXT, buyer TEXT, time INTEGER)");
            PreparedStatement blacklist = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS blacklist (id INTEGER PRIMARY KEY AUTO_INCREMENT, user TEXT, time INTEGER)");
            PreparedStatement expired = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS expired (id INTEGER PRIMARY KEY AUTO_INCREMENT, item TEXT, amount INTEGER, price INTEGER, seller TEXT, time INTEGER)");
            PreparedStatement claims = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS claims (id INTEGER PRIMARY KEY AUTO_INCREMENT, item TEXT, amount INTEGER, price INTEGER, seller TEXT, buyer TEXT, time INTEGER)");

            items.executeUpdate();
            logs.executeUpdate();
            blacklist.executeUpdate();
            expired.executeUpdate();
            claims.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectSqlite() {
        connection = null;
        try {
            String url = pluginFolder.getPath() + "/database.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);

            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectExternal() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format("jdbc:mariadb://%s:%s/%s?autoReconnect=true&useSSL=false", databaseHost, databasePort, databaseName));
            config.setUsername(databaseUsername);
            config.setPassword(databasePassword);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            HikariDataSource ds = new HikariDataSource(config);
            connection = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (useExternalDatabase) {
            try {
                if (connection.isClosed() || connection == null) {
                    connectExternal();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (connection.isClosed() || connection == null) {
                    connectSqlite();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
