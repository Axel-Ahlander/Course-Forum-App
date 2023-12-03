package edu.virginia.sde.reviews;

import javafx.collections.ObservableList;

import java.util.List;

public class CourseReviewsService {
    private Review review;

    public CourseReviewsService() {}

    public CourseReviewsService(Review review) {
        this.review = review;
    }

    public void saveReview() {
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.save(this.review);
    }
    public void updateReview(User user, int newRating, String newComment, Course course) {
        ReviewDAO reviewDAO = new ReviewDAO();
        Review existingReview = reviewDAO.findByUserAndCourse(user, course);

        if (existingReview != null) {
            existingReview.setRating(newRating);
            existingReview.setComment(newComment);

            reviewDAO.update(existingReview);
        }
    }

    public float calculateReviewAverage(Course course) {
        float grade = 0;
        int count = 0;
        CourseDAO courseDao = new CourseDAO();
        List<Review> reviews = courseDao.getAllReviews(course);
        for (Review review : reviews) {
            count++;
            grade += review.getRating();
        }
        if (count != 0) { // has rating
            return grade / count;
        }
        else { // does not have rating and can be 0 since users can only enter 1-5
            return 0;
        }
    }


}