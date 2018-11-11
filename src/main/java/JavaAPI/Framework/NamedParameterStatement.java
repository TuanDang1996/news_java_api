package JavaAPI.Framework;

import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class NamedParameterStatement {
    private final PreparedStatement statement;
    private final Map indexMap = new HashMap();

    public NamedParameterStatement(Connection connection, String query, boolean returnGeneratedKeys) throws SQLException {
        String parsedQuery = parse(query, this.indexMap);
        if(returnGeneratedKeys) {
            this.statement = connection.prepareStatement(parsedQuery, 1);
        } else {
            this.statement = connection.prepareStatement(parsedQuery);
        }

    }

    private static final String parse(String query, Map paramMap) {
        int length = query.length();
        StringBuffer parsedQuery = new StringBuffer(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;

        for(int itr = 0; itr < length; ++itr) {
            char entry = query.charAt(itr);
            if(inSingleQuote) {
                if(entry == 39) {
                    inSingleQuote = false;
                }
            } else if(inDoubleQuote) {
                if(entry == 34) {
                    inDoubleQuote = false;
                }
            } else if(entry == 39) {
                inSingleQuote = true;
            } else if(entry == 34) {
                inDoubleQuote = true;
            } else if(entry == 58 && itr + 1 < length && Character.isJavaIdentifierStart(query.charAt(itr + 1))) {
                int list;
                for(list = itr + 2; list < length && Character.isJavaIdentifierPart(query.charAt(list)); ++list) {
                    ;
                }

                String indexes = query.substring(itr + 1, list);
                entry = 63;
                itr += indexes.length();
                Object i = (List)paramMap.get(indexes);
                if(i == null) {
                    i = new LinkedList();
                    paramMap.put(indexes, i);
                }

                ((List)i).add(new Integer(index));
                ++index;
            }

            parsedQuery.append(entry);
        }

        Iterator var14 = paramMap.entrySet().iterator();

        while(var14.hasNext()) {
            Map.Entry var15 = (Map.Entry)var14.next();
            List var16 = (List)var15.getValue();
            int[] var17 = new int[var16.size()];
            int var18 = 0;

            Integer x;
            for(Iterator itr2 = var16.iterator(); itr2.hasNext(); var17[var18++] = x.intValue()) {
                x = (Integer)itr2.next();
            }

            var15.setValue(var17);
        }

        return parsedQuery.toString();
    }

    private int[] getIndexes(String name) {
        int[] indexes = (int[])((int[])this.indexMap.get(name));
        if(indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        } else {
            return indexes;
        }
    }

    public void setObject(String name, Object value) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setObject(indexes[i], value);
        }

    }

    public void setNull(String name, int sqlType) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setNull(indexes[i], sqlType);
        }

    }

    public void setInputStream(String name, InputStream value) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setBinaryStream(indexes[i], value);
        }

    }

    public void setString(String name, String value) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setString(indexes[i], value);
        }

    }

    public void setInt(String name, int value) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setInt(indexes[i], value);
        }

    }

    public void setLong(String name, long value) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setLong(indexes[i], value);
        }

    }

    public void setTimestamp(String name, Timestamp value) throws SQLException {
        int[] indexes = this.getIndexes(name);

        for(int i = 0; i < indexes.length; ++i) {
            this.statement.setTimestamp(indexes[i], value);
        }

    }

    public void setDate(String name, Date value) throws SQLException {
        int[] indexes = this.getIndexes(name);
        int[] arr$ = indexes;
        int len$ = indexes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            int index = arr$[i$];
            this.statement.setDate(index, value);
        }

    }

    public void setTime(String name, Time value) throws SQLException {
        int[] indexes = this.getIndexes(name);
        int[] arr$ = indexes;
        int len$ = indexes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            int index = arr$[i$];
            this.statement.setTime(index, value);
        }

    }

    public PreparedStatement getStatement() {
        return this.statement;
    }

    public boolean execute() throws SQLException {
        return this.statement.execute();
    }

    public ResultSet executeQuery() throws SQLException {
        statement.setQueryTimeout(700000);
        return this.statement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return this.statement.executeUpdate();
    }

    public void close() throws SQLException {
        this.statement.close();
    }

    public void addBatch() throws SQLException {
        this.statement.addBatch();
    }

    public int[] executeBatch() throws SQLException {
        return this.statement.executeBatch();
    }
}
