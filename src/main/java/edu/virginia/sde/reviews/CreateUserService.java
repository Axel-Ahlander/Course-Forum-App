package edu.virginia.sde.reviews;

public class CreateUserService {
    private User user;
    public CreateUserService( User user){
        this.user = user;
    }

    public void saveUser(User user){
        UserDAO DAO = new UserDAO();
        DAO.save(user);
    }

}
