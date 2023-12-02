package edu.virginia.sde.reviews;

public class CourseReviewsService {
    private Review review;

    public CourseReviewsService() {
    }

    public CourseReviewsService(Review review) {
        this.review = review;
    }

    public void saveReview() {
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.save(this.review);
    }

    public void updateReview(User user, int newRating, String newComment) {
        ReviewDAO reviewDAO = new ReviewDAO();
        Review existingReview = reviewDAO.findByUser(user);

        if (existingReview != null) {
            existingReview.setRating(newRating);
            existingReview.setComment(newComment);

            reviewDAO.update(existingReview); // Use an update method instead
        }
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



}