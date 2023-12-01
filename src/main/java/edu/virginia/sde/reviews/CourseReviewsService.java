package edu.virginia.sde.reviews;

public class CourseReviewsService {
    private Review review;

    public CourseReviewsService(Review review){
        this.review = review;
    }

    public void saveReview(){
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.save(this.review);
    }

}

