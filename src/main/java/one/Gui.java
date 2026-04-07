package one;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Gui extends Application {

    private final MariaDbConnection dbConnection = new MariaDbConnection();
    private final HelperClass helper = new HelperClass(dbConnection);
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final CartService cartService = new CartService(dbConnection);

    private TextField nameField = new TextField();
    private TextField priceField = new TextField();
    private TextField quantityField = new TextField();

    private Button getResult = new Button();
    private Button addItem = new Button();
    private Button displayButton = new Button();

    private Label name = new Label();
    private Label price = new Label();
    private Label quantity = new Label();
    private Label addedLabel = new Label();
    private Label resultLabel = new Label("");

    private void updateUI() {
        nameField.setPromptText(helper.getMessage("prompt.itemName"));
        priceField.setPromptText(helper.getMessage("prompt.itemPrice"));
        quantityField.setPromptText(helper.getMessage("prompt.itemQuantity"));

        getResult.setText(helper.getMessage("total.bill"));
        displayButton.setText(helper.getMessage("prompt.displayButton"));
        addItem.setText(helper.getMessage("prompt.addItem"));

        name.setText(helper.getMessage("field.name"));
        price.setText(helper.getMessage("field.price"));
        quantity.setText(helper.getMessage("field.quantity"));

        addedLabel.setText(""); // reset message
    }

    @Override
    public void start(Stage primaryStage) {

        HBox langBar = createLanguageBar();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(16));

        ColumnConstraints labelCol = new ColumnConstraints(90);
        ColumnConstraints fieldCol = new ColumnConstraints();
        fieldCol.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelCol, fieldCol);

        // Input validation
        priceField
                .setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d*(\\.\\d*)?") ? c : null));
        quantityField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d*") ? c : null));

        grid.add(name, 0, 0);
        grid.add(price, 0, 1);
        grid.add(quantity, 0, 2);
        grid.add(nameField, 1, 0);
        grid.add(priceField, 1, 1);
        grid.add(quantityField, 1, 2);

        // 🎨 Input style
        String fieldStyle = "-fx-background-color: #f9fafb;" +
                "-fx-border-color: #d1d5db;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 6 10;" +
                "-fx-font-size: 13;";

        nameField.setStyle(fieldStyle);
        priceField.setStyle(fieldStyle);
        quantityField.setStyle(fieldStyle);

        // 🎨 Buttons
        addItem.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 10;");
        getResult.setStyle("-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-background-radius: 10;");
        displayButton.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-background-radius: 10;");

        addedLabel.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
        resultLabel.setStyle("-fx-text-fill: #111827; -fx-font-size: 14; -fx-font-weight: bold;");

        // ➕ Add item action
        addItem.setOnAction(e -> {
            try {
                String itemName = nameField.getText();
                double priceVal = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(quantityField.getText());

                shoppingCart.addItem(itemName, new Item(priceVal, qty));

                addedLabel.setText(helper.getMessage("prompt.itemAdded"));

                nameField.clear();
                priceField.clear();
                quantityField.clear();

            } catch (Exception ex) {
                addedLabel.setText("Invalid input!");
            }
        });

        // 💰 Total button
        getResult.setOnAction(e -> {
            resultLabel.setText(
                    helper.getMessage("total.bill") + shoppingCart.getTotalBill());
            cartService.saveCart(shoppingCart, helper.getCurrentLocale());
        });

        // 📋 Display items
        displayButton.setOnAction(e -> resultLabel.setText(shoppingCart.displayBill()));

        HBox addRow = new HBox(10, addItem, addedLabel);
        addRow.setAlignment(Pos.CENTER_LEFT);
        addRow.setPadding(new Insets(0, 16, 8, 16));

        HBox buttonRow = new HBox(10, getResult, displayButton);
        buttonRow.setPadding(new Insets(0, 16, 8, 16));

        HBox resultRow = new HBox(resultLabel);
        resultRow.setPadding(new Insets(0, 16, 12, 16));

        updateUI();

        VBox card = new VBox(grid, addRow, buttonRow, resultRow);
        card.setSpacing(12);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-radius: 16;" +
                        "-fx-border-color: #e0e0e0;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 4);");

        VBox root = new VBox(langBar, card);
        root.setSpacing(14);
        root.setPadding(new Insets(16));
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #f5f7fa, #e4e8eb);" +
                        "-fx-font-family: 'Segoe UI';");

        Scene scene = new Scene(root, 450, 380);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shopping Cart");
        primaryStage.show();
    }

    private HBox createLanguageBar() {
        Button en = new Button("EN");
        Button fi = new Button("FI");
        Button sv = new Button("SV");
        Button ja = new Button("JP");
        Button ur = new Button("UR");
        Button ne = new Button("NE");

        String style = "-fx-background-color: #374151;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 6 10;";

        en.setStyle(style);
        fi.setStyle(style);
        sv.setStyle(style);
        ja.setStyle(style);
        ur.setStyle(style);
        ne.setStyle(style);

        en.setOnAction(e -> switchLang("en"));
        fi.setOnAction(e -> switchLang("fi"));
        sv.setOnAction(e -> switchLang("sv"));
        ja.setOnAction(e -> switchLang("ja"));
        ur.setOnAction(e -> switchLang("ur"));
        ne.setOnAction(e -> switchLang("ne"));

        HBox bar = new HBox(10, en, fi, sv, ja, ur, ne);
        bar.setPadding(new Insets(10));
        bar.setStyle("-fx-background-color: #1f2937; -fx-background-radius: 12;");
        return bar;
    }

    private void switchLang(String lang) {
        helper.setLocale(lang);
        updateUI();
    }

    public static void main(String[] args) {
        launch(args);
    }
}