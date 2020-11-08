package com.fullstackjava.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;

public class Demo implements EntryPoint {
    private final VerticalPanel VERTICAL_PANEL_OF = new VerticalPanel();
    private final Button BUTTON = new Button("Enter");
    private final TextBox TEXT_BOX = new TextBox();

    private int[] currentArrOfNumber;

    private HorizontalPanel horizontalPanelNew = new HorizontalPanel();
    private FlexTable flexTableNew = new FlexTable();
    private VerticalPanel verticalPanelOfSortNew = new VerticalPanel();

    final Button SORT = new Button("Sort");
    final Button RESET = new Button("Reset");

    public Demo() {
    }

    public void onModuleLoad() {
        joinField();
        RootPanel.get("block").add(VERTICAL_PANEL_OF);
        TEXT_BOX.setFocus(true);
        enterNumber();

        setStyles();
    }

    private void joinField() {
        VERTICAL_PANEL_OF.add(TEXT_BOX);
        VERTICAL_PANEL_OF.add(BUTTON);
        joinSortTable();
    }

    public void enterNumber() {
        BUTTON.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                generateNumbers();
            }
        });

        TEXT_BOX.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                if (keyDownEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) generateNumbers();
            }
        });
    }

    public void generateNumbers() {
        final String numbers = TEXT_BOX.getText().trim();

        if (!numbers.matches("^\\d+$")) {
            Window.alert("'" + numbers + "' isn't right. Enter number from 1 till 1000.");
            TEXT_BOX.selectAll();
            return;
        }

        randomNumbers(numbers);
        TEXT_BOX.setText("");
    }

    private void randomNumbers(String numbers) {
        int count = Integer.parseInt(numbers);
        int[] arrOfNumber = new int[count];

        for (int i = 0; i < arrOfNumber.length; i++) {
            arrOfNumber[i] = (int) (Math.random() * 1000);
        }
        boolean smallValue = false;
        for (int i = 0; i < arrOfNumber.length; i++) {
            if (arrOfNumber[i] <= 30) {
                smallValue = true;
                break;
            }
        }

        if (!smallValue) arrOfNumber[(int) (Math.random() * (arrOfNumber.length - 1))] = (int) (Math.random() * 30);

        currentArrOfNumber = Arrays.copyOf(arrOfNumber, arrOfNumber.length);

        generateTableOfNumbers();

        RootPanel.get("block").clear();
        RootPanel.get("block").add(horizontalPanelNew);
    }

    private void generateTableOfNumbers() {
        putValueIntoSortTable();
        sort();
        reset();
    }

    private void joinSortTable() {
        verticalPanelOfSortNew.add(SORT);
        verticalPanelOfSortNew.add(RESET);
        horizontalPanelNew.add(flexTableNew);
        horizontalPanelNew.add(verticalPanelOfSortNew);
    }

    private void putValueIntoSortTable() {
        int valueOfStrings = 10;
        int row = 0;
        int column = 0;

        for (int i = 0; i < currentArrOfNumber.length; i++) {
            if (row >= valueOfStrings) {
                row = 0;
                column++;
                flexTableNew.setText(row, column, String.valueOf(currentArrOfNumber[i]));
            }

            flexTableNew.setText(row, column, String.valueOf(currentArrOfNumber[i]));
            row++;
        }
    }

    public boolean check = false;
    public int randomNumber = 0;
    public int cell = 0;

    public void sort() {
        flexTableNew.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                HTMLTable.Cell src = flexTableNew.getCellForEvent(clickEvent);
                int rowIndex = src.getRowIndex();
                int columnIndex = src.getCellIndex();
                int lengthForArray = columnIndex == 0 ? columnIndex : columnIndex * 10;
                cell = rowIndex + lengthForArray;

                int number = Integer.parseInt(flexTableNew.getCellFormatter().getElement(rowIndex, columnIndex).getInnerHTML());
                if (number <= 30) {
                    randomNumber = doChange(rowIndex, columnIndex);
                    currentArrOfNumber[cell] = randomNumber;
                    flexTableNew.setText(rowIndex, columnIndex, String.valueOf(currentArrOfNumber[cell]));
                }
                else Window.alert("Please select a value smaller or equal to 30.");
            }
        });
        SORT.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (!check) {
                    recursiveDecreasing(currentArrOfNumber, 0, currentArrOfNumber.length - 1);
                    check = true;
                } else {

                    recursiveIncreasing(currentArrOfNumber, 0, currentArrOfNumber.length - 1);
                    check = false;
                }
                afterSorting();
            }
        });
    }

    public int decreasingSort(int arr[], int left, int right) {
        int pivot = arr[left];
        int i = left;
        for (int j = left + 1; j <= right; j++) {
            if (arr[j] > pivot) {
                i = i + 1;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i];
        arr[i] = arr[left];
        arr[left] = temp;

        return i;

    }

    public void recursiveDecreasing(int arr[], int left, int right) {
        if (left < right) {
            int q = decreasingSort(arr, left, right);
            recursiveDecreasing(arr, left, q);
            recursiveDecreasing(arr, q + 1, right);
        }
    }

    public static int increasingSort(int[] arr, int left, int right) {
        int pivot = arr[right];
        for (int i = left; i < right; i++) {
            if (arr[i] < pivot) {
                int temp = arr[left];
                arr[left] = arr[i];
                arr[i] = temp;
                left++;
            }
        }

        int temp = arr[left];
        arr[left] = pivot;
        arr[right] = temp;

        return left;
    }

    public static void recursiveIncreasing(int[] arr, int left, int right) {
        int partition = increasingSort(arr, left, right);
        if (partition - 1 > left) {
            recursiveIncreasing(arr, left, partition - 1);
        }
        if (partition + 1 < right) {
            recursiveIncreasing(arr, partition + 1, right);
        }
    }

    private void afterSorting() {
        int valueOfStrings = 10;
        int row = 0;
        int column = 0;

        for (int i = 0; i < currentArrOfNumber.length; i++) {
            if (row >= valueOfStrings) {
                row = 0;
                column++;
                flexTableNew.setText(row, column, String.valueOf(currentArrOfNumber[i]));
            }

            flexTableNew.setText(row, column, String.valueOf(currentArrOfNumber[i]));
            row++;
        }
    }

    private void reset() {
        RESET.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Window.Location.reload();
            }
        });
    }

    private int doChange(int rowIndex, int columnIndex) {
        int random = (int) (Math.random() * 1000);
        flexTableNew.setText(rowIndex, columnIndex, String.valueOf(random));
        return random;
    }

    public void setStyles() {
        VERTICAL_PANEL_OF.setStyleName("block__intro");
        TEXT_BOX.setStyleName("block__text-box");
        BUTTON.setStyleName("block__button");

        horizontalPanelNew.setStyleName("block__sort-h");
        verticalPanelOfSortNew.setStyleName("block__sort-v");
        flexTableNew.setStyleName("block__sort_flex");
    }
}