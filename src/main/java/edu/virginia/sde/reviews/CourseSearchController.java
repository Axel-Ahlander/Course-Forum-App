package edu.virginia.sde.reviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.virginia.sde.reviews.LoginController.activeUser;

public class CourseSearchController {

    @FXML
    Hyperlink logOutLink;

    @FXML
    private TabPane tabPane;

    @FXML
    private Button addCourseTabButton, selectSearchTabButton, searchButton, addCourseButton;

    @FXML
    private Label usernameLabel, searchErrorLabel, addCourseErrorLabel, addCourseSuccessLabel;

    @FXML
    private TextField searchSubjectTextField, searchNumberTextField, searchTitleTextField, addCourseSubjectTextField, addCourseNumberTextField, addCourseTitleTextField;
    @FXML
    TableColumn<Course, String> courseSubjectColumn;
    @FXML
    TableColumn<Course, Integer> courseNumberColumn;
    @FXML
    TableColumn<Course, String> courseTitleColumn;
    @FXML
    TableColumn<Course, Float> courseRatingColumn;
    @FXML
    TableView<Course> tableView;

    @FXML
    TableColumn<Course, Void> buttonColumn;

    private Course courseReview;

    public void initialize() {
        addCourseTabButton.setOnAction(e -> selectTab());
        selectSearchTabButton.setOnAction(e -> tabPane.getSelectionModel().select(0));

        usernameLabel.setText(activeUser.getName());

        searchErrorLabel.setText("");
        addCourseErrorLabel.setText("");
        addCourseSuccessLabel.setText("");

        //not sure if this is necessary
     /*   tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {
            @Override
            public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
                if (newValue != null) {
                  //  handleRowSelection(newValue);
                    handleHyperlinkAction(newValue);
                }
            }
        });
*/
        //hyperlink:

        ///will replace with rating once method for calculating the rating is worked out
        courseSubjectColumn.setCellFactory(column -> new TableCell<Course, String>() {
            Hyperlink hyperlink = new Hyperlink();

            {
                hyperlink.setOnAction(event -> {
                    Course selectedCourse = getTableView().getItems().get(getIndex());
                    handleHyperlinkAction(selectedCourse);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    hyperlink.setText(item);
                    setGraphic(hyperlink);
                }
            }
        });
        tableViewAllCourses();
    }

    private void handleHyperlinkAction(Course selectedCourse) {
        // Do something with the selected course, for example, pass it to another scene
        // You can use selectedCourse.getSubject(), selectedCourse.getNumber(), etc.
        // Example: Pass selectedCourse to a method that creates a new scene
        setCourseReview(selectedCourse);
        System.out.println(selectedCourse.getSubject());
        System.out.println(selectedCourse.getNumber());
        System.out.println(selectedCourse.getId());
        openNewScene(selectedCourse);
    }

    public Course getCourseReview() {
        return courseReview;
    }

    public void setCourseReview(Course courseReview) {
        this.courseReview = courseReview;
    }

    private void openNewScene(Course selectedCourse) {
        // Implement the logic to create and show a new scene using the selected course
        // You can use FXMLLoader to load the FXML for the new scene
        // Example:
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("NewScene.fxml"));
        // Parent root = loader.load();
        // NewSceneController controller = loader.getController();
        // controller.initialize(selectedCourse);
        // ...
        // Show the new scene
    }

    public void temporaryCourseReviewsButton(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseReviews.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Reviews");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    private void handleButtonAction(Course selectedCourse) {
        // Do something with the selected course, for example, pass it to another scene
        // You can use selectedCourse.getSubject(), selectedCourse.getNumber(), etc.

        // Example: Pass selectedCourse to a method that creates a new scene
        openNewScene(selectedCourse);
    }


    public void searchSubjectTextField() {
        searchNumberTextField.requestFocus();
    }

    public void searchNumberTextField() {
        searchTitleTextField.requestFocus();
    }

    public void searchTitleTextField() {
        searchButton.fire();
    }

