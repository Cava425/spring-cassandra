package com.example.cassandra.utils;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.Statement;

import java.nio.ByteBuffer;

public class PageUtil {

    public static ResultSet skipTo(CqlSession session, Statement statement, int pageNum, int pageSize) {
        // Absolute index of the first row we want to display on the web page. Our goal is that
        // rs.next() returns that row.
        int targetRow = (pageNum - 1) * pageSize;

        ResultSet rs = session.execute(statement);
        // Absolute index of the next row returned by rs (if it is not exhausted)
        int currentRow = 0;
        int fetchedSize = rs.getAvailableWithoutFetching();
        ByteBuffer nextState = rs.getExecutionInfo().getPagingState();

        // Skip protocol pages until we reach the one that contains our target row.
        // For example, if the first query returned 60 rows and our target is row number 90, we know
        // we can skip those 60 rows directly without even iterating through them.
        // This part is optional, we could simply iterate through the rows with the for loop below,
        // but that's slightly less efficient because iterating each row involves a bit of internal
        // decoding.
        while (fetchedSize > 0 && nextState != null && currentRow + fetchedSize < targetRow) {
            statement = statement.setPagingState(nextState);
            rs = session.execute(statement);
            currentRow += fetchedSize;
            fetchedSize = rs.getAvailableWithoutFetching();
            nextState = rs.getExecutionInfo().getPagingState();
        }

        if (currentRow < targetRow) {
            for (@SuppressWarnings("unused") Row row : rs) {
                if (++currentRow == targetRow) {
                    break;
                }
            }
        }
        // If targetRow is past the end, rs will be exhausted.
        // This means you can request a page past the end in the web UI (e.g. request page 12 while
        // there are only 10 pages), and it will show up as empty.
        // One improvement would be to detect that and take a different action, for example redirect
        // to page 10 or show an error message, this is left as an exercise for the reader.
        return rs;
    }
}
