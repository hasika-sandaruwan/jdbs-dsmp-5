package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView tblCustomers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colSalary;
    public TableColumn colAddress;
    public TableColumn colTools;

    public void saveCustomerOnAction(ActionEvent actionEvent) {
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
    }
}
