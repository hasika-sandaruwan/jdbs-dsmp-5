package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.CrudUtil;
import view.tm.CustomerTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TableView<CustomerTm> tblCustomers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colSalary;
    public TableColumn colAddress;
    public TableColumn colTools;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colTools.setCellValueFactory(new PropertyValueFactory<>("bar"));

        try{
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> tmObservableList = FXCollections.observableArrayList();
        ResultSet set = CrudUtil.execute("SELECT * from customer");
        while (set.next()) {
            Button deleteButton = new Button("Delete");
            Button updateButton = new Button("Update");

            ButtonBar buttonBar = new ButtonBar();
            buttonBar.getButtons().addAll(deleteButton, updateButton);

            CustomerTm customer = new CustomerTm(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getDouble(4),
                    buttonBar
            );

            deleteButton.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {
                   try{
                     boolean  isDeleted = CrudUtil.execute("DELETE FROM customer WHERE customer_id = ?", customer.getId());
                     if(isDeleted){
                         loadAllCustomers();
                         new Alert(Alert.AlertType.INFORMATION,"Customer has been deleted").show();
                     }else{
                         new Alert(Alert.AlertType.WARNING,"Try Again").show();
                     }
                   } catch (SQLException | ClassNotFoundException ex) {
                       throw new RuntimeException(ex);
                   }
                }
            });

            tmObservableList.add(customer);
        }
        tblCustomers.setItems(tmObservableList);
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        try{
            boolean isSaved = CrudUtil.execute(
                    "INSERT INTO customer VALUES (?, ?, ?, ?)",
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );
            if(isSaved){
                clearFields();
                loadAllCustomers();
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Customer Not Saved").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    private void clearFields(){
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
    }
}
