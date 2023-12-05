package org.rasul.labmaker.table;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ResultBean implements Serializable {

    private List<TableRow> rows;

    public ResultBean() {
        this.rows = new ArrayList<>();
    }

    public List<TableRow> getRows() {
        return rows;
    }

    public void setRows(List<TableRow> rows) {
        this.rows = rows;
    }
    public void addRow(TableRow row) {
        this.rows.add(row);
    }
}
