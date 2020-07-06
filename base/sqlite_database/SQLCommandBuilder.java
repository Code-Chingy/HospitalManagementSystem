package com.techupstudio.school_management_system.base.sqlite_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLCommandBuilder {

    private Connection connection;

    public SQLCommandBuilder(Connection connection) {
        this.connection = connection;
    }

    private void log(String tag, String message) {
        Logger.getLogger("Hospital Database")
                .log(Level.INFO, (tag + ": " + message));
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Query withTable(String table) {
        return new Query(table);
    }

    public class Query {

        String TABLE;

        public Query(String table) {
            this.TABLE = table.trim();
        }

        public CreateTableQuery create(String... fieldAndAttributes) {
            return new CreateTableQuery(fieldAndAttributes);
        }

        public SelectQuery select(String... selections) {
            return new SelectQuery(selections);
        }

        public InsertQuery insert(ContentValues values) {
            return new InsertQuery(values);
        }

        public UpdateQuery update(ContentValues values) {
            return new UpdateQuery(values);
        }

        public DeleteQuery delete() {
            return new DeleteQuery();
        }

        public DropTableQuery drop() {
            return new DropTableQuery();
        }


        //procedure(args, method).setarg1 | setarg2

        public class CreateTableQuery {

            private String QUERY;

            public CreateTableQuery(String... columnsAndAttributes) {
                if (columnsAndAttributes.length > 0) {
                    String fieldAndAttributes = "";

                    for (String columnEntry : columnsAndAttributes) {
                        fieldAndAttributes += columnEntry.trim() + ", ";
                    }

                    fieldAndAttributes = fieldAndAttributes.substring(0, fieldAndAttributes.lastIndexOf(", "));

                    QUERY = "CREATE TABLE " + TABLE + " (" + fieldAndAttributes + ")";
                }
            }

            public Commit commit() throws SQLException {
                return new Commit(QUERY);
            }
        }

        public class SelectQuery {

            private String QUERY;

            public SelectQuery(String... selections) {
                if (selections.length == 0) {
                    QUERY = "SELECT * FROM " + TABLE;
                } else {
                    QUERY = "SELECT";

                    for (int i = 0; i < selections.length; i++) {
                        String selection = selections[i].trim();

                        QUERY += " " + selection;

                        if (i < selections.length - 1) {
                            QUERY += ",";
                        }
                    }

                    QUERY += " FROM " + TABLE;
                }
            }

            public Where where(String condition) {
                return new Where(condition);
            }

            public GroupBy groupBy(String... columns) {
                return new GroupBy(QUERY, columns);
            }

            public OrderBy orderBy(String column) {
                return new OrderBy(QUERY, column);
            }

            public Result getResult() {
                return new Result(QUERY);
            }

            public class Where {

                private String QUERY_1;

                public Where(String condition) {
                    QUERY_1 = QUERY + " WHERE " + condition;
                }

                public GroupBy groupBy(String... columns) {
                    return new GroupBy(QUERY_1, columns);
                }

                public OrderBy orderBy(String column) {
                    return new OrderBy(QUERY_1, column);
                }

                public Result getResult() {
                    return new Result(QUERY_1);
                }

            }

        }

        public class InsertQuery {

            private String QUERY;

            public InsertQuery(ContentValues contentValues) {

                if (contentValues != null && !contentValues.isEmpty()) {

                    String COLUMNS = "";
                    String VALUES = "";

                    for (String key : contentValues.keySet()) {
                        COLUMNS += key + ", ";
                        VALUES += contentValues.get(key) + ", ";
                    }

                    COLUMNS = COLUMNS.substring(0, COLUMNS.lastIndexOf(", "));
                    VALUES = VALUES.substring(0, VALUES.lastIndexOf(", "));

                    QUERY = "INSERT INTO " + TABLE + " (" + COLUMNS + ") VALUES (" + VALUES + ")";

                }
            }

            public Commit commit() throws SQLException {
                return new Commit(QUERY);
            }

        }

        public class UpdateQuery {

            private String QUERY;

            public UpdateQuery(ContentValues contentValues) {
                String updateFields = "";
                for (String key : contentValues.keySet()) {
                    updateFields += " " + key + " = " + contentValues.get(key) + ",";
                }
                updateFields = updateFields.substring(0, updateFields.lastIndexOf(","));
                QUERY = "UPDATE " + TABLE + " SET" + updateFields;
            }

            public Where where(String condition) {
                return new Where(condition);
            }

            public Commit commit() throws SQLException {
                return new Commit(QUERY);
            }

            public class Where {
                private String QUERY_1;

                public Where(String condition) {
                    QUERY_1 = QUERY + " WHERE " + condition;
                }

                public Commit commit() throws SQLException {
                    return new Commit(QUERY_1);
                }

            }

        }

        public class DeleteQuery {

            private String QUERY;

            public DeleteQuery() {
                QUERY = "DELETE * FROM " + TABLE;
            }

            public Where where(String condition) {
                return new Where(condition);
            }

            public Commit commit() throws SQLException {
                return new Commit(QUERY);
            }

            public class Where {

                private String QUERY_1;

                public Where(String condition) {
                    QUERY_1 = QUERY + " WHERE " + condition;
                }

                public Commit commit() throws SQLException {
                    return new Commit(QUERY_1);
                }

            }

        }

        public class DropTableQuery {

            private String QUERY;

            public DropTableQuery() {
                QUERY = "DROP TABLE " + TABLE;
            }

            public Commit commit() throws SQLException {
                return new Commit(QUERY);
            }
        }

        public class GroupBy {

            private String QUERY_1;

            public GroupBy(String query, String[] columns) {
                String groupColumns = "";
                for (String key : columns) {
                    groupColumns += " " + key + ",";
                }
                groupColumns = groupColumns.substring(0, groupColumns.lastIndexOf(","));
                QUERY_1 = query + " GROUP BY" + groupColumns;
            }

            public OrderBy orderBy(String column) {
                return new OrderBy(QUERY_1, column);
            }

            public GroupHaving having(String condition) {
                return new GroupHaving(QUERY_1, condition);
            }

            public Result getResult() {
                return new Result(QUERY_1);
            }

        }

        public class GroupHaving {

            private String QUERY_1;

            public GroupHaving(String query, String condition) {
                QUERY_1 = query + " HAVING " + condition;
            }

            public OrderBy orderBy(String column) {
                return new OrderBy(QUERY_1, column);
            }

            public Result getResult() {
                return new Result(QUERY_1);
            }
        }

        public class OrderBy {

            private String QUERY_1;

            public OrderBy(String query, String column) {
                QUERY_1 = query + " ORDER BY " + column;
            }

            public Result ascending() {
                QUERY_1 += " ASC";
                return new Result(QUERY_1);
            }

            public Result descending() {
                QUERY_1 += " DESC";
                return new Result(QUERY_1);
            }

            public Result getResult() {
                return new Result(QUERY_1);
            }
        }

        public class Result {

            private String QUERY_1;

            Result(String query) {
                QUERY_1 = query;
            }

            public All all() {
                return new All(QUERY_1);
            }

            public Limiter limit(long number) {
                return new Limiter(QUERY_1, number);
            }

            public PaginaterBuilder paginate(long limit) {
                return new PaginaterBuilder(QUERY_1, limit);
            }

            public class Count {

                List<Integer> COUNT_LIST;

                public Count(String query) throws SQLException {

                    COUNT_LIST = new ArrayList<>();

                    String[] ARR = query.split("FROM");
                    String COUNT_QUERY = "SELECT COUNT(*) as count FROM" + ARR[1];
                    Statement statement = getStatement();
                    ResultSet resultSet = null;
                    System.out.println("Running Query: " + COUNT_QUERY);
                    resultSet = statement.executeQuery(COUNT_QUERY);
                    while (resultSet.next()) {
                        COUNT_LIST.add(resultSet.getInt("count"));
                    }
                }

                public List get() {
                    return COUNT_LIST;
                }

            }

            public class All {

                private String FINAL_QUERY;

                All(String query) {
                    FINAL_QUERY = query + ";";
                }

                public List count() throws SQLException {
                    return new Count(FINAL_QUERY).get();
                }

                public ResultSet get() throws SQLException {
                    log("Running Query", FINAL_QUERY);
                    return getStatement().executeQuery(FINAL_QUERY);
                }
            }

            public class Limiter {

                private String FINAL_QUERY;

                public Limiter(String query, long limit) {
                    FINAL_QUERY = query + " LIMIT " + ((int) limit) + ";";
                    //TODO : run query and save in resultData
                }

                public Count count() throws SQLException {
                    return new Count(FINAL_QUERY);
                }

                public ResultSet get() throws SQLException {
                    log("Running Query", FINAL_QUERY);
                    return getStatement().executeQuery(FINAL_QUERY);
                }
            }

            public class PaginaterBuilder {

                private String FINAL_QUERY;
                private ResultSet RESULT_SET;
                private long PAGINATION_LIMIT;


                public PaginaterBuilder(String query, long limit) {
                    PAGINATION_LIMIT = limit;
                }

                public Paginater get() throws SQLException {
                    log("Running Query", FINAL_QUERY);
                    RESULT_SET = getStatement().executeQuery(FINAL_QUERY);
                    return new Paginater(RESULT_SET, PAGINATION_LIMIT);
                }
            }

            public class Paginater {

                private ResultSet RESULT_SET;
                private long PAGINATION_LIMIT;

                public Paginater(ResultSet resultSet, long limit) {
                    RESULT_SET = resultSet;
                    PAGINATION_LIMIT = limit;
                }

                public boolean hasNextBatch() {
                    return false;
                }

                public boolean hasPreviousBatch() {
                    return false;
                }

                public ResultSet getNextBatch() {
                    return null;
                }

                public ResultSet getPreviousBatch() {
                    return null;
                }

            }

            //TODO: FUTURE
            private class ResultItem {
                //TODO  : build result item
                //use json // map<String, Object>
            }

            //TODO: FUTURE
            private class ResultItems extends ArrayList<ResultItem> {
                //TODO : build result items data
            }


        }

        public class Commit {

            private String FINAL_QUERY;
            private boolean operationStatus;
            private long updateCount;

            public Commit(String query) throws SQLException {

                if (query != null && !query.isEmpty()) {
                    query = query.trim();
                    FINAL_QUERY = query + ";";
                    String COMMAND = query.substring(0, query.indexOf(" "));
                    Statement statement = getStatement();
                    switch (COMMAND) {
                        case "CREATE":
                        case "DROP":
                            log("Running Query", FINAL_QUERY);
                            operationStatus = statement.execute(FINAL_QUERY);
                            updateCount = 1;
                            break;
                        case "UPDATE":
                        case "DELETE":
                        case "INSERT":
                            log("Running Query", FINAL_QUERY);
                            updateCount = statement.executeUpdate(FINAL_QUERY);
                            operationStatus = updateCount > -1;
                            break;
                    }
                    statement.close();
                }
            }

            public long getUpdateCount() {
                return updateCount;
            }

            public boolean isSuccessful() {
                return operationStatus;
            }
        }

    }

}
