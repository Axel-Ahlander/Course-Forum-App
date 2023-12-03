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
import java.util.Set;

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

        //The average course review rating of the course - this should be blank if the course has no reviews,
// otherwise show as a number with two decimal places (i.e., 2.73, 5.00, etc.)
        courseRatingColumn.setCellFactory(column -> new TableCell<Course, Float>() {
            Hyperlink hyperlink = new Hyperlink();

            {
                hyperlink.setOnAction(event -> {
                    Course selectedCourse = getTableView().getItems().get(getIndex());
                    handleHyperlinkAction(selectedCourse);
                });
            }

            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                // set blank if rating is null or default(0)
                if (empty || item == null || item == 0.0f) {
                    hyperlink.setText("___");
                } else {
                    hyperlink.setText(String.format("%.2f", item));
                }
                setGraphic(hyperlink);
            }
        });
        // may need if change hyperlink to be other than rating since blank if no ratings
        // or just access using myReviews to add to a course that doesn't yet have a review?
//
//        courseSubjectColumn.setCellFactory(column -> new TableCell<Course, String>() {
//            Hyperlink hyperlink = new Hyperlink();
//
//            {
//                hyperlink.setOnAction(event -> {
//                    Course selectedCourse = getTableView().getItems().get(getIndex());
//                    handleHyperlinkAction(selectedCourse);
//                });
//            }
//
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    hyperlink.setText(item);
//                    setGraphic(hyperlink);
//                }
//            }
//        });
        calcRevAvg();
        tableViewAllCourses();
    }

    private void handleHyperlinkAction(Course selectedCourse) {
        //probably better way to do this..
        Set<Review> courseReviews = selectedCourse.getReviews();
        boolean hasReview = false;
        for (Review review : courseReviews) {
            if (review.getUser().equals(activeUser)) {
                hasReview = true;
                break;
            }
        }
        if (hasReview) {
            courseReviewSceneEditReview(selectedCourse);
        } else {
            courseReviewSceneAddReview(selectedCourse);
        }
    }

    private void courseReviewSceneAddReview(Course selectedCourse) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviews.fxml"));
            Parent root = loader.load();
            CourseReviewsController controller = loader.getController();
            controller.initialize(selectedCourse);
            Stage stage = (Stage) logOutLink.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Course Reviews");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void courseReviewSceneEditReview(Course selectedCourse) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviewsEditReview.fxml"));
            Parent root = loader.load();
            CourseReviewsEditReviewController controller = loader.getController();
            controller.initialize(selectedCourse);
            Stage stage = (Stage) logOutLink.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Course Reviews");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if (courseExists()) {
                addCourseErrorLabel.setText("The course already exists");
            } else {
                createNewCourse();
                CourseDAO courseDAO = new CourseDAO();
                // Refresh the TableView with the updated reviews
                ObservableList<Course> updatedCourses = courseDAO.getAllCourses();
                tableView.setItems(updatedCourses);
                tableView.refresh();
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
        CourseDAO courseDAO = new CourseDAO();
        ObservableList<Course> allCourses = courseDAO.getAllCourses();
        for (Course course : allCourses) {
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
            course.setSubject(addCourseSubjectTextField.getText().toUpperCase());
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

    private void selectiveSearchTableView() {
        CourseDAO courseDAO = new CourseDAO();
        ObservableList<Course> courses = courseDAO.getAllCourses();
        List<Course> filteredCourses = new ArrayList<>();

        String searchSubject = searchSubjectTextField.getText().trim().toLowerCase();
        String searchNumber = searchNumberTextField.getText().trim();
        String searchTitle = searchTitleTextField.getText().trim().toLowerCase();

        // Empty check flags
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

    public void calcRevAvg() {
        CourseDAO dao = new CourseDAO();
        ObservableList<Course> courses = dao.getAllCourses();

        for (Course course : courses) {
            List<Review> reviews = dao.getAllReviews(course);
            float gradeTotal = 0;
            int count = 0;

            for (Review review : reviews) {
                count++;
                gradeTotal += review.getRating();
            }
            if (count != 0) { // if course has review
                float avgRating = gradeTotal / count;
                course.setRating(avgRating);
                dao.merge(course);
            } else { // if course doesn't have review set to 0 since user can only set 1-5
                course.setRating(0);
                dao.merge(course);
            }
        }
        tableView.refresh();
    }
}