    public void addCourseSubjectTextField() {
        addCourseNumberTextField.requestFocus();
    }

    public void addCourseNumberTextField() {
        addCourseTitleTextField.requestFocus();
    }

    public void addCourseTitleTextField() {
        addCourseButton.fire();
    }

    private void selectTab() {
        tabPane.getTabs().stream()
                .filter(tab -> tab.getText().equals("Add Course"))
                .findFirst()
                .ifPresent(tab -> tabPane.getSelectionModel().select(tab));
    }

    public void myReviewsButtonClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MyReviews.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("My Reviews");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void logOutAccountClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void handleAddCourseButtonClick() {
        addCourseErrorLabel.setText("");
        addCourseSuccessLabel.setText("");
        if (validAddCourseInput()) {
            if (!courseExists()) {
                createNewCourse();
                CourseDAO courseDAO = new CourseDAO();
                // Refresh the TableView with the updated reviews
                ObservableList<Course> updatedCourses = courseDAO.getAllCourses();
                tableView.setItems(updatedCourses);
                tableView.refresh();
            } else {
                addCourseErrorLabel.setText("The course already exists");
            }
        }
    }

    private boolean validAddCourseInput() {
        String subject = addCourseSubjectTextField.getText();
        String number = addCourseNumberTextField.getText();
        String title = addCourseTitleTextField.getText();
        if (!validAddCourseSubject(subject)) {
            return false;
        }
        if (!validAddCourseNumber(number)) {
            return false;
        }
        return validAddCourseTitle(title);
    }

    private boolean validAddCourseSubject(String subject) {
        if (subject.isEmpty()) {
            addCourseErrorLabel.setText("Must enter a course subject");
            return false;
        }
        if (!subject.matches("[a-zA-Z]+")) {
            addCourseErrorLabel.setText("Subject must be letters only");
            return false;
        }
        if (subject.length() < 2 || subject.length() > 4) {
            addCourseErrorLabel.setText("Subject must be 2-4 letters long");
            return false;
        }
        return true;

    }

    private boolean validAddCourseNumber(String number) {
        if (number.isEmpty()) {
            addCourseErrorLabel.setText("Must enter a course number");
            return false;
        }
        if (!number.matches("\\d+")) {
            addCourseErrorLabel.setText("Course number must be numbers only");
            return false;
        }
        if (number.length() != 4) {
            addCourseErrorLabel.setText("Course number must be exactly 4 digits");
            return false;
        }
        return true;
    }

    private boolean validAddCourseTitle(String title) {
        if (title.isEmpty() || title.length() > 50) {
            addCourseErrorLabel.setText("Course title must be 1-50 characters long");
            return false;
        }
        return true;
    }

