package com.haulmont.vaadin.sample;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 * @author artamonov
 * @version $Id$
 */
public class SampleVaadinUI extends UI {
    int n = 10;
    int c = 10;
    int r = 10;

    VerticalLayout layout;
    TextField text1;
    TextField text2;
    TextField text3;
    CheckBox checkBox;

    @Override
    protected void init(VaadinRequest request) {
        initMain();
    }

    protected void initMain() {
        layout = new VerticalLayout();
        layout.setStyleName("main");
        layout.setSpacing(true);
        layout.setSizeFull();
        setContent(layout);
        setSizeFull();

        HorizontalLayout hbox = new HorizontalLayout();
        hbox.setSpacing(true);
        layout.addComponent(hbox);

        text1 = new TextField();
        text1.setCaption("vbox count");
        text1.setRequired(true);
        text1.setValue(n + "");
        hbox.addComponent(text1);

        text2 = new TextField();
        text2.setCaption("rows count * 6");
        text2.setRequired(true);
        text2.setValue(r + "");
        hbox.addComponent(text2);

        text3 = new TextField();
        text3.setCaption("columns count * 3");
        text3.setRequired(true);
        text3.setValue(c + "");
        hbox.addComponent(text3);

        if (checkBox == null) {
            checkBox = new CheckBox("Use Grid");
        }

        hbox.addComponent(checkBox);

        Button btn = new Button("repaint");
        btn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                repaint();
            }
        });
        hbox.addComponent(btn);
    }

    protected void repaint() {
        try {
            n = Integer.parseInt(text1.getValue());
        } catch (NumberFormatException e) {
            n = 10;
        }

        try {
            r = Integer.parseInt(text2.getValue());
        } catch (NumberFormatException e) {
            r = 10;
        }

        try {
            c = Integer.parseInt(text3.getValue());
        } catch (NumberFormatException e) {
            c = 10;
        }

        initMain();

        VerticalLayout prevL = layout;
        for (int i = 0; i < n; i++) {
            VerticalLayout curL = new VerticalLayout();
            curL.setSpacing(true);
            curL.setSizeFull();
            prevL.addComponent(curL);
            prevL.setExpandRatio(curL, 1);
            prevL = curL;
        }

        if (!checkBox.getValue()) {
            Table table = createTable();
            table.setSizeFull();
            prevL.addComponent(table)   ;
            prevL.setExpandRatio(table, 1);
        } else {
            Grid grid = createGrid();
            grid.setSizeFull();
            prevL.addComponent(grid) ;
            prevL.setExpandRatio(grid, 1);
        }
    }

    protected Grid createGrid() {
        Grid grid = new Grid("This is my GRID in " + n + " VerticalBox");
        grid.setWidth("100%")                                            ;

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */

        for (int i = 0; i < c; i++) {
            grid.addColumn("First Name" + i, String.class);
            grid.addColumn("Last Name" + i, String.class);
            grid.addColumn("Year" + i, Integer.class);
        }

        for (int i = 0; i < r; i++) {
        /* Add a few items in the grid. */
            grid.addRow(makeRow(new Object[]{ "Nicolaus" + i, "Copernicus", 1473}, this.c));
            grid.addRow(makeRow(new Object[]{ "Tycho" + i, "Brahe", 1546}, this.c));
            grid.addRow(makeRow(new Object[]{ "Galileo" + i, "Galilei", 1548}, this.c));
            grid.addRow(makeRow(new Object[]{ "Johannes" + i, "Kepler", 1564}, this.c));
            grid.addRow(makeRow(new Object[]{ "Isaac" + i, "Newton", 1571}, this.c));
        }

        return grid;
    }

    protected Table createTable() {
        Table table = new Table("This is my Table in " + n + " VerticalBox");
        table.setWidth("100%")                                               ;

        /* Define the names and data types of columns.
         * The "default value" parameter is meaningless here. */

        for (int i = 0; i < c; i++) {
            table.addContainerProperty("First Name" + i, String.class, null);
            table.addContainerProperty("Last Name" + i, String.class, null);
            table.addContainerProperty("Year" + i, Integer.class, null);
        }

        int j = 0;
        for (int i = 0; i < r; i++) {
            /* Add a few items in the grid. */
            table.addItem(makeRow(new Object[]{ "Nicolaus" + i, "Copernicus", 1473}, this.c), j++);
            table.addItem(makeRow(new Object[]{ "Tycho" + i, "Brahe", 1546}, this.c), j++);
            table.addItem(makeRow(new Object[]{ "Galileo" + i, "Galilei", 1548}, this.c), j++);
            table.addItem(makeRow(new Object[]{ "Johannes" + i, "Kepler", 1564}, this.c), j++);
            table.addItem(makeRow(new Object[]{ "Isaac" + i, "Newton", 1571}, this.c), j++);
        }

        return table;
    }

    protected Object[] makeRow(Object[] data, int c) {
        Object[] row = new Object[c * data.length];
        for (int j = 0; j < c; j++) {
            int x = 0;
            for (Object value : data) {
                row[j * data.length + x] = value;
                x++;
            }
        }

        return row;
    }
}