    private boolean courseExists() {
        ObservableList<Course> courses = tableView.getItems();
        List<Course> selectedCourses = new ArrayList<>();
        for (Course course : courses) {
            if (addCourseSubjectTextField.getText().equalsIgnoreCase(course.getSubject()) &&
                    Integer.parseInt(addCourseNumberTextField.getText()) == (course.getNumber())
                    && addCourseTitleTextField.getText().equalsIgnoreCase(course.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public void handleSearchButtonClick() {
        searchErrorLabel.setText("");
        if (validSearchInput()) {
            //code to search for and display courses that fit search criteria
            //for title look for matching substrings (i.e., don't require exact titles
            //for subject and number, require exact letter matches
            selectiveSearchTableView();
        }
    }

    private boolean validSearchInput() {
        String subject = searchSubjectTextField.getText();
        String number = searchNumberTextField.getText();
        String title = searchTitleTextField.getText();

        if (!validSearchSubject(subject)) {
            return false;

        }
        if (!validSearchNumber(number)) {
            return false;
        }
        return validSearchTitle(title);
    }

    private boolean validSearchTitle(String title) {
        if (title.isEmpty()) {
            return true;
        }
        if (title.length() > 50) {
            searchErrorLabel.setText("Title must be 50 characters or less");
            return false;
        }
        return true;
    }

    private boolean validSearchSubject(String subject) {
        if (subject.isEmpty()) {
            return true;
        }
        if (!subject.matches("[a-zA-Z]+")) {
            searchErrorLabel.setText("Subject must be letters only");
            return false;
        }
        if (subject.length() > 4) {
            searchErrorLabel.setText("Course subject cannot exceed 4 letters");
            return false;
        }
        return true;
    }

    private boolean validSearchNumber(String number) {
        if (number.isEmpty()) {
            return true;
        }
        if (!number.matches("\\d+")) {
            searchErrorLabel.setText("Course number must be numbers only");
            return false;
        }
        if (number.length() > 4) {
            searchErrorLabel.setText("Course number cannot exceed 4 digits");
            return false;
        }
        return true;
    }

    private void createNewCourse() {
        try {
            Course course = new Course();
            course.setSubject(addCourseSubjectTextField.getText());
            course.setNumber(Integer.parseInt(addCourseNumberTextField.getText()));
            course.setTitle(addCourseTitleTextField.getText());
            CreateCourseService createCourse = new CreateCourseService(course);
            createCourse.saveCourse();
            addCourseSuccessLabel.setText("Course successfully added.");
        } catch (NumberFormatException e) {
            addCourseErrorLabel.setText("Invalid number format. Please enter a valid course number.");
        } catch (Exception e) {
            addCourseErrorLabel.setText("An error occurred while adding the course.");
        }
    }

    private void tableViewAllCourses() {
        courseSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        CourseDAO courseDAO = new CourseDAO();
        List<Course> courseList = courseDAO.getAllCourses();

        tableView.getItems().clear();//not sure if this line is necessary
        tableView.getItems().addAll(courseList);
        tableView.setItems(FXCollections.observableList(courseList));
        tableView.refresh();
// need to cite McBurneys piazza post here probably
/*
    var cell = new TableCell<ReviewTableRow, String>();
    Text text = new Text();
    cell.setGraphic(text);
    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
    text.wrappingWidthProperty().bind(commentColumn.widthProperty());
    text.textProperty().bind(cell.itemProperty());
    return cell;
 });*/
        courseTitleColumn.setCellFactory(column -> {
            var cell = new TableCell<Course, String>() {
                final Text text = new Text();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    text.setText(item);
                    text.wrappingWidthProperty().bind(courseTitleColumn.widthProperty());
                    setGraphic(text);
                }
            };
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            return cell;
        });
    }

    public void selectiveSearchTableView() {
        ObservableList<Course> courses = tableView.getItems();
        List<Course> filteredCourses = new ArrayList<>();

        String searchSubject = searchSubjectTextField.getText().trim().toLowerCase();
        String searchNumber = searchNumberTextField.getText().trim();
        String searchTitle = searchTitleTextField.getText().trim().toLowerCase();

        boolean isSubjectEmpty = searchSubject.isEmpty();
        boolean isNumberEmpty = searchNumber.isEmpty();
        boolean isTitleEmpty = searchTitle.isEmpty();

        for (Course course : courses) {
            boolean subjectMatch = isSubjectEmpty || course.getSubject().equalsIgnoreCase(searchSubject);
            boolean numberMatch = isNumberEmpty || String.valueOf(course.getNumber()).equals(searchNumber);
            boolean titleMatch = isTitleEmpty || course.getTitle().toLowerCase().contains(searchTitle);

            if ((subjectMatch && numberMatch && titleMatch) ||
                    (subjectMatch && numberMatch && isTitleEmpty) ||
                    (subjectMatch && isNumberEmpty && titleMatch) ||
                    (isSubjectEmpty && numberMatch && titleMatch) ||
                    (subjectMatch && isNumberEmpty && isTitleEmpty) ||
                    (isSubjectEmpty && numberMatch && isTitleEmpty) ||
                    (isSubjectEmpty && isNumberEmpty && titleMatch)) {
                filteredCourses.add(course);
            }
        }

        tableView.setItems(FXCollections.observableList(filteredCourses));
        tableView.refresh();
    }



}